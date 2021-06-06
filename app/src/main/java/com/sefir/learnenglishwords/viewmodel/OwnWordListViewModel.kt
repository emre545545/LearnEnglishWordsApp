package com.sefir.learnenglishwords.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.sefir.learnenglishwords.model.Word

import com.sefir.learnenglishwords.service.WordDatabase

import kotlinx.coroutines.launch

class OwnWordListViewModel(application: Application) : BaseViewModel(application) {

    val wordLiveData = MutableLiveData<List<Word>>()


    fun takeRoomData() {

        launch {
            val dao = WordDatabase(getApplication()).wordDao()
            val words = dao.getOwnWord()
            wordLiveData.value = words
        }
    }
}