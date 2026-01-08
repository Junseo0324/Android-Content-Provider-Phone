package com.devhjs.getphonenumber.domain.usecase

import com.devhjs.getphonenumber.domain.model.Contact
import com.devhjs.getphonenumber.util.KoreanChosung
import javax.inject.Inject

class SearchContactsUseCase @Inject constructor() {

    operator fun invoke(query: String, contacts: List<Contact>): List<Contact> {
        if (query.isBlank()) return contacts
        
        return contacts.filter { contact ->
            KoreanChosung.match(contact.name, query) || 
            contact.phoneNumber.contains(query)
        }
    }
}
