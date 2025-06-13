package com.yenaly.han1meviewer

import android.util.Log
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.tasks.await

object TranslationUtil {
    private const val TAG = "TranslationUtil"
    
    /**
     * Translates text from Chinese to the target language
     * @param text The text to translate
     * @param sourceLang Source language code (default: Chinese)
     * @param targetLang Target language code (default: from Preferences)
     * @return Translated text or original text if translation fails
     */
    suspend fun translateText(
        text: String,
        sourceLang: String = TranslateLanguage.CHINESE,
        targetLang: String = Preferences.targetLanguage
    ): String {
        if (text.isBlank()) return text
        
        return try {
            val options = TranslatorOptions.Builder()
                .setSourceLanguage(sourceLang)
                .setTargetLanguage(targetLang)
                .build()
            
            val translator = Translation.getClient(options)
            translator.translate(text).await().also {
                translator.close()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Translation failed for text: $text", e)
            text // Return original text on failure
        }
    }

    /**
     * Checks if translation is supported for the language pair
     */
    fun isTranslationSupported(
        sourceLang: String = TranslateLanguage.CHINESE,
        targetLang: String = Preferences.targetLanguage
    ): Boolean {
        return try {
            TranslateLanguage.fromLanguageTag(sourceLang) != null &&
            TranslateLanguage.fromLanguageTag(targetLang) != null &&
            Translation.getClient(
                TranslatorOptions.Builder()
                    .setSourceLanguage(sourceLang)
                    .setTargetLanguage(targetLang)
                    .build()
            ).let { true }
        } catch (e: Exception) {
            false
        }
    }
}
