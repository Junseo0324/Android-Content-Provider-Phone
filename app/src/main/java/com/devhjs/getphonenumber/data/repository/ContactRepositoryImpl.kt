package com.devhjs.getphonenumber.data.repository

import com.devhjs.getphonenumber.data.datasource.ContactDataSource
import com.devhjs.getphonenumber.data.local.dao.FavoriteDao
import com.devhjs.getphonenumber.data.local.entity.FavoriteEntity
import com.devhjs.getphonenumber.domain.model.Contact
import com.devhjs.getphonenumber.domain.repository.ContactRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ContactRepositoryImpl @Inject constructor(
    private val contactDataSource: ContactDataSource,
    private val favoriteDao: FavoriteDao
) : ContactRepository {

    override fun getContactsFlow(): Flow<List<Contact>> {
        val systemContactsFlow = flow {
            emit(contactDataSource.getSystemContacts())
        }
        val favoriteIdsFlow = favoriteDao.getFavoriteIds()

        return systemContactsFlow.combine(favoriteIdsFlow) { contacts, favoriteIds ->
            val favoriteIdSet = favoriteIds.toSet()
            contacts.map { contact ->
                contact.copy(isFavorite = favoriteIdSet.contains(contact.id))
            }
        }
    }

    override suspend fun toggleFavorite(contactId: String, isFavorite: Boolean) {
        if (isFavorite) {
            favoriteDao.insert(FavoriteEntity(contactId))
        } else {
            favoriteDao.delete(contactId)
        }
    }
}
