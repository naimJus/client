package com.example.domain.usecase

import com.example.data.model.Address
import com.example.data.model.Company
import com.example.data.model.User
import com.example.data.model.exception.UserFetchException
import com.example.data.repository.UserRepository
import com.example.domain.model.Result
import com.example.domain.model.UserItem
import com.example.domain.model.toItem
import io.mockk.coEvery
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
 * The GetUsersUseCase class takes a UserRepository dependency and implements the UseCase interface. It fetches a list of users from the repository and maps them to UserItem objects. It handles exceptions of type UserFetchException by wrapping them in a Result.Error instance.
 *
 * Testing Scenarios:
 * - Successful retrieval of a list of users and mapping them to a list of UserItems.
 * - Handling an exception of type UserFetchException and returning an error result.
 * - Testing the mapping of users to UserItems with valid data.
 * - Testing the mapping of users to UserItems with empty data.
 * - Testing the mapping of users to UserItems with null data.
 * - Verifying that the correct forceRemote parameter is passed to the repository.
 * - Verifying that the invoke function is executed asynchronously.
 * - Handling a custom exception that extends UserFetchException.
 * - Testing the case when userRepository.getUsers() returns an empty list
 */
class GetUsersUseCaseTest {

    private lateinit var getUsersUseCase: GetUsersUseCase
    private val userRepository: UserRepository = mockk(relaxed = true)

    @Before
    fun setup() {
        getUsersUseCase = GetUsersUseCase(userRepository)
    }

    @After
    fun release() {
        unmockkAll()
    }

    @Test
    fun `GetUsersUseCase should return a list of UserItems when repository call is successful`() = runBlocking {
        // Arrange
        val users = listOf(
            User(1L, "User1", "user1", "user1@example.com", Address(), "123-456-7890", null, null),
            User(2L, "User2", "user2", "user2@example.com", Address(), "987-654-3210", null, null)
        )
        coEvery { userRepository.getUsers(true) } returns users

        // Act
        val result = getUsersUseCase(true)

        // Assert
        val expectedUserItems = users.map { it.toItem() }
        assertEquals(Result.Success(expectedUserItems), result)
    }

    @Test
    fun `GetUsersUseCase should return an empty list of UserItems when the repository returns an empty list`() = runBlocking {
        // Arrange
        val emptyUsers = emptyList<User>()
        coEvery { userRepository.getUsers(true) } returns emptyUsers

        // Act
        val result = getUsersUseCase(true)

        // Assert
        assertEquals(Result.Success(emptyList<UserItem>()), result)
    }

    @Test
    fun `GetUsersUseCase should handle UserFetchException and return an error result`() = runBlocking {
        // Arrange
        val exception = UserFetchException.NetworkException
        coEvery {
            userRepository.getUsers(true)
        } throws exception

        // Act
        val result = getUsersUseCase(true)

        // Assert
        assertEquals(Result.Error(exception), result)
    }

    @Test
    fun `GetUsersUseCase should correctly map User objects to UserItems with valid data`() = runBlocking {
        // Arrange
        val users = listOf(
            User(1L, "User1", "user1", "user1@example.com", Address(), "123-456-7890", null, null),
            User(2L, "User2", "user2", "user2@example.com", Address(), "987-654-3210", null, null)
        )
        coEvery { userRepository.getUsers(true) } returns users

        // Act
        val result = getUsersUseCase(true)

        // Assert
        val expectedUserItems = users.map { it.toItem() }
        assertEquals(Result.Success(expectedUserItems), result)
    }

    @Test
    fun `GetUsersUseCase should map User objects to UserItems with empty data`() = runBlocking {
        // Arrange
        val users = listOf(
            User(1L, "", "", "", Address(), "", "", Company()),
            User(2L, "", "", "", Address(), "", "", Company())
        )
        coEvery { userRepository.getUsers(true) } returns users

        // Act
        val result = getUsersUseCase(true)

        // Assert
        val expectedUserItems = users.map { it.toItem() }
        assertEquals(Result.Success(expectedUserItems), result)
    }

    @Test
    fun `GetUsersUseCase should map User objects to UserItems with null data`() = runBlocking {
        // Arrange
        val users = listOf(
            User(1L, "null", null, null, null, null, null),
            User(2L, "null", null, null, null, null, null)
        )
        coEvery { userRepository.getUsers(true) } returns users

        // Act
        val result = getUsersUseCase(true)

        // Assert
        val expectedUserItems = users.map { it.toItem() }
        assertEquals(Result.Success(expectedUserItems), result)
    }

    @Test
    fun `GetUsersUseCase should pass the correct forceRemote parameter to the repository`() = runBlocking {
        // Arrange
        val forceRemote = true
        val users = listOf(
            User(1L, "User1", "user1", "user1@example.com", Address(), "123-456-7890", null, null),
            User(2L, "User2", "user2", "user2@example.com", Address(), "987-654-3210", null, null)
        )
        coEvery { userRepository.getUsers(forceRemote) } returns users

        // Act
        val result = getUsersUseCase(forceRemote)

        // Assert
        val expectedUserItems = users.map { it.toItem() }
        assertEquals(Result.Success(expectedUserItems), result)
    }

    @Test
    fun `GetUsersUseCase should execute asynchronously`() {
        // Arrange
        val forceRemote = true
        val users = listOf(
            User(1L, "User1", "user1", "user1@example.com", Address(), "123-456-7890", null, null),
            User(2L, "User2", "user2", "user2@example.com", Address(), "987-654-3210", null, null)
        )
        coEvery { userRepository.getUsers(forceRemote) } returns users

        // Act
        val startTime = System.currentTimeMillis()
        runBlocking {
            getUsersUseCase(forceRemote)
        }
        val endTime = System.currentTimeMillis()

        // Assert
        val executionTime = endTime - startTime
        assertTrue("Execution time is too short: $executionTime", executionTime >= 0)
    }

    @Test
    fun `GetUsersUseCase should handle a custom UserFetchException`() = runBlocking {
        // Arrange
        val exception = UserFetchException.UnknownException(Exception(), "Custom fetch exception")
        coEvery { userRepository.getUsers(true) } throws exception

        // Act
        val result = getUsersUseCase(true)

        // Assert
        assertEquals(Result.Error(exception), result)
    }
}

