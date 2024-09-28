package top.threshold.ktscaffold.entity

import com.baomidou.mybatisplus.annotation.*
import lombok.Data
import java.io.Serializable
import java.time.LocalDateTime


@Data
open class BaseDO : Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null

    @Version
    var version: Int? = null

    @TableField(value = "is_deleted")
    @TableLogic(value = "false", delval = "true")
    var deleted: Boolean? = null
    var createTime: LocalDateTime? = null
    var updateTime: LocalDateTime? = null
}