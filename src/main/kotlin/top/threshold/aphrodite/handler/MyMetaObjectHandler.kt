package top.threshold.aphrodite.handler

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler
import org.apache.ibatis.reflection.MetaObject
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class MyMetaObjectHandler : MetaObjectHandler {
    override fun insertFill(metaObject: MetaObject) {
        this.strictInsertFill(metaObject, "createTime", LocalDateTime::class.java, LocalDateTime.now())
    }

    override fun updateFill(metaObject: MetaObject) {
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime::class.java, LocalDateTime.now())
    }
}