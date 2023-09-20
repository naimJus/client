package com.example.data.repository

import com.example.data.datasource.UserDataSource
import com.example.data.model.User
import com.example.data.model.exception.UserFetchException
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.net.UnknownHostException

/**
 * Code Analysis:
 * The BankUserRepository class implements the UserRepository interface and handles the retrieval of user data. It maintains a cache of users and allows fetching users either from the remote data source or from the cache based on the forceRemote parameter. It also handles exceptions, including custom exceptions that extend UserFetchException.
 *
 * Testing Scenarios:
 * - Successful retrieval of users from the remote data source when forceRemote is true.
 * - Successful retrieval of users from the cache when forceRemote is false and the cache is available.
 * - Handling a network exception when fetching remote users.
 * - Handling a cache not available exception when the cache is empty and forceRemote is false.
 * - Retrieving a user by ID from the cache.
 * - Handling a not found exception when retrieving a user by ID from the cache.
 * - Handling a network exception when fetching remote users and converting it to a UserFetchException.NetworkException.
 * - Handling an unknown exception when fetching remote users and converting it to a UserFetchException.UnknownException.
 * - Testing the update of the cache after fetching remote users.
 */
@RunWith(JUnit4::class)
class BankUserRepositoryTest {

    private lateinit var userRepository: BankUserRepository
    private val remoteDataSource: UserDataSource = mockk(relaxed = true)

    @Before
    fun setup() {
        userRepository = BankUserRepository(remoteDataSource)
    }

    @After
    fun release() {
        unmockkAll()
    }

    @Test
    fun `BankUserRepository should retrieve remote users when forceRemote is true`() = runTest {
        // Arrange
        val remoteUsers = listOf(User(1L, "User1"), User(2L, "User2"))
        coEvery { remoteDataSource.getUsers() } returns remoteUsers

        // Act
        val result = userRepository.getUsers(forceRemote = true)

        // Assert
        assertEquals(remoteUsers, result)
    }

    @Test
    fun `BankUserRepository should retrieve cached users when forceRemote is false and cache is available`() = runTest {
        // Arrange
        val cachedUsers = listOf(User(1L, "User1"), User(2L, "User2"))
        userRepository.cachedUsers.addAll(cachedUsers)

        // Act
        val result = userRepository.getUsers(forceRemote = false)

        // Assert
        assertEquals(cachedUsers, result)
    }

    @Test(expected = UserFetchException.NetworkException::class)
    fun `BankUserRepository should handle a network exception when fetching remote users`() = runTest {
        // Arrange
        coEvery { remoteDataSource.getUsers() } throws UnknownHostException()

        // Act
        userRepository.getUsers(forceRemote = true)
    }

    @Test(expected = UserFetchException.CacheNotAvailableException::class)
    fun `BankUserRepository should handle a cache not available exception when cache is empty and forceRemote is false`() = runTest {
        // Act
        userRepository.getUsers(forceRemote = false)
    }

    @Test
    fun `BankUserRepository should retrieve a user by ID from the cache`() = runTest {
        // Arrange
        val userId = 1L
        val cachedUsers = listOf(User(1L, "User1"), User(2L, "User2"))
        userRepository.cachedUsers.addAll(cachedUsers)

        // Act
        val result = userRepository.getUser(userId)

        // Assert
        val expectedUser = cachedUsers.firstOrNull { it.id == userId }
        assertEquals(expectedUser, result)
    }

    @Test(expected = UserFetchException.NotFoundException::class)
    fun `BankUserRepository should handle a not found exception when retrieving a user by ID from the cache`() = runTest {
        // Arrange
        val userId = 1L

        // Act
        userRepository.getUser(userId)
    }

    @Test(expected = UserFetchException.NetworkException::class)
    fun `BankUserRepository should handle a network exception and convert it to NetworkException`() = runTest {
        // Arrange
        coEvery { remoteDataSource.getUsers() } throws UnknownHostException()

        // Act
        userRepository.getUsers(forceRemote = true)
    }

    @Test(expected = UserFetchException.UnknownException::class)
    fun `BankUserRepository should handle an unknown exception and convert it to UnknownException`() = runTest {
        // Arrange
        coEvery { remoteDataSource.getUsers() } throws Exception("Unknown error")

        // Act
        userRepository.getUsers(forceRemote = true)
    }

    @Test
    fun `BankUserRepository should update the cache after fetching remote users`() = runTest {
        // Arrange
        val remoteUsers = listOf(User(1L, "User1"), User(2L, "User2"))
        coEvery { remoteDataSource.getUsers() } returns remoteUsers

        // Act
        userRepository.getUsers(forceRemote = true)

        // Assert
        assertEquals(remoteUsers, userRepository.cachedUsers)
    }
}
