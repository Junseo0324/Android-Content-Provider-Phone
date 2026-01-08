package com.devhjs.getphonenumber.domain.model

data class Contact(
    val id: String,
    val name: String,
    val phoneNumber: String,
    val isFavorite: Boolean = false
)
