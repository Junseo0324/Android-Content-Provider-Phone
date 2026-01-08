package com.devhjs.getphonenumber.util

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class KoreanChosungTest {

    @Test
    fun `getChosung extracts initial consonants correctly`() {
        assertEquals("ㅎㄱㄷ", KoreanChosung.getChosung("홍길동"))
        assertEquals("ㄱㄴㄷ", KoreanChosung.getChosung("가나다"))
        assertEquals("Test", KoreanChosung.getChosung("Test")) // 영문 유지
        assertEquals("123", KoreanChosung.getChosung("123")) // 숫자 유지
        assertEquals("ㅎㄱㄷ 123", KoreanChosung.getChosung("홍길동 123")) // 공백 및 혼합
    }

    @Test
    fun `isChosung identifies chosung strings correctly`() {
        assertTrue(KoreanChosung.isChosung("ㅎㄱㄷ"))
        assertTrue(KoreanChosung.isChosung("ㄱㄴㄷ"))
        assertTrue(KoreanChosung.isChosung("ㄱ ㄴ ㄷ")) // 공백 포함
        assertFalse(KoreanChosung.isChosung("홍길동")) // 완성형 한글 포함
        assertFalse(KoreanChosung.isChosung("ㅎ길동"))
    }

    @Test
    fun `match returns true for valid chosung search`() {
        assertTrue(KoreanChosung.match("홍길동", "ㅎㄱㄷ"))
        assertTrue(KoreanChosung.match("홍길동", "ㅎ"))
        assertTrue(KoreanChosung.match("홍길동", "ㄱㄷ")) // 중간 일치 확인 (containment) -> 현재 로직상 getChosung("홍길동")은 "ㅎㄱㄷ"이므로 "ㄱㄷ" 포함됨.
    }

    @Test
    fun `match returns true for standard search`() {
        assertTrue(KoreanChosung.match("홍길동", "홍"))
        assertTrue(KoreanChosung.match("홍길동", "길동"))
        assertTrue(KoreanChosung.match("010-1234-5678", "1234"))
    }

    @Test
    fun `match ignores case for english`() {
        assertTrue(KoreanChosung.match("Apple", "apple"))
        assertTrue(KoreanChosung.match("Apple", "app"))
    }

    @Test
    fun `match returns false for non-matching query`() {
        assertFalse(KoreanChosung.match("홍길동", "ㅋ"))
        assertFalse(KoreanChosung.match("홍길동", "김"))
        assertFalse(KoreanChosung.match("010-1234-5678", "9999"))
    }
}
