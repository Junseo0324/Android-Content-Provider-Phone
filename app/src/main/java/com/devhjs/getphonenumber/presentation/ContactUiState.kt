package com.devhjs.getphonenumber.presentation

import com.devhjs.getphonenumber.domain.model.Contact

data class ContactUiState(
    val contacts: List<Contact> = emptyList(),
    val searchQuery: String = ""
)
