package com.devhjs.getphonenumber.domain.usecase

import com.devhjs.getphonenumber.domain.model.Contact
import com.devhjs.getphonenumber.domain.repository.ContactRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetContactsUseCaseTest {

    private val repository: ContactRepository = mockk()
    private val useCase = GetContactsUseCase(repository)

    @Test
    fun `invoke calls repository getContactsFlow`() = runTest {
        // Given
        val contacts = listOf(
            Contact(id = "1", name = "Kim", phoneNumber = "010-1111-1111"),
            Contact(id = "2", name = "Lee", phoneNumber = "010-2222-2222")
        )
        every { repository.getContactsFlow() } returns flowOf(contacts)

        // When
        val result = useCase()

        // Then
        result.collect { collectedContacts ->
            assertEquals(contacts, collectedContacts)
        }
        verify(exactly = 1) { repository.getContactsFlow() }
    }
}
