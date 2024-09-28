package top.threshold.ktscaffold.entity.pojo

import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableName
import top.threshold.ktscaffold.entity.BaseDO

/**
 * <p>
 * 用户账户表
 * </p>
 *
 * @author qingshan
 * @since 2024-09-28
 */
@TableName("t_account")
class AccountDO : BaseDO() {

    /**
     * 用户名
     */
    @TableField("user_name")
    var userName: String? = null

    /**
     * 年龄
     */
    @TableField("age")
    var age: Int? = null

    override fun toString(): String {
        return "AccountDO{" +
                "userName=" + userName +
                ", age=" + age +
                "}"
    }
}
