package top.threshold.aphrodite.models

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class UserSchema(
    val userCode: String,
    val userNo: Long,
    val username: String? = null,
    val nickname: String? = null,
    val password: String? = null,
    val salt: String? = null,
    val email: String? = null,
    val phone: String,
    val clientIp: String? = null,
    val loginAt: Instant? = null,
    val loginToken: String? = null,
    val avatar: String? = null,
    val status: Short = 1,
    val createdAt: Instant? = null,
    val updatedAt: Instant? = null,
    val deletedAt: Instant? = null,
    val createdBy: String? = null,
    val updatedBy: String? = null,
    val version: Long? = null,
)


