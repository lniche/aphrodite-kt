package top.threshold.ktscaffold.entity.pojo

import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableName
import lombok.Data
import top.threshold.ktscaffold.entity.BaseDO

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author qingshan
 * @since 2024-09-29
 */
@Data
@TableName("t_user")
class UserDO : BaseDO() {

    /**
     * 用户名
     */
    @TableField("username")
    var username: String? = null

    /**
     * 昵称
     */
    @TableField("nickname")
    var nickname: String? = null

    /**
     * 用户编号
     */
    @TableField("user_no")
    var userNo: Long? = null

    /**
     * 用户编码
     */
    @TableField("user_code")
    var userCode: String? = null

    /**
     * 密码
     */
    @TableField("password")
    var password: String? = null

    /**
     * 加盐
     */
    @TableField("salt")
    var salt: String? = null

    /**
     * 电子邮件
     */
    @TableField("email")
    var email: String? = null

    /**
     * 电话号码
     */
    @TableField("phone")
    var phone: String? = null

    override fun toString(): String {
        return "UserDO{" +
                "username=" + username +
                ", nickname=" + nickname +
                ", userNo=" + userNo +
                ", userCode=" + userCode +
                ", password=" + password +
                ", salt=" + salt +
                ", email=" + email +
                ", phone=" + phone +
                "}"
    }
}
