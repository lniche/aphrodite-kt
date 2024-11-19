package top.threshold.aphrodite.services

import kotlinx.coroutines.Dispatchers
import kotlinx.datetime.Clock
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import top.threshold.aphrodite.models.UserSchema

class UserService(database: Database) {

    object User : Table("t_user") {
        val id = long("id").autoIncrement()
        val userCode = varchar("user_code", 50).uniqueIndex()
        val userNo = long("user_no").uniqueIndex()
        val username = varchar("username", 50).nullable().uniqueIndex()
        val nickname = varchar("nickname", 50).nullable()
        val password = varchar("password", 255).nullable()
        val salt = varchar("salt", 255).nullable()
        val email = varchar("email", 255).nullable()
        val phone = varchar("phone", 20)
        val clientIp = varchar("client_ip", 255).nullable()
        val loginAt = timestamp("login_at").nullable()
        val loginToken = varchar("login_token", 255).nullable()
        val avatar = varchar("avatar", 255).nullable()
        val status = short("status").default(1)
        val createdAt = timestamp("created_at").nullable()
        val updatedAt = timestamp("updated_at").nullable()
        val deletedAt = timestamp("deleted_at").nullable()
        val createdBy = varchar("created_by", 50).nullable()
        val updatedBy = varchar("updated_by", 50).nullable()
        val version = long("version").default(1)

        override val primaryKey = PrimaryKey(id)
    }

    init {
        transaction(database) {
            SchemaUtils.create(User)
        }
    }

    suspend fun create(user: UserSchema): Long = dbQuery {
        User
            .insert() {
                it[userCode] = user.userCode
                it[userNo] = user.userNo
                it[username] = user.username
                it[nickname] = user.nickname
                it[password] = user.password
                it[salt] = user.salt
                it[email] = user.email
                it[phone] = user.phone
                it[clientIp] = user.clientIp
                it[loginAt] = user.loginAt
                it[loginToken] = user.loginToken
                it[avatar] = user.avatar
                it[status] = user.status
                it[createdAt] = user.createdAt
                it[updatedAt] = user.updatedAt
                it[deletedAt] = user.deletedAt
                it[createdBy] = user.createdBy
                it[updatedBy] = user.updatedBy
                it[version] = 1
            }[User.id]
    }

    suspend fun getByPhone(phone: String): UserSchema? = dbQuery {
        User.selectAll().where { User.phone eq phone }
            .mapNotNull { it.toUserSchema() }
            .singleOrNull()
    }

    suspend fun getByCode(userCode: String): UserSchema? = dbQuery {
        User.selectAll().where { User.userCode eq userCode }
            .mapNotNull { it.toUserSchema() }
            .singleOrNull()
    }

    private fun ResultRow.toUserSchema(): UserSchema {
        return UserSchema(
            userCode = this[User.userCode],
            userNo = this[User.userNo],
            username = this[User.username],
            nickname = this[User.nickname],
            password = this[User.password],
            salt = this[User.salt],
            email = this[User.email],
            phone = this[User.phone],
            clientIp = this[User.clientIp],
            loginAt = this[User.loginAt],
            loginToken = this[User.loginToken],
            avatar = this[User.avatar],
            status = this[User.status],
            createdBy = this[User.createdBy],
            updatedBy = this[User.updatedBy],
            createdAt = this[User.createdAt],
            updatedAt = this[User.updatedAt],
            deletedAt = this[User.deletedAt],
        )
    }

    suspend fun update(user: UserSchema) = dbQuery {
        User.update({ User.userCode eq user.userCode }) {
            it[userCode] = user.userCode
            it[userNo] = user.userNo
            it[username] = user.username
            it[nickname] = user.nickname
            it[password] = user.password
            it[salt] = user.salt
            it[email] = user.email
            it[phone] = user.phone
            it[clientIp] = user.clientIp
            it[loginAt] = user.loginAt
            it[loginToken] = user.loginToken
            it[avatar] = user.avatar
            it[status] = user.status
            it[updatedAt] = user.updatedAt
            it[updatedBy] = user.updatedBy
        }
    }

    suspend fun delete(userCode: String) = dbQuery {
        User.update({ User.userCode eq userCode }) {
            it[status] = 3
            it[deletedAt] = Clock.System.now()
        }
    }

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}
