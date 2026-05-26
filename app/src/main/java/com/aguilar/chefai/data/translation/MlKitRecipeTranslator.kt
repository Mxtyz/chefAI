package com.aguilar.chefai.data.translation

import com.google.android.gms.tasks.Task
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class MlKitRecipeTranslator : RecipeTranslator {
    private val englishToSpanish = createTranslator(
        sourceLanguage = TranslateLanguage.ENGLISH,
        targetLanguage = TranslateLanguage.SPANISH
    )
    private val spanishToEnglish = createTranslator(
        sourceLanguage = TranslateLanguage.SPANISH,
        targetLanguage = TranslateLanguage.ENGLISH
    )
    private val conditions = DownloadConditions.Builder().build()
    private val cache = mutableMapOf<String, String>()

    override suspend fun toSpanish(text: String): String {
        return translate(text, englishToSpanish, "en-es")
    }

    override suspend fun toEnglish(text: String): String {
        return translate(text, spanishToEnglish, "es-en")
    }

    private suspend fun translate(text: String, translator: Translator, direction: String): String {
        val cleanText = text.trim()
        if (cleanText.isBlank()) return text

        val cacheKey = "$direction:$cleanText"
        cache[cacheKey]?.let { return it }

        translator.downloadModelIfNeeded(conditions).await()
        val translated = translator.translate(cleanText).await()
        cache[cacheKey] = translated
        return translated
    }

    private fun createTranslator(sourceLanguage: String, targetLanguage: String): Translator {
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(sourceLanguage)
            .setTargetLanguage(targetLanguage)
            .build()
        return Translation.getClient(options)
    }
}

private suspend fun <T> Task<T>.await(): T {
    return suspendCancellableCoroutine { continuation ->
        addOnSuccessListener { result ->
            if (continuation.isActive) continuation.resume(result)
        }
        addOnFailureListener { exception ->
            if (continuation.isActive) continuation.resumeWithException(exception)
        }
        addOnCanceledListener {
            continuation.cancel()
        }
    }
}
