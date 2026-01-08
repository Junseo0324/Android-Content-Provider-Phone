package com.devhjs.getphonenumber.domain.usecase

import com.devhjs.getphonenumber.domain.model.Contact
import com.devhjs.getphonenumber.domain.repository.ContactRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetContactsUseCase @Inject constructor(
    private val repository: ContactRepository
) {
    operator fun invoke(): Flow<List<Contact>> {
        return repository.getContactsFlow()
    }
}
