package top.threshold.aphrodite.repository.impl;

import com.baomidou.mybatisplus.extension.kotlin.KtQueryChainWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.stereotype.Service
import top.threshold.aphrodite.entity.pojo.UserDO
import top.threshold.aphrodite.mapper.UserMapper
import top.threshold.aphrodite.repository.IUserRepository

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qingshan
 * @since 2024-09-29
 */
@Service
open class UserRepositoryImpl : ServiceImpl<UserMapper, UserDO>(), IUserRepository {

    override fun getByCode(userCode: String): UserDO? {
        return KtQueryChainWrapper(UserDO::class.java).eq(UserDO::userCode, userCode).one()
    }

}
