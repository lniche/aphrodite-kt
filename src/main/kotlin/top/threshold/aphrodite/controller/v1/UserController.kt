package top.threshold.aphrodite.controller.v1;

import cn.hutool.core.bean.BeanUtil
import cn.hutool.core.util.DesensitizedUtil
import cn.hutool.core.util.StrUtil
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.Parameters
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import lombok.Data
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import top.threshold.aphrodite.constant.CacheKey
import top.threshold.aphrodite.controller.BaseController
import top.threshold.aphrodite.entity.Result
import top.threshold.aphrodite.repository.IUserRepository
import top.threshold.aphrodite.utils.RedisUtil
import java.time.OffsetDateTime


/**
 * <p>
 *  User Controller
 * </p>
 *
 * @author qingshan
 * @since 2024-09-29
 */
@RestController
@RequestMapping("/v1/user")
@Tag(name = "User Module")
class UserController(
    val userRepository: IUserRepository,
    val redisUtil: RedisUtil
) : BaseController() {

    @Data
    class GetUserResp {
        /**
         * Nickname
         */
        @field:Schema(description = "User nickname", example = "john")
        var nickname: String? = null

        /**
         * User Number
         */
        @field:Schema(description = "User Number", example = "A8000")
        var userNo: Long? = null

        /**
         * User Code
         */
        @field:Schema(description = "User Code", example = "100000")
        var userCode: String? = null

        /**
         * Email
         */
        @field:Schema(description = "User email", example = "john@example.com")
        var email: String? = null

        /**
         * Phone Number
         */
        @field:Schema(description = "User phone number", example = "13800138000")
        var phone: String? = null
    }

    @Operation(
        summary = "Get User",
        description = "Retrieve user information",
        security = [SecurityRequirement(name = "Authorization")]
    )
    @Parameters(
        Parameter(name = "userCode", description = "User Number", `in` = ParameterIn.PATH)
    )
    @GetMapping("/{userCode}")
    fun getUser(@PathVariable userCode: String): Result<GetUserResp?> {
        val actualUserCode = if (StrUtil.isBlank(userCode)) {
            loginUid()
        } else {
            userCode
        }
        val redisKey = CacheKey.USER + actualUserCode
        val getUserResp = redisUtil.getObj(redisKey, GetUserResp::class.java)

        if (getUserResp == null) {
            val userDO = userRepository.getByCode(actualUserCode)
            userDO?.let {
                redisUtil[redisKey, it] = 60
                return Result.ok(GetUserResp().apply { BeanUtil.copyProperties(it, this) })
            }
        }

        getUserResp?.apply {
            email = DesensitizedUtil.email(email)
            phone = DesensitizedUtil.mobilePhone(phone)
        }

        return Result.ok(getUserResp)
    }

    @Data
    @Schema(description = "Request object for updating user information")
    class UpdateUserReq {
        /**
         * Nickname
         */
        @field:Schema(description = "User nickname", example = "john", required = false)
        var nickname: String? = null

        /**
         * Email
         */
        @field:Schema(description = "User email", example = "john@example.com", required = false)
        var email: String? = null
    }

    @Operation(
        summary = "Update User",
        description = "Update user information based on user details",
        security = [SecurityRequirement(name = "Authorization")]
    )
    @PutMapping("")
    fun updateUser(
        @Parameter(description = "Update user information", required = true)
        @Validated @RequestBody updateUserReq: UpdateUserReq
    ): Result<Void> {
        val userDO = userRepository.getByCode(loginUid()) ?: return Result.err("User does not exist")
        BeanUtil.copyProperties(updateUserReq, userDO, "userCode")
        userRepository.updateById(userDO)
        return Result.ok()
    }

    @Operation(
        summary = "Delete User",
        description = "Delete user if obtained through token",
        security = [SecurityRequirement(name = "Authorization")]
    )
    @DeleteMapping("")
    fun deleteUser(): Result<Void> {
        val userDO = userRepository.getByCode(loginUid()) ?: return Result.err("User is not valid")
        userDO.status = 3
        userDO.deletedAt = OffsetDateTime.now()
        userRepository.updateById(userDO)
        return Result.ok()
    }
}
