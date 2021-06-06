package com.sefir.learnenglishwords.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sefir.learnenglishwords.R
import com.sefir.learnenglishwords.adapter.WordRVadapter
import com.sefir.learnenglishwords.model.Word
import com.sefir.learnenglishwords.viewmodel.OwnWordListViewModel
import kotlinx.android.synthetic.main.activity_own_word_list.*

class OwnWordList : AppCompatActivity() {

    private lateinit var viewModel: OwnWordListViewModel
    private lateinit var allWordsList: List<Word>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_own_word_list)
        viewModel = ViewModelProvider(this).get(OwnWordListViewModel::class.java)
        viewModel.takeRoomData()
        observeLiveData()


    }

    fun observeLiveData() {

        println("observe çalıştı")
        viewModel.wordLiveData.observe(this, Observer { words ->
            words?.let {
                allWordsList = it
                val layoutManager = LinearLayoutManager(this)
                rv_own_list.layoutManager = layoutManager
                val adapter = WordRVadapter(allWordsList)
                rv_own_list.adapter = adapter
            }
        })
    }

    fun addWord(view: View) {
        val intent = Intent(this, AddWord::class.java)
        startActivity(intent)
        finish()
    }
}