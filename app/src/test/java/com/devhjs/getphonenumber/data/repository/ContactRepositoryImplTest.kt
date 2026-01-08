package com.devhjs.getphonenumber.data.repository

import com.devhjs.getphonenumber.data.datasource.ContactDataSource
import com.devhjs.getphonenumber.data.local.dao.FavoriteDao
import com.devhjs.getphonenumber.data.local.entity.FavoriteEntity
import com.devhjs.getphonenumber.domain.model.Contact
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ContactRepositoryImplTest {

    private val contactDataSource: ContactDataSource = mockk()
    private val favoriteDao: FavoriteDao = mockk(relaxed = true)
    private val repository = ContactRepositoryImpl(contactDataSource, favoriteDao)

    @Test
    fun `getContactsFlow combines system contacts and favorites correctly`() = runTest {
        // Given
        val systemContacts = listOf(
            Contact(id = "1", name = "A", phoneNumber = "010-1111-1111", isFavorite = false),
            Contact(id = "2", name = "B", phoneNumber = "010-2222-2222", isFavorite = false)
        )
        val favoriteIds = listOf("1")

        every { contactDataSource.getSystemContacts() } returns flowOf(systemContacts)
        every { favoriteDao.getFavoriteIds() } returns flowOf(favoriteIds)

        // When
        repository.getContactsFlow().collect { contacts ->
            // Then
            assertEquals(2, contacts.size)
            
            // ID "1" should be favorite
            val contactA = contacts.find { it.id == "1" }
            assertTrue(contactA!!.isFavorite)
            
            // ID "2" should not be favorite
            val contactB = contacts.find { it.id == "2" }
            assertFalse(contactB!!.isFavorite)
        }
    }

    @Test
    fun `toggleFavorite inserts when isFavorite is true`() = runTest {
        // Given
        val contactId = "1"
        val isFavorite = true

        // When
        repository.toggleFavorite(contactId, isFavorite)

        // Then
        coVerify { favoriteDao.insert(FavoriteEntity(contactId)) }
    }

    @Test
    fun `toggleFavorite deletes when isFavorite is false`() = runTest {
        // Given
        val contactId = "1"
        val isFavorite = false

        // When
        repository.toggleFavorite(contactId, isFavorite)

        // Then
        coVerify { favoriteDao.delete(contactId) }
    }
}
