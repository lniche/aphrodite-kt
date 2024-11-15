package top.threshold.aphrodite.pkg.entity

import com.baomidou.mybatisplus.annotation.*
import lombok.Data
import java.time.OffsetDateTime

@Data
open class BaseDO {

    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null

    @Version
    var version: Long? = null

    @TableField(fill = FieldFill.INSERT)
    var createdAt: OffsetDateTime? = null

    @TableField(fill = FieldFill.UPDATE)
    var updatedAt: OffsetDateTime? = null

    @TableField
    var deletedAt: OffsetDateTime? = null
}
