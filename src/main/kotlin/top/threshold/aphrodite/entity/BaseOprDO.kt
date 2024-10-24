package top.threshold.aphrodite.entity

import lombok.Data

@Data
open class BaseOprDO : BaseDO() {

    var createdBy: String? = null
    var updatedBy: String? = null
}