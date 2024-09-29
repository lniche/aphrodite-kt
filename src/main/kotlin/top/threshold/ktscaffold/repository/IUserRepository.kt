package top.threshold.ktscaffold.repository;

import com.baomidou.mybatisplus.extension.service.IService
import top.threshold.ktscaffold.entity.pojo.UserDO

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

}
