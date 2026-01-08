package com.devhjs.getphonenumber.domain.repository

import com.devhjs.getphonenumber.domain.model.Contact
import kotlinx.coroutines.flow.Flow

interface ContactRepository {
    suspend fun toggleFavorite(contactId: String, isFavorite: Boolean)
    fun getContactsFlow(): Flow<List<Contact>>
}
