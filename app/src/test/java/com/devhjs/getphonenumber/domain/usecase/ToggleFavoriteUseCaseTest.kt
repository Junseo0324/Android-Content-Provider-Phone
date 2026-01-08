package com.devhjs.getphonenumber.domain.usecase

import com.devhjs.getphonenumber.domain.repository.ContactRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class ToggleFavoriteUseCaseTest {

    private val repository: ContactRepository = mockk(relaxed = true)
    private val useCase = ToggleFavoriteUseCase(repository)

    @Test
    fun `invoke calls repository toggleFavorite`() = runTest {
        // Given
        val contactId = "123"
        val isFavorite = true
        coEvery { repository.toggleFavorite(contactId, isFavorite) } returns Unit

        // When
        useCase(contactId, isFavorite)

        // Then
        coVerify(exactly = 1) { repository.toggleFavorite(contactId, isFavorite) }
    }
}
