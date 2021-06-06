package com.sefir.learnenglishwords.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.sefir.learnenglishwords.model.Word
import com.sefir.learnenglishwords.service.WordDatabase
import com.sefir.learnenglishwords.util.PrivateSharedPreferences
import kotlinx.coroutines.launch

class QuizQuestionViewModel(application: Application) : BaseViewModel(application) {

    val wordLiveData = MutableLiveData<List<Word>>()
    val privateSharedPreferences = PrivateSharedPreferences(getApplication())


    fun takeRoomData(type: String) {

        launch {
            val dao = WordDatabase(getApplication()).wordDao()
            val words = dao.getFourWord(type)
            wordLiveData.value = words
        }
    }

}