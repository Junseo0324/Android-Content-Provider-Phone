package com.devhjs.getphonenumber.data.datasource

import android.content.ContentResolver
import android.content.Context
import android.database.ContentObserver
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.slot
import io.mockk.unmockkStatic
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SystemContactDataSourceTest {

    private val context: Context = mockk(relaxed = true)
    private val contentResolver: ContentResolver = mockk(relaxed = true)
    private val cursor: Cursor = mockk(relaxed = true)
    private val dataSource = SystemContactDataSource(context)

    @Before
    fun setup() {
        // Android statics mocking
        mockkStatic(Uri::class)
        every { Uri.parse(any()) } returns mockk()
        every { Uri.withAppendedPath(any(), any()) } returns mockk()
    }

    @After
    fun tearDown() {
        unmockkStatic(Uri::class)
    }

    @Test
    fun `getSystemContacts emits contacts from ContentResolver`() = runTest {
        // Given
        every { context.contentResolver } returns contentResolver
        
        // Cursor setup
        every { 
            contentResolver.query(
                any(), any(), any(), any(), any()
            ) 
        } returns cursor

        // Mocking Cursor data for 1 row
        every { cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID) } returns 0
        every { cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME) } returns 1
        every { cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER) } returns 2
        
        every { cursor.moveToNext() } returns true andThen false // 1 row then end
        every { cursor.getString(0) } returns "1"
        every { cursor.getString(1) } returns "Test User"
        every { cursor.getString(2) } returns "010-1234-5678"

        // When
        val contacts = dataSource.getSystemContacts().first()

        // Then
        assertEquals(1, contacts.size)
        assertEquals("1", contacts[0].id)
        assertEquals("Test User", contacts[0].name)
        assertEquals("010-1234-5678", contacts[0].phoneNumber)
        
        // Verify ContentObserver registration
        verify { 
            contentResolver.registerContentObserver(
                any(), 
                true, 
                any<ContentObserver>()
            ) 
        }
    }
}
