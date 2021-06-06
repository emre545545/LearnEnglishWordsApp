package com.sefir.learnenglishwords.service

import com.sefir.learnenglishwords.model.Word
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

import retrofit2.converter.gson.GsonConverterFactory

class WordAPIService {

    private val BASE_URL = "https://emre545545.github.io/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(WordAPI::class.java)

    fun getData(): Single<List<Word>> {
        return api.getWord()
    }
}