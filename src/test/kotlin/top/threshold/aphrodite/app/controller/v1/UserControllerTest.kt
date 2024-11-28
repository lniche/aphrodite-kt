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
import top.threshold.aphrodite.app.controller.v1.UserController.GetUserResponse
import top.threshold.aphrodite.app.entity.pojo.UserDO
import top.threshold.aphrodite.app.repository.IUserRepository
import top.threshold.aphrodite.pkg.utils.RedisUtil
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset

@WebMvcTest(UserController::class)
internal class UserControllerTest {
    @Autowired
    private val mockMvc: MockMvc? = null

    @MockBean
    private val mockUserRepository: IUserRepository? = null

    @MockBean
    private val mockRedisUtil: RedisUtil? = null

    @Test
    @Throws(Exception::class)
    fun testGetUser() {
        // Setup
        // Configure RedisUtil.getObj(...).
        val getUserResponse = GetUserResponse()
        getUserResponse.nickname = "nickname"
        getUserResponse.userNo = 0L
        getUserResponse.userCode = "userCode"
        getUserResponse.email = "email"
        getUserResponse.phone = "phone"
        Mockito.`when`<GetUserResponse?>(mockRedisUtil!!.getObj<GetUserResponse>("key", GetUserResponse::class.java))
            .thenReturn(getUserResponse)

        // Run the test and verify the results
        mockMvc!!.perform(
            MockMvcRequestBuilders.get("/v1/user/{userCode}", "userCode")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json("{}", true))
    }

    @Test
    @Throws(Exception::class)
    fun testGetUser_RedisUtilGetObjReturnsNull() {
        // Setup
        Mockito.`when`<GetUserResponse?>(mockRedisUtil!!.getObj<GetUserResponse>("key", GetUserResponse::class.java))
            .thenReturn(null)

        // Configure UserRepository.getByCode(...).
        val userDO = UserDO()
        userDO.deletedAt = OffsetDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0), ZoneOffset.UTC)
        userDO.userCode = "userCode"
        userDO.userNo = 0L
        userDO.username = "username"
        userDO.status = 0
        Mockito.`when`<UserDO?>(mockUserRepository!!.getByCode("code")).thenReturn(userDO)

        // Run the test and verify the results
        mockMvc!!.perform(
            MockMvcRequestBuilders.get("/v1/user/{userCode}", "userCode")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json("", true))

        // Confirm RedisUtil.set(...).
        val value = UserDO()
        value.deletedAt = OffsetDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0), ZoneOffset.UTC)
        value.userCode = "userCode"
        value.userNo = 0L
        value.username = "username"
        value.status = 0
        Mockito.verify<RedisUtil?>(mockRedisUtil).set("key", value, 60L)
    }

    @Test
    @Throws(Exception::class)
    fun testGetUser_UserRepositoryReturnsNull() {
        // Setup
        Mockito.`when`<GetUserResponse?>(mockRedisUtil!!.getObj<GetUserResponse>("key", GetUserResponse::class.java))
            .thenReturn(null)
        Mockito.`when`<UserDO?>(mockUserRepository!!.getByCode("code")).thenReturn(null)

        // Run the test and verify the results
        mockMvc!!.perform(
            MockMvcRequestBuilders.get("/v1/user/{userCode}", "userCode")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json("{}", true))
    }

    @Test
    @Throws(Exception::class)
    fun testUpdateUser() {
        // Setup
        // Configure UserRepository.getByCode(...).
        val userDO = UserDO()
        userDO.deletedAt = OffsetDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0), ZoneOffset.UTC)
        userDO.userCode = "userCode"
        userDO.userNo = 0L
        userDO.username = "username"
        userDO.status = 0
        Mockito.`when`<UserDO?>(mockUserRepository!!.getByCode("code")).thenReturn(userDO)

        // Run the test and verify the results
        mockMvc!!.perform(
            MockMvcRequestBuilders.put("/v1/user")
                .content("content").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json("{}", true))

        // Confirm UserRepository.updateById(...).
        val entity = UserDO()
        entity.deletedAt = OffsetDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0), ZoneOffset.UTC)
        entity.userCode = "userCode"
        entity.userNo = 0L
        entity.username = "username"
        entity.status = 0
        Mockito.verify<IUserRepository?>(mockUserRepository).updateById(entity)
    }

    @Test
    @Throws(Exception::class)
    fun testUpdateUser_UserRepositoryGetByCodeReturnsNull() {
        // Setup
        Mockito.`when`<UserDO?>(mockUserRepository!!.getByCode("code")).thenReturn(null)

        // Run the test and verify the results
        mockMvc!!.perform(
            MockMvcRequestBuilders.put("/v1/user")
                .content("content").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json("", true))
    }

    @Test
    @Throws(Exception::class)
    fun testDeleteUser() {
        // Setup
        // Configure UserRepository.getByCode(...).
        val userDO = UserDO()
        userDO.deletedAt = OffsetDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0), ZoneOffset.UTC)
        userDO.userCode = "userCode"
        userDO.userNo = 0L
        userDO.username = "username"
        userDO.status = 0
        Mockito.`when`<UserDO?>(mockUserRepository!!.getByCode("code")).thenReturn(userDO)

        // Run the test and verify the results
        mockMvc!!.perform(
            MockMvcRequestBuilders.delete("/v1/user")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json("{}", true))

        // Confirm UserRepository.updateById(...).
        val entity = UserDO()
        entity.deletedAt = OffsetDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0), ZoneOffset.UTC)
        entity.userCode = "userCode"
        entity.userNo = 0L
        entity.username = "username"
        entity.status = 0
        Mockito.verify<IUserRepository?>(mockUserRepository).updateById(entity)
    }

    @Test
    @Throws(Exception::class)
    fun testDeleteUser_UserRepositoryGetByCodeReturnsNull() {
        // Setup
        Mockito.`when`<UserDO?>(mockUserRepository!!.getByCode("code")).thenReturn(null)

        // Run the test and verify the results
        mockMvc!!.perform(
            MockMvcRequestBuilders.delete("/v1/user")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json("", true))
    }
}
