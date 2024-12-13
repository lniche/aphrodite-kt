package top.threshold.aphrodite.app.controller.v1

import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import top.threshold.aphrodite.app.model.pojo.UserDO
import top.threshold.aphrodite.app.repository.IUserRepository
import top.threshold.aphrodite.pkg.constant.CacheKey
import top.threshold.aphrodite.pkg.utils.RedisUtil
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import kotlin.Throws

@WebMvcTest(AuthController::class)
internal class AuthControllerTest {
    @Autowired
    private val mockMvc: MockMvc? = null

    @MockBean
    private val mockRedisUtil: RedisUtil? = null

    @MockBean
    private val mockUserRepository: IUserRepository? = null

    @Test
    @Throws(Exception::class)
    fun testSendVerifyCode() {
        // Setup
        Mockito.`when`<Boolean?>(mockRedisUtil!!.hasKey("key")).thenReturn(false)

        // Run the test and verify the results
        mockMvc!!.perform(
            MockMvcRequestBuilders.post("/v1/send-code")
                .content("content").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json("{}", true))
        Mockito.verify<RedisUtil?>(mockRedisUtil).setStr("key", "value", 60L)
    }

    @Test
    @Throws(Exception::class)
    fun testSendVerifyCode_RedisUtilHasKeyReturnsTrue() {
        // Setup
        Mockito.`when`<Boolean?>(mockRedisUtil!!.hasKey("key")).thenReturn(true)

        // Run the test and verify the results
        mockMvc!!.perform(
            MockMvcRequestBuilders.post("/v1/send-code")
                .content("content").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json("{}", true))
    }

    @Test
    @Throws(Exception::class)
    fun testLogin() {
        // Setup
        Mockito.`when`<String?>(mockRedisUtil!!.getStr("key")).thenReturn("result")

        // Configure UserRepository.getByPhone(...).
        val userDO = UserDO()
        userDO.userCode = "userCode"
        userDO.userNo = 0L
        userDO.nickname = "nickname"
        userDO.phone = "phone"
        userDO.clientIp = "clientIp"
        userDO.loginAt = OffsetDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0), ZoneOffset.UTC)
        userDO.loginToken = "loginToken"
        Mockito.`when`<UserDO?>(mockUserRepository!!.getByPhone("phone")).thenReturn(userDO)

        Mockito.`when`<Long?>(mockRedisUtil.nextId(CacheKey.NEXT_UNO)).thenReturn(0L)

        // Run the test and verify the results
        mockMvc!!.perform(
            MockMvcRequestBuilders.post("/v1/login")
                .content("content").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json("{}", true))

        // Confirm UserRepository.save(...).
        val entity = UserDO()
        entity.userCode = "userCode"
        entity.userNo = 0L
        entity.nickname = "nickname"
        entity.phone = "phone"
        entity.clientIp = "clientIp"
        entity.loginAt = OffsetDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0), ZoneOffset.UTC)
        entity.loginToken = "loginToken"
        Mockito.verify<IUserRepository?>(mockUserRepository).save(entity)

        // Confirm UserRepository.updateById(...).
        val entity1 = UserDO()
        entity1.userCode = "userCode"
        entity1.userNo = 0L
        entity1.nickname = "nickname"
        entity1.phone = "phone"
        entity1.clientIp = "clientIp"
        entity1.loginAt = OffsetDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0), ZoneOffset.UTC)
        entity1.loginToken = "loginToken"
        Mockito.verify<IUserRepository?>(mockUserRepository).updateById(entity1)
        Mockito.verify<RedisUtil?>(mockRedisUtil).del("keys")
    }

    @Test
    @Throws(Exception::class)
    fun testLogout() {
        // Setup
        // Configure UserRepository.getByCode(...).
        val userDO = UserDO()
        userDO.userCode = "userCode"
        userDO.userNo = 0L
        userDO.nickname = "nickname"
        userDO.phone = "phone"
        userDO.clientIp = "clientIp"
        userDO.loginAt = OffsetDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0), ZoneOffset.UTC)
        userDO.loginToken = "loginToken"
        Mockito.`when`<UserDO?>(mockUserRepository!!.getByCode("code")).thenReturn(userDO)

        // Run the test and verify the results
        mockMvc!!.perform(
            MockMvcRequestBuilders.post("/v1/logout")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json("{}", true))

        // Confirm UserRepository.updateById(...).
        val entity = UserDO()
        entity.userCode = "userCode"
        entity.userNo = 0L
        entity.nickname = "nickname"
        entity.phone = "phone"
        entity.clientIp = "clientIp"
        entity.loginAt = OffsetDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0), ZoneOffset.UTC)
        entity.loginToken = "loginToken"
        Mockito.verify<IUserRepository?>(mockUserRepository).updateById(entity)
    }

    @Test
    @Throws(Exception::class)
    fun testLogout_UserRepositoryGetByCodeReturnsNull() {
        // Setup
        Mockito.`when`<UserDO?>(mockUserRepository!!.getByCode("code")).thenReturn(null)

        // Run the test and verify the results
        mockMvc!!.perform(
            MockMvcRequestBuilders.post("/v1/logout")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json("", true))
    }
}
