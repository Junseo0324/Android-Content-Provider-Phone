package com.devhjs.getphonenumber.domain.usecase

import com.devhjs.getphonenumber.domain.model.Contact
import javax.inject.Inject

class SearchContactsUseCase @Inject constructor() {
    
    /**
     * Filters the list of contacts based on the search query.
     * Supports Korean Initial Consonant (Cho-sung) search.
     */
    operator fun invoke(query: String, contacts: List<Contact>): List<Contact> {
        if (query.isBlank()) return contacts
        
        // TODO: Implement Cho-sung search logic here or delegate to a utility
        return contacts.filter { contact ->
            contact.name.contains(query, ignoreCase = true) || 
            contact.phoneNumber.contains(query)
            // Add Cho-sung match logic later
        }
    }
}
