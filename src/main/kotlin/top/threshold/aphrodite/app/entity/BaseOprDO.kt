package top.threshold.aphrodite.app.entity

import com.baomidou.mybatisplus.annotation.FieldFill
import com.baomidou.mybatisplus.annotation.TableField
import lombok.Data

@Data
open class BaseOprDO : BaseDO() {

    @TableField(fill = FieldFill.INSERT)
    var createdBy: String? = null

    @TableField(fill = FieldFill.UPDATE)
    var updatedBy: String? = null
}