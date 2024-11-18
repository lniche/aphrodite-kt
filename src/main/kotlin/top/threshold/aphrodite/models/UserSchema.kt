package top.threshold.aphrodite.models

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class UserSchema(
    val userCode: String,
    val userNo: Long,
    val username: String?,
    val nickname: String?,
    val password: String?,
    val salt: String?,
    val email: String?,
    val phone: String,
    val openId: String?,
    val clientIp: String?,
    val loginAt: Instant?,
    val loginToken: String?,
    val avatar: String?,
    val status: Short = 1,
    val createdAt: Instant?,
    val updatedAt: Instant?,
    val deletedAt: Instant?,
    val createdBy: String?,
    val updatedBy: String?,
    val version: Long = 1
)


