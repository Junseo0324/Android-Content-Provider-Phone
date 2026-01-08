package com.devhjs.getphonenumber.data.datasource

import android.content.ContentResolver
import android.content.Context
import android.database.ContentObserver
import android.provider.ContactsContract
import com.devhjs.getphonenumber.domain.model.Contact
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class SystemContactDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) : ContactDataSource {

    override fun getSystemContacts(): Flow<List<Contact>> = callbackFlow {
        val contentResolver: ContentResolver = context.contentResolver
        
        // 데이터 변경 감지 리스너
        val observer = object : ContentObserver(null) {
            override fun onChange(selfChange: Boolean) {
                trySend(fetchContacts(contentResolver))
            }
        }

        // 등록
        contentResolver.registerContentObserver(
            ContactsContract.Contacts.CONTENT_URI,
            true,
            observer
        )

        // 초기 데이터 로드
        trySend(fetchContacts(contentResolver))

        // 종료 시 해제
        awaitClose {
            contentResolver.unregisterContentObserver(observer)
        }
    }

    private fun fetchContacts(contentResolver: ContentResolver): List<Contact> {
        val contacts = mutableListOf<Contact>()
        val projection = arrayOf(
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        )

        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            projection,
            null,
            null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
        )

        cursor?.use {
            val idIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)
            val nameIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            val numberIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)

            while (it.moveToNext()) {
                val id = it.getString(idIndex) ?: continue
                val name = it.getString(nameIndex) ?: "Unknown"
                val number = it.getString(numberIndex) ?: ""

                contacts.add(
                    Contact(
                        id = id,
                        name = name,
                        phoneNumber = number,
                        isFavorite = false
                    )
                )
            }
        }
        return contacts
    }
}
