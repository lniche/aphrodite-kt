package top.threshold.aphrodite.controller.v1;

import cn.hutool.core.bean.BeanUtil
import cn.hutool.core.util.DesensitizedUtil
import cn.hutool.core.util.IdUtil
import cn.hutool.core.util.StrUtil
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.Parameters
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import lombok.Data
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import top.threshold.aphrodite.constant.CacheKey
import top.threshold.aphrodite.controller.BaseController
import top.threshold.aphrodite.entity.ResultKt
import top.threshold.aphrodite.entity.pojo.UserDO
import top.threshold.aphrodite.entity.vo.UserVO
import top.threshold.aphrodite.repository.IUserRepository
import top.threshold.aphrodite.utils.RedisUtil
import java.time.LocalDate
import java.time.OffsetDateTime
import java.util.*


/**
 * <p>
 *  用户控制器
 * </p>
 *
 * @author qingshan
 * @since 2024-09-29
 */
@RestController
@RequestMapping("/user")
@Tag(name = "用户管理", description = "提供用户的创建、更新、删除和查询等操作")
class UserController(
    val userRepository: IUserRepository,
    val redisUtil: RedisUtil
) : BaseController() {
    @Operation(
        summary = "获取用户",
        description = "获取用户",
    )
    @Parameters(
        Parameter(name = "userCode", description = "用户编号", `in` = ParameterIn.PATH)
    )
    @GetMapping("/{userCode}")
    fun getUser(@PathVariable userCode: String): ResultKt<UserVO?> {
        val actualUserCode = if (StrUtil.isBlank(userCode)) {
            loginUid()
        } else {
            userCode
        }
        val redisKey = CacheKey.USER + actualUserCode
        val userVO = redisUtil.getObj(redisKey, UserVO::class.java)

        if (userVO == null) {
            val userDO = userRepository.getByCode(actualUserCode)
            userDO?.let {
                redisUtil[redisKey, it] = 60
                return ResultKt.success(UserVO().apply { BeanUtil.copyProperties(it, this) })
            }
        }

        userVO?.apply {
            email = DesensitizedUtil.email(email)
            phone = DesensitizedUtil.mobilePhone(phone)
        }

        return ResultKt.success(userVO)
    }

    @Data
    @Schema(description = "更新用户信息的请求对象")
    open class UpdateUserReq {
        /**
         * 用户名
         */
        @field:Schema(description = "用户名", example = "john_doe", required = true)
        var username: String? = null

        /**
         * 昵称
         */
        @field:Schema(description = "用户昵称", example = "John", required = false)
        var nickname: String? = null

        /**
         * 电子邮件
         */
        @field:Schema(description = "用户电子邮件", example = "john@example.com", required = false)
        var email: String? = null
    }

    @Operation(
        summary = "更新用户",
        description = "根据用户信息更新用户",
    )
    @PutMapping("")
    fun updateUser(
        @Parameter(description = "更新用户信息", required = true)
        @Validated @RequestBody updateUserVO: UpdateUserReq
    ): ResultKt<Void> {
        val userDO = userRepository.getByCode(loginUid()) ?: return ResultKt.fail("用户不存在")
        BeanUtil.copyProperties(updateUserVO, userDO, "userCode")
        userRepository.updateById(userDO)
        return ResultKt.success()
    }

    @Data
    @Schema(description = "更新用户信息的请求对象")
    class CreateUserReq : UpdateUserReq() {
        /**
         * 手机号
         */
        @field:NotBlank(message = "手机号不能为空")
        @field:Schema(description = "用户手机号", example = "13800138000", required = true)
        @Pattern(regexp = "^\\+?[1-9]\\d{1,14}\$", message = "手机号格式不正确")
        var phone: String? = null

        /**
         * 验证码
         */
        @field:NotBlank(message = "验证码不能为空")
        @field:Schema(description = "用户注册时的验证码", example = "1234", required = true)
        var code: String? = null
    }

    @Operation(
        summary = "新增用户",
        description = "根据用户信息新增用户",
    )
    @PostMapping("/register")
    fun createUser(
        @Parameter(description = "新增用户信息", required = true)
        @Validated @RequestBody createUserVO: CreateUserReq
    ): ResultKt<Void> {
        val existUser = userRepository.getByPhone(createUserVO.phone!!)
        if (Objects.nonNull(existUser)) {
            return ResultKt.fail("该手机号已被注册")
        }
        val today = LocalDate.now()
        val dailyKey = CacheKey.SMS_CODE_NUM + "${createUserVO.phone}:$today"
        if (!redisUtil.hasKey(dailyKey)) return ResultKt.fail("验证码失效，请重新获取")
        if (createUserVO.code.equals(redisUtil.getStr(dailyKey))) return ResultKt.fail("验证码错误，请重新输入")

        val userDO = UserDO()
        BeanUtil.copyProperties(createUserVO, userDO)
        userDO.userNo = redisUtil.nextId(CacheKey.NEXTID_UNO)
        userDO.userCode = IdUtil.getSnowflakeNextIdStr()
        userDO.clientIp = realIpAddress
        userDO.loginAt = OffsetDateTime.now()
        userRepository.save(userDO)

        redisUtil.del(dailyKey)
        return ResultKt.success()
    }

    @Operation(
        summary = "删除用户",
        description = "根据token获取到用户则删除",
    )
    @DeleteMapping("")
    fun deleteUser(): ResultKt<Void> {
        val userDO = userRepository.getByCode(loginUid()) ?: return ResultKt.fail("用户不合法")
        userDO.deleted = true
        userDO.deletedAt = OffsetDateTime.now()
        userRepository.updateById(userDO)
        return ResultKt.success()
    }
}
