package com.devhjs.getphonenumber.util

object KoreanChosung {
    private const val HANGUL_BASE = 0xAC00
    private const val HANGUL_END = 0xD7A3
    // 초성 리스트: ㄱ ㄲ ㄴ ㄷ ㄸ ㄹ ㅁ ㅂ ㅃ ㅅ ㅆ ㅇ ㅈ ㅉ ㅊ ㅋ ㅌ ㅍ ㅎ
    private val CHOSUNG = charArrayOf(
        'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ',
        'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
    )

    /**
     * 문자열에서 초성만 추출하여 반환합니다.
     * 한글이 아닌 문자는 그대로 유지됩니다.
     */
    fun getChosung(text: String): String {
        val result = StringBuilder()
        for (char in text) {
            if (char.code in HANGUL_BASE..HANGUL_END) {
                val chosungIndex = (char.code - HANGUL_BASE) / 28 / 21
                result.append(CHOSUNG[chosungIndex])
            } else {
                result.append(char)
            }
        }
        return result.toString()
    }

    /**
     * 검색어가 초성으로만 이루어져 있는지 확인합니다.
     */
    fun isChosung(text: String): Boolean {
        return text.all { it in CHOSUNG || !it.isLetter() } // 자음이거나 특수문자/공백 등
    }
    
    /**
     * 주어진 텍스트가 검색어(초성 또는 일반)를 포함하는지 확인합니다.
     */
    fun match(text: String, query: String): Boolean {
        if (query.isBlank()) return true
        
        // 검색어가 초성으로만 이루어져 있다면 초성 비교
        if (isChosung(query)) {
            return getChosung(text).contains(query)
        }
        
        // 아니면 일반 포함 비교
        return text.contains(query, ignoreCase = true)
    }
}
