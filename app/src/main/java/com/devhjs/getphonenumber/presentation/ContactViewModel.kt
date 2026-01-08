package com.devhjs.getphonenumber.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    
    private val _allContacts = getContactsUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val uiState: StateFlow<ContactUiState> = combine(_allContacts, _searchQuery) { contacts, query ->
        ContactUiState(
            contacts = searchContactsUseCase(query, contacts),
            searchQuery = query
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ContactUiState()
    )

    fun onAction(action: ContactAction) {
        when (action) {
            is ContactAction.OnSearchQueryChange -> {
                _searchQuery.value = action.query
            }
            is ContactAction.OnToggleFavorite -> {
                viewModelScope.launch {
                    toggleFavoriteUseCase(action.id, !action.isFavorite)
                }
            }
        }
    }
}
