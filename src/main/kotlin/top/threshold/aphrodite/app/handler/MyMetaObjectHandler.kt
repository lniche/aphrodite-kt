package top.threshold.aphrodite.app.handler

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler
import org.apache.ibatis.reflection.MetaObject
import org.springframework.stereotype.Component
import java.time.OffsetDateTime

@Component
class MyMetaObjectHandler : MetaObjectHandler {
    override fun insertFill(metaObject: MetaObject) {
        this.strictInsertFill(metaObject, "createdAt", OffsetDateTime::class.java, OffsetDateTime.now())
        this.strictInsertFill(metaObject, "createdBy", String::class.java, "777")
    }

    override fun updateFill(metaObject: MetaObject) {
        this.strictUpdateFill(metaObject, "updatedAt", OffsetDateTime::class.java, OffsetDateTime.now())
        this.strictInsertFill(metaObject, "updatedBy", String::class.java, "777")
    }
}