package top.threshold.aphrodite.app.entity.pojo

import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableName
import top.threshold.aphrodite.pkg.entity.BaseOprDO
import java.time.OffsetDateTime

@TableName("t_user")
class UserDO : BaseOprDO() {

    /**
     * User Code
     */
    @TableField("user_code")
    var userCode: String? = null

    /**
     * User Number
     */
    @TableField("user_no")
    var userNo: Long? = null

    /**
     * Username
     */
    @TableField("username")
    var username: String? = null

    /**
     * Nickname
     */
    @TableField("nickname")
    var nickname: String? = null

    /**
     * Password
     */
    @TableField("password")
    var password: String? = null

    /**
     * Salt
     */
    @TableField("salt")
    var salt: String? = null

    /**
     * Email
     */
    @TableField("email")
    var email: String? = null

    /**
     * Phone
     */
    @TableField("phone")
    var phone: String? = null

    /**
     * WeChat OpenID
     */
    @TableField("open_id")
    var openId: String? = null

    /**
     * Client IP
     */
    @TableField("client_ip")
    var clientIp: String? = null

    /**
     * Login Time
     */
    @TableField("login_at")
    var loginAt: OffsetDateTime? = null

    /**
     * Login Token
     */
    @TableField("login_token")
    var loginToken: String? = null

    /**
     * Avatar
     */
    @TableField("avatar")
    var avatar: String? = null

    /**
     * 0: Unactivated, 1: Active, 2: Frozen, 3: Deleted
     */
    @TableField("avatar")
    var status: Short? = null

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
