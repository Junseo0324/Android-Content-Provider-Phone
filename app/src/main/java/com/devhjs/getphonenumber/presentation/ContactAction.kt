package com.devhjs.getphonenumber.presentation

import com.devhjs.getphonenumber.domain.model.Contact

sealed interface ContactAction {
    data class OnSearchQueryChange(val query: String) : ContactAction
    data class OnToggleFavorite(val id: String, val isFavorite: Boolean) : ContactAction
}
