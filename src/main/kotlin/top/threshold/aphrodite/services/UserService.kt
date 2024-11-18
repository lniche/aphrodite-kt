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
        val openId = varchar("open_id", 255).nullable()
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

        override val primaryKey = PrimaryKey(id) // Primary key on "id"
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
                it[openId] = user.openId
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
                it[version] = user.version
            }[User.id]
    }

    suspend fun read(id: Long): UserSchema? = dbQuery {
        User.selectAll().where { User.id eq id }
            .mapNotNull {
                UserSchema(
                    userCode = it[User.userCode],
                    userNo = it[User.userNo],
                    username = it[User.username],
                    nickname = it[User.nickname],
                    password = it[User.password],
                    salt = it[User.salt],
                    email = it[User.email],
                    phone = it[User.phone],
                    openId = it[User.openId],
                    clientIp = it[User.clientIp],
                    loginAt = it[User.loginAt],
                    loginToken = it[User.loginToken],
                    avatar = it[User.avatar],
                    status = it[User.status],
                    createdBy = it[User.createdBy],
                    updatedBy = it[User.updatedBy],
                    createdAt = it[User.createdAt],
                    updatedAt = it[User.updatedAt],
                    deletedAt = it[User.deletedAt],
                    version = it[User.version]
                )
            }
            .singleOrNull()
    }

    suspend fun update(id: Long, user: UserSchema) = dbQuery {
        User.update({ User.id eq id }) {
            it[userCode] = user.userCode
            it[userNo] = user.userNo
            it[username] = user.username
            it[nickname] = user.nickname
            it[password] = user.password
            it[salt] = user.salt
            it[email] = user.email
            it[phone] = user.phone
            it[openId] = user.openId
            it[clientIp] = user.clientIp
            it[loginAt] = user.loginAt
            it[loginToken] = user.loginToken
            it[avatar] = user.avatar
            it[status] = user.status
            it[updatedAt] = user.updatedAt
            it[updatedBy] = user.updatedBy
        }
    }

    suspend fun delete(id: Long) = dbQuery {
        User.update({ User.id eq id }) {
            it[status] = 3
            it[deletedAt] = Clock.System.now()
        }
    }

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}
