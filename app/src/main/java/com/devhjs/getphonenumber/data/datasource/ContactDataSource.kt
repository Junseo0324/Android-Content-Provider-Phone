package com.devhjs.getphonenumber.data.datasource

import com.devhjs.getphonenumber.domain.model.Contact

interface ContactDataSource {
    suspend fun getSystemContacts(): List<Contact>
}
