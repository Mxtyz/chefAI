package com.aguilar.chefai.utils

interface RecipeTranslator {
    suspend fun toSpanish(text: String): String
    suspend fun toEnglish(text: String): String
}
