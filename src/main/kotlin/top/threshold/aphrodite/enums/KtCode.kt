package top.threshold.aphrodite.enums

import top.threshold.aphrodite.exception.KtAssert


/**
 * 错误产生来源分为 1/2/3，
 * 1 表示错误来源于用户，比如参数错误，用户安装版本过低，用户支付超时等问题；
 * 2 表示错误来源于当前系统，往往是业务逻辑出错，或程序健壮性差等问题；
 * 3 表示错误来源于第三方服务，比如 CDN 服务出错，消息投递超时等问题；四位数字编号从 0001 到 9999，大类之间的步长间距预留 100
 */
enum class KtCode(override val code: Int, override val message: String) : KtAssert {
    BAD_REQUEST(400, "非法的HTTP请求"),
    UNAUTHORIZED(401, "身份未认证"),
    FORBIDDEN(403, "拒绝执行此请求"),
    NOT_FOUND(404, "无法找到"),
    METHOD_NOT_SUPPORT(405, "方法被禁止"),
    HTTP_FAILURE(500, "HTTP调用异常"),

    OK(0, "成功"),
    FAIL(-1, "失败"),

    /**
     * 1类错误码
     */
    PHONE_NUM_INVALID(10001, "非法的手机号码"),
    VERIFY_SMS_INVALID(10002, "验证码不正确"),
    VERIFY_SMS_OVER_1MIN(10003, "一分钟后再试"),
    VERIFY_SMS_SEND_ERR(10004, "验证码服务异常"),
    VERIFY_SMS_EXPIRED(10005, "验证码已过期"),
    LOGIN_EXPIRED(10006, "由于您长时间未登录，请重新登录"),
    LOGIN_STATUS_ERROR1(10007, "用户状态异常"),
    LOGIN_STATUS_ERROR2(10008, "您已申请注销，请联系客服解决"),
    THIRD_LOGIN_NO_BIND_ERRPR(10009, "未绑定用户"),
    THIRD_LOGIN_OTHER_BIND_ERROR(10010, "当前手机号已绑定其他账号"),

    /**
     * 2类错误码
     */
    UNKNOW(20000, "未知异常"),
    PARAMS_MISSING(20001, "参数缺失"),
    PARAMS_INVALID(20002, "参数格式错误"),
    USER_DATA_MISSING(20003, "用户数据缺失"),
    CONFIG_DATA_MISSING(20004, "配置数据缺失"),
    ORDER_DATA_MISSING(20005, "订单数据缺失"),
    TRADE_ERROR(20006, "交易异常，请联系客服"),

    /**
     * 3类错误码
     */
    APPLE_NOTIFY_HANDLE_ERROR(30001, "苹果回调处理异常"),
    GOOGLE_NOTIFY_HANDLE_ERROR(30002, "谷歌回调处理异常"),
    ;

}