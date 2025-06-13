
package com.yenaly.han1meviewer.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.yenaly.han1meviewer.util.TranslationUtil

class MainViewModel : ViewModel() {

    private val translationUtil = TranslationUtil()

    fun translateChineseToEnglish(chineseText: String) {
        translationUtil.translateText(chineseText)
        translationUtil.translatedText.observeForever { translatedText ->
            // Handle translated text
            // You can now use translatedText to update the UI or perform other actions
        }
    }
}
