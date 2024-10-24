package top.threshold.ktscaffold.entity.pojo

import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableName
import top.threshold.ktscaffold.entity.BaseOprDO
import java.time.LocalDateTime

/**
 * <p>
 * 
 * </p>
 *
 * @author qingshan
 * @since 2024-10-24
 */
@TableName("t_user")
class UserDO : BaseOprDO() {

    /**
     * 用户编码
     */
    @TableField("user_code")
    var userCode: String? = null

    /**
     * 用户编号
     */
    @TableField("user_no")
    var userNo: Long? = null

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
     * 密码
     */
    @TableField("password")
    var password: String? = null

    /**
     * 盐值
     */
    @TableField("salt")
    var salt: String? = null

    /**
     * 邮箱
     */
    @TableField("email")
    var email: String? = null

    /**
     * 电话
     */
    @TableField("phone")
    var phone: String? = null

    /**
     * 微信OpenID
     */
    @TableField("open_id")
    var openId: String? = null

    /**
     * 客户端IP
     */
    @TableField("client_ip")
    var clientIp: String? = null

    /**
     * 登录时间
     */
    @TableField("login_at")
    var loginAt: LocalDateTime? = null

    /**
     * 登录令牌
     */
    @TableField("login_token")
    var loginToken: String? = null

    override fun toString(): String {
        return "UserDO{" +
                "userCode=" + userCode +
                ", userNo=" + userNo +
                ", username=" + username +
                ", nickname=" + nickname +
                ", password=" + password +
                ", salt=" + salt +
                ", email=" + email +
                ", phone=" + phone +
                ", openId=" + openId +
                ", clientIp=" + clientIp +
                ", loginAt=" + loginAt +
                ", loginToken=" + loginToken +
                "}"
    }
}
