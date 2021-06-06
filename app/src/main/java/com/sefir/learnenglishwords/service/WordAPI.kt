package com.sefir.learnenglishwords.service

import com.sefir.learnenglishwords.model.Word
import io.reactivex.Single
import retrofit2.http.GET


interface WordAPI {

    @GET("words.json")
    fun getWord(): Single<List<Word>>

}