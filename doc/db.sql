CREATE TABLE t_user
(
    id          INT AUTO_INCREMENT PRIMARY KEY,                                     -- 用户ID
    username    VARCHAR(50)  NOT NULL UNIQUE,                                       -- 用户名
    nickname    VARCHAR(50)  DEFAULT NULL UNIQUE,                                   -- 昵称
    user_no     INT(10)      NOT NULL UNIQUE,                                       -- 用户编号
    user_code   VARCHAR(100) NOT NULL UNIQUE,                                       -- 用户编码
    password    VARCHAR(255) NOT NULL,                                              -- 密码
    salt        VARCHAR(255) NOT NULL,                                              -- 加盐
    email       VARCHAR(100) DEFAULT NULL,                                          -- 电子邮件
    phone       VARCHAR(15)  DEFAULT NULL,                                          -- 电话号码
    create_by   VARCHAR(100) DEFAULT NULL,                                          -- 创建人
    update_by   VARCHAR(100) DEFAULT NULL,                                          -- 更新人
    create_time DATETIME     DEFAULT CURRENT_TIMESTAMP,                             -- 创建时间
    update_time DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- 更新时间
    is_deleted  TINYINT(1)   DEFAULT 0,                                             -- 软删除标识，0表示未删除，1表示已删除
    version     INT          DEFAULT 1                                             -- 乐观版本号
);