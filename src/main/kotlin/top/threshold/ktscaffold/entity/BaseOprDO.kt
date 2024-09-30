package top.threshold.ktscaffold.entity

import lombok.Data

@Data
open class BaseOprDO : BaseDO() {

    var createdBy: String? = null
    var updatedBy: String? = null
}