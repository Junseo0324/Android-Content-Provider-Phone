package com.devhjs.getphonenumber.domain.usecase

import com.devhjs.getphonenumber.domain.model.Contact
import org.junit.Assert.assertEquals
import org.junit.Test

class SearchContactsUseCaseTest {

    private val useCase = SearchContactsUseCase()

    private val contacts = listOf(
        Contact(id = "1", name = "홍길동", phoneNumber = "010-1234-5678"),
        Contact(id = "2", name = "김철수", phoneNumber = "010-9876-5432"),
        Contact(id = "3", name = "James", phoneNumber = "010-5555-5555")
    )

    @Test
    fun `invoke returns correct contacts for name search`() {
        val result = useCase("홍", contacts)
        assertEquals(1, result.size)
        assertEquals("홍길동", result[0].name)
    }

    @Test
    fun `invoke returns correct contacts for number search`() {
        val result = useCase("5432", contacts)
        assertEquals(1, result.size)
        assertEquals("김철수", result[0].name)
    }

    @Test
    fun `invoke returns correct contacts for chosung search`() {
        val result = useCase("ㅎㄱㄷ", contacts)
        assertEquals(1, result.size)
        assertEquals("홍길동", result[0].name)
    }

    @Test
    fun `invoke returns all contacts for blank query`() {
        val result = useCase("", contacts)
        assertEquals(contacts.size, result.size)
    }

    @Test
    fun `invoke returns empty list for no match`() {
        val result = useCase("X", contacts)
        assertEquals(0, result.size)
    }
}
