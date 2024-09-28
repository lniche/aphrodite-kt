package top.threshold.ktscaffold.entity

import lombok.Data

@Data
open class BaseOprDO : BaseDO() {

    var createUser: Long? = null

    var updateUser: Long? = null
}