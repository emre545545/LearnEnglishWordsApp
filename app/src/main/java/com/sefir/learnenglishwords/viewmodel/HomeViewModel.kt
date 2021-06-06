package com.sefir.learnenglishwords.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.sefir.learnenglishwords.model.Word
import com.sefir.learnenglishwords.service.WordAPIService
import com.sefir.learnenglishwords.service.WordDatabase
import com.sefir.learnenglishwords.util.PrivateSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : BaseViewModel(application) {
    val words = MutableLiveData<List<Word>>()

    private val wordAPIService = WordAPIService()
    private val disposable = CompositeDisposable()
    val privateSharedPreferences = PrivateSharedPreferences(getApplication())

    fun refreshData() {
        val mode = privateSharedPreferences.getMode()
        if (mode == 0) {
            loadingFromInternet()
            privateSharedPreferences.saveMode(1)
        } else {
            loadingFromSqlite()
        }
    }

    private fun loadingFromSqlite() {


        launch {

            val wordDao = WordDatabase(getApplication()).wordDao()
            val wordList = wordDao.getAllWord()
            words.value = wordList
        }
    }

    private fun loadingFromInternet() {


        disposable.add(
            wordAPIService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Word>>() {
                    override fun onSuccess(t: List<Word>) {
                        storeSqlite(t)
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }
                })
        )
    }


    private fun storeSqlite(wordList: List<Word>) {


        launch {
            val dao = WordDatabase(getApplication()).wordDao()
            dao.deleteAllWord()
            val uuidList = dao.insertAll(*wordList.toTypedArray())

            var i = 0
            while (i < wordList.size) {

                wordList[i].uuid = uuidList[i].toInt()
                i += 1
            }
            words.value = wordList
        }
    }


}