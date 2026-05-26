package com.aguilar.chefai.data.translation

interface RecipeTranslator {
    suspend fun toSpanish(text: String): String
    suspend fun toEnglish(text: String): String
}
