package top.threshold.aphrodite.controller.v1;

import cn.hutool.core.bean.BeanUtil
import cn.hutool.core.util.DesensitizedUtil
import cn.hutool.core.util.StrUtil
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.Parameters
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import lombok.Data
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import top.threshold.aphrodite.constant.CacheKey
import top.threshold.aphrodite.controller.BaseController
import top.threshold.aphrodite.entity.ResultKt
import top.threshold.aphrodite.repository.IUserRepository
import top.threshold.aphrodite.utils.RedisUtil
import java.time.OffsetDateTime


/**
 * <p>
 *  用户控制器
 * </p>
 *
 * @author qingshan
 * @since 2024-09-29
 */
@RestController
@RequestMapping("/v1/user")
@Tag(name = "用户模块")
class UserController(
    val userRepository: IUserRepository,
    val redisUtil: RedisUtil
) : BaseController() {
    @Data
    class GetUserResp {
        /**
         * 昵称
         */
        var nickname: String? = null

        /**
         * 用户编号
         */
        var userNo: Long? = null

        /**
         * 用户编码
         */
        var userCode: String? = null

        /**
         * 电子邮件
         */
        var email: String? = null

        /**
         * 电话号码
         */
        var phone: String? = null
    }

    @Operation(
        summary = "获取用户",
        description = "获取用户",
        security = [SecurityRequirement(name = "Authorization")]
    )
    @Parameters(
        Parameter(name = "userCode", description = "用户编号", `in` = ParameterIn.PATH)
    )
    @GetMapping("/{userCode}")
    fun getUser(@PathVariable userCode: String): ResultKt<GetUserResp?> {
        val actualUserCode = if (StrUtil.isBlank(userCode)) {
            loginUid()
        } else {
            userCode
        }
        val redisKey = CacheKey.USER + actualUserCode
        val getUserResp = redisUtil.getObj(redisKey, GetUserResp::class.java)

        if (getUserResp == null) {
            val userDO = userRepository.getByCode(actualUserCode)
            userDO?.let {
                redisUtil[redisKey, it] = 60
                return ResultKt.success(GetUserResp().apply { BeanUtil.copyProperties(it, this) })
            }
        }

        getUserResp?.apply {
            email = DesensitizedUtil.email(email)
            phone = DesensitizedUtil.mobilePhone(phone)
        }

        return ResultKt.success(getUserResp)
    }

    @Data
    @Schema(description = "更新用户信息的请求对象")
    open class UpdateUserReq {
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
        security = [SecurityRequirement(name = "Authorization")]
    )
    @PutMapping("")
    fun updateUser(
        @Parameter(description = "更新用户信息", required = true)
        @Validated @RequestBody updateUserReq: UpdateUserReq
    ): ResultKt<Void> {
        val userDO = userRepository.getByCode(loginUid()) ?: return ResultKt.fail("用户不存在")
        BeanUtil.copyProperties(updateUserReq, userDO, "userCode")
        userRepository.updateById(userDO)
        return ResultKt.success()
    }

    @Operation(
        summary = "删除用户",
        description = "根据token获取到用户则删除",
        security = [SecurityRequirement(name = "Authorization")]
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
