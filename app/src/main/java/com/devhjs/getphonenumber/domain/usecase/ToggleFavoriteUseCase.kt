package com.devhjs.getphonenumber.domain.usecase

import com.devhjs.getphonenumber.domain.repository.ContactRepository
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val repository: ContactRepository
) {
    suspend operator fun invoke(id: String, isFavorite: Boolean) {
        repository.toggleFavorite(id, isFavorite)
    }
}
