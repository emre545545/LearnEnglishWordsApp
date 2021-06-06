package com.sefir.learnenglishwords.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.sefir.learnenglishwords.model.Word
import com.sefir.learnenglishwords.service.WordDatabase
import kotlinx.coroutines.launch

class ShowOneOwnWordViewModel(application: Application) : BaseViewModel(application) {

    val wordLiveData = MutableLiveData<Word>()

    fun takeRoomData(uuid: Int) {

        launch {
            val dao = WordDatabase(getApplication()).wordDao()
            var word = dao.getWord(uuid)
            wordLiveData.value = word
        }
    }

    fun removeOneElement(uuid: Int) {
        launch {
            val dao = WordDatabase(getApplication()).wordDao()
            dao.deleteOneElement(uuid)
        }
    }

}