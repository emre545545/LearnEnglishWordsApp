package com.sefir.learnenglishwords.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.sefir.learnenglishwords.model.Word
import com.sefir.learnenglishwords.service.WordDatabase
import kotlinx.coroutines.launch

class ShowWordViewModel(application: Application) : BaseViewModel(application) {

    val wordLiveData = MutableLiveData<Word>()

    private lateinit var word: Word


    fun takeRoomData(type: String) {

        launch {
            val dao = WordDatabase(getApplication()).wordDao()
            word = dao.getWord(type)
            wordLiveData.value = word
        }
    }


    fun addToList() {
        var word2 = Word(
            type = "own",
            wordName = word.wordName,
            translated = word.translated,
            mean = word.mean,
            example = word.example,
            image = word.image
        )


        launch {
            val dao = WordDatabase(getApplication()).wordDao()
            var uuid2 = dao.insertOneElement(word2)
            word2.uuid = uuid2.toInt()
        }
    }

    fun addOwnWordToList(word: Word) {

        launch {
            val dao = WordDatabase(getApplication()).wordDao()
            var wo = word
            var uuid3 = dao.insertOneElement(wo)
            wo.uuid = uuid3.toInt()
        }
    }

    fun changeImage(str : String) {

        launch {
            val dao = WordDatabase(getApplication()).wordDao()
            dao.update(word.uuid, str)
        }

    }


}