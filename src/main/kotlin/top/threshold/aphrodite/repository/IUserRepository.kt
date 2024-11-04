package top.threshold.aphrodite.repository;

import com.baomidou.mybatisplus.extension.service.IService
import top.threshold.aphrodite.entity.pojo.UserDO

interface IUserRepository : IService<UserDO> {
    fun getByCode(userCode: String): UserDO?
    fun getByPhone(phone: String): UserDO?
}
