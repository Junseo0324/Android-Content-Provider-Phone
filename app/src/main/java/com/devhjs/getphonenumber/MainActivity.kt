package com.devhjs.getphonenumber

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.devhjs.getphonenumber.presentation.ContactScreenRoot
import com.devhjs.getphonenumber.ui.theme.GetPhoneNumberTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GetPhoneNumberTheme {
                ContactScreenRoot()
            }
        }
    }
}