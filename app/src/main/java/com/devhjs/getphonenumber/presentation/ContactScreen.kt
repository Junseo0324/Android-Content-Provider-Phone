package com.devhjs.getphonenumber.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.devhjs.getphonenumber.presentation.component.ContactItem



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactScreen(
    state: ContactUiState,
    onAction: (ContactAction) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("연락처") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            TextField(
                value = state.searchQuery,
                onValueChange = { onAction(ContactAction.OnSearchQueryChange(it)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("검색 (이름, 번호, 초성)") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                singleLine = true
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(state.contacts, key = { it.id }) { contact ->
                    ContactItem(
                        contact = contact,
                        onToggleFavorite = { onAction(ContactAction.OnToggleFavorite(contact.id, contact.isFavorite)) }
                    )
                }
            }
        }
    }
}
