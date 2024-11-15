package top.threshold.aphrodite.app.entity.pojo

import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableName
import top.threshold.aphrodite.pkg.entity.BaseOprDO
import java.time.OffsetDateTime

@TableName("t_user")
class UserDO : BaseOprDO() {

    @TableField("user_code")
    var userCode: String? = null

    @TableField("user_no")
    var userNo: Long? = null

    @TableField("username")
    var username: String? = null

    @TableField("nickname")
    var nickname: String? = null

    @TableField("password")
    var password: String? = null

    @TableField("salt")
    var salt: String? = null

    @TableField("email")
    var email: String? = null

    @TableField("phone")
    var phone: String? = null

    @TableField("client_ip")
    var clientIp: String? = null

    @TableField("login_at")
    var loginAt: OffsetDateTime? = null

    @TableField("login_token")
    var loginToken: String? = null

    @TableField("avatar")
    var avatar: String? = null

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
                ", avatar=" + avatar +
                ", clientIp=" + clientIp +
                ", loginAt=" + loginAt +
                ", loginToken=" + loginToken +
                "}"
    }
}
