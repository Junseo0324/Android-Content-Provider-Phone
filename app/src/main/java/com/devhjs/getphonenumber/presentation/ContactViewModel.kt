package com.devhjs.getphonenumber.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devhjs.getphonenumber.domain.model.Contact
import com.devhjs.getphonenumber.domain.usecase.GetContactsUseCase
import com.devhjs.getphonenumber.domain.usecase.SearchContactsUseCase
import com.devhjs.getphonenumber.domain.usecase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(
    getContactsUseCase: GetContactsUseCase,
    private val searchContactsUseCase: SearchContactsUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _allContacts = getContactsUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val contacts: StateFlow<List<Contact>> = combine(_allContacts, _searchQuery) { contacts, query ->
        searchContactsUseCase(query, contacts)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun onToggleFavorite(contact: Contact) {
        viewModelScope.launch {
            toggleFavoriteUseCase(contact.id, !contact.isFavorite)
        }
    }
}
