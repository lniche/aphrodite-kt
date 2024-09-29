package top.threshold.ktscaffold.controller.v1;

import cn.hutool.core.bean.BeanUtil
import cn.hutool.core.util.DesensitizedUtil
import cn.hutool.core.util.IdUtil
import cn.hutool.core.util.StrUtil
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.Parameters
import io.swagger.v3.oas.annotations.enums.ParameterIn
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*
import top.threshold.ktscaffold.constant.CacheKey
import top.threshold.ktscaffold.entity.ResultKt
import top.threshold.ktscaffold.entity.pojo.UserDO
import top.threshold.ktscaffold.entity.vo.UserVO
import top.threshold.ktscaffold.enums.KtCode
import top.threshold.ktscaffold.repository.IUserRepository
import top.threshold.ktscaffold.util.RedisUtil


/**
 * <p>
 *  前端控制器
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
    @GetMapping("/get/{userCode}")
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
    @PostMapping("/update")
    fun update(@Valid @RequestBody userVO: UserVO): ResultKt<Void> {
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
