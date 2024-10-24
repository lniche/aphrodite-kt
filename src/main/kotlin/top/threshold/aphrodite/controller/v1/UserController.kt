package top.threshold.aphrodite.controller.v1;

import cn.hutool.core.bean.BeanUtil
import cn.hutool.core.util.DesensitizedUtil
import cn.hutool.core.util.IdUtil
import cn.hutool.core.util.StrUtil
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.Parameters
import io.swagger.v3.oas.annotations.enums.ParameterIn
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import top.threshold.aphrodite.constant.CacheKey
import top.threshold.aphrodite.entity.ResultKt
import top.threshold.aphrodite.entity.pojo.UserDO
import top.threshold.aphrodite.entity.vo.UserVO
import top.threshold.aphrodite.enums.KtCode
import top.threshold.aphrodite.repository.IUserRepository
import top.threshold.aphrodite.util.RedisUtil


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
class UserController(
    val userService: IUserRepository,
    val redisUtil: RedisUtil
) {
    @Operation(summary = "获取用户信息")
    @Parameters(
        Parameter(name = "userCode", description = "用户编号", `in` = ParameterIn.PATH)
    )
    @GetMapping("/{userCode}")
    fun get(@PathVariable userCode: String): ResultKt<UserVO?> {
        KtCode.PARAMS_MISSING.assertNotEmpty(userCode)

        val redisKey = CacheKey.USER + userCode
        val userVO = redisUtil.getObj(redisKey, UserVO::class.java) ?: run {
            val userDO = userService.getByCode(userCode)
            userDO?.let {
                redisUtil.set(redisKey, it, 60)
            }
            UserVO().apply {
                BeanUtil.copyProperties(userDO, this)
            }
        }
        userVO.email = DesensitizedUtil.email(userVO.email)
        userVO.phone = DesensitizedUtil.mobilePhone(userVO.phone)
        return ResultKt.success(userVO)
    }

    @Operation(summary = "更新用户信息")
    @PutMapping("/")
    fun update(@Validated @RequestBody userVO: UserVO): ResultKt<Void> {
        KtCode.PARAMS_MISSING.assertNotNull(userVO)
        if (StrUtil.isBlank(userVO.userCode)) {
            val userDO = UserDO()
            BeanUtil.copyProperties(userVO, userDO)
            userDO.userNo = redisUtil.nextId(CacheKey.NEXTID_UNO)
            userDO.userCode = IdUtil.getSnowflakeNextIdStr()
            userService.save(userDO)
        } else {
            val userDO = userService.getByCode(userVO.userCode!!)
            BeanUtil.copyProperties(userVO, userDO, "userCode")
            userService.updateById(userDO)
        }
        return ResultKt.success()
    }
}
