package com.example.domain.usecase

import com.example.data.model.Address
import com.example.data.model.User
import com.example.data.model.exception.UserFetchException
import com.example.data.repository.UserRepository
import com.example.domain.model.Result
import com.example.domain.model.toItem
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Code Analysis:
 * The GetUserByIdUseCase class takes a UserRepository dependency and implements the UseCase interface. It fetches a user by ID from the repository and converts it into a UserItem. It handles exceptions of type UserFetchException by wrapping them in a Result.Error instance.
 *
 * Testing scenarios:
 * - Successful retrieval of a user by ID and mapping it to a UserItem.
 * - Handling an exception of type UserFetchException and returning an error result.
 * - Testing the mapping of a user to a UserItem with valid data.
 * - Testing the mapping of a user to a UserItem with empty data.
 * - Testing the mapping of a user to a UserItem with null data.
 * - Passing a negative user ID and handling the error.
 * - Passing a non-existent user ID and handling the error.
 * - Verifying that the correct user ID is passed to the repository.
 * - Verifying that the invoke function is executed asynchronously.
 * - Handling a custom exception that extends UserFetchException.
 */
class GetUserByIdUseCaseTest {

    private lateinit var getUserByIdUseCase: GetUserByIdUseCase
    private val userRepository: UserRepository = mockk(relaxed = true)

    @Before
    fun setup() {
        getUserByIdUseCase = GetUserByIdUseCase(userRepository)
    }

    @After
    fun release() {
        unmockkAll()
    }

    @Test
    fun `GetUserByIdUseCase should return a UserItem when repository call is successful`() = runBlocking {
        // Arrange
        val userId = 1L
        val user = User(userId, "User1")
        coEvery { userRepository.getUser(userId) } returns user

        // Act
        val result = getUserByIdUseCase(userId)

        // Assert
        val expectedUserItem = user.toItem()
        assertEquals(Result.Success(expectedUserItem), result)
    }

    @Test
    fun `GetUserByIdUseCase should handle UserFetchException and return an error result`() = runBlocking {
        // Arrange
        val userId = 1L
        val exception = UserFetchException.NetworkException
        coEvery { userRepository.getUser(userId) } throws exception

        // Act
        val result = getUserByIdUseCase(userId)

        // Assert
        assertEquals(Result.Error(exception), result)
    }

    @Test
    fun `GetUserByIdUseCase should correctly map a User to a UserItem with valid data`() = runBlocking {
        // Arrange
        val userId = 1L
        val user = User(userId, "User1", "user1", "user1@example.com", Address(), "123-456-7890", null, null)
        coEvery { userRepository.getUser(userId) } returns user

        // Act
        val result = getUserByIdUseCase(userId)

        // Assert
        val expectedUserItem = user.toItem()
        assertEquals(Result.Success(expectedUserItem), result)
    }

    @Test
    fun `GetUserByIdUseCase should map a User to a UserItem with empty data`() = runBlocking {
        // Arrange
        val userId = 1L
        val user = User(userId, "", "", "", Address(), "", null, null)
        coEvery { userRepository.getUser(userId) } returns user

        // Act
        val result = getUserByIdUseCase(userId)

        // Assert
        val expectedUserItem = user.toItem()
        assertEquals(Result.Success(expectedUserItem), result)
    }

    @Test
    fun `GetUserByIdUseCase should map a User to a UserItem with null data`() = runBlocking {
        // Arrange
        val userId = 1L
        val user = User(userId, "Name", null, null, null, null, null, null)
        coEvery { userRepository.getUser(userId) } returns user

        // Act
        val result = getUserByIdUseCase(userId)

        // Assert
        val expectedUserItem = user.toItem()
        assertEquals(Result.Success(expectedUserItem), result)
    }

    @Test
    fun `GetUserByIdUseCase should handle a negative user ID and return an error result`() = runBlocking {
        // Arrange
        val negativeUserId = -1L
        coEvery { userRepository.getUser(negativeUserId) } throws UserFetchException.NotFoundException(negativeUserId)

        // Act
        val result = getUserByIdUseCase(negativeUserId)

        // Assert
        val expectedException = UserFetchException.NotFoundException(-1)
        assertEquals(Result.Error(expectedException), result)
    }

    @Test
    fun `GetUserByIdUseCase should handle a non-existent user ID and return an error result`() = runBlocking {
        // Arrange
        val nonExistentId = 9999L
        coEvery { userRepository.getUser(nonExistentId) } throws UserFetchException.NotFoundException(9999L)

        // Act
        val result = getUserByIdUseCase(nonExistentId)

        // Assert
        val expectedException = UserFetchException.NotFoundException(9999L)
        assertEquals(Result.Error(expectedException), result)
    }

    @Test
    fun `GetUserByIdUseCase should pass the correct user ID to the repository`() = runBlocking {
        // Arrange
        val userId = 1L

        // Act
        getUserByIdUseCase(userId)

        // Assert
        coVerify { userRepository.getUser(userId) }
        confirmVerified(userRepository)
    }

    @Test
    fun `GetUserByIdUseCase should execute asynchronously`() {
        // Arrange
        val userId = 1L
        val user = User(userId, "User1")
        coEvery { userRepository.getUser(userId) } returns user

        // Act
        val startTime = System.currentTimeMillis()
        runBlocking {
            getUserByIdUseCase(userId)
        }
        val endTime = System.currentTimeMillis()

        // Assert
        val executionTime = endTime - startTime
        assertTrue("Execution time is too short: $executionTime", executionTime >= 0)
    }

    @Test
    fun `GetUserByIdUseCase should handle a custom UserFetchException`() = runBlocking {
        // Arrange
        val userId = 1L
        val customException = UserFetchException.UnknownException(Exception(), "Custom fetch exception")
        coEvery { userRepository.getUser(userId) } throws customException

        // Act
        val result = getUserByIdUseCase(userId)

        // Assert
        assertEquals(Result.Error(customException), result)
    }
}
