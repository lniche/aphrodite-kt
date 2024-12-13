package top.threshold.aphrodite.app.repository;

import com.baomidou.mybatisplus.extension.service.IService
import top.threshold.aphrodite.app.model.pojo.UserDO

interface IUserRepository : IService<UserDO> {
    fun getByCode(userCode: String): UserDO?
    fun getByPhone(phone: String): UserDO?
}
