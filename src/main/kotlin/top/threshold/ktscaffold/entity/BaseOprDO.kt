package top.threshold.ktscaffold.entity

import lombok.Data

@Data
open class BaseOprDO : BaseDO() {

    var createUser: String? = null
    var updateUser: String? = null
}