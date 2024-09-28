package top.threshold.ktscaffold.repository.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.stereotype.Service
import top.threshold.ktscaffold.entity.pojo.AccountDO
import top.threshold.ktscaffold.mapper.AccountMapper
import top.threshold.ktscaffold.repository.IAccountService

/**
 * <p>
 * 用户账户表 服务实现类
 * </p>
 *
 * @author qingshan
 * @since 2024-09-28
 */
@Service
open class AccountServiceImpl : ServiceImpl<AccountMapper, AccountDO>(), IAccountService {

}
