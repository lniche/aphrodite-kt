package top.threshold.ktscaffold.controller.v1;

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import top.threshold.ktscaffold.constant.CacheKey
import top.threshold.ktscaffold.entity.ResultKt
import top.threshold.ktscaffold.entity.pojo.AccountDO
import top.threshold.ktscaffold.enums.RCode
import top.threshold.ktscaffold.repository.IAccountService
import top.threshold.ktscaffold.util.RedisUtil

/**
 * <p>
 * 用户账户表 前端控制器
 * </p>
 *
 * @author qingshan
 * @since 2024-09-28
 */
@RestController
@RequestMapping("/account")
class AccountController(
    val accountService: IAccountService,
    val redisUtil: RedisUtil
) {
    @GetMapping("/get/{id}")
    fun get(@PathVariable id: Long): ResultKt<AccountDO?> {
        RCode.PARAMS_MISSING.assertNotNull(id)
        if (redisUtil.hasKey(CacheKey.ACCOUNT + id)) {
            return ResultKt.success(redisUtil.getObj(CacheKey.ACCOUNT + id, AccountDO::class.java))
        }
        val accountDO = accountService.getById(id)
        redisUtil[CacheKey.ACCOUNT + id, accountDO] = 60
        return ResultKt.success(accountDO)
    }
}

