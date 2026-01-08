package com.devhjs.getphonenumber.data.datasource

import com.devhjs.getphonenumber.domain.model.Contact

import kotlinx.coroutines.flow.Flow

interface ContactDataSource {
    fun getSystemContacts(): Flow<List<Contact>>
}
