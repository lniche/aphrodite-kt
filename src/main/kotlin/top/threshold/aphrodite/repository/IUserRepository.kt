package top.threshold.aphrodite.repository;

import com.baomidou.mybatisplus.extension.service.IService
import top.threshold.aphrodite.entity.pojo.UserDO

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qingshan
 * @since 2024-09-29
 */
interface IUserRepository : IService<UserDO> {
    fun getByCode(userCode: String): UserDO?
    fun getByPhone(phone: String): UserDO?
}
