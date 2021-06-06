package com.sefir.learnenglishwords.view


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sefir.learnenglishwords.R
import com.sefir.learnenglishwords.model.Word
import com.sefir.learnenglishwords.service.NotificationService
import com.sefir.learnenglishwords.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.activity_home.*

class Home : AppCompatActivity() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var allWordsList: List<Word>
    private lateinit var downtoup: Animation
    private lateinit var uptodown: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        downtoup = AnimationUtils.loadAnimation(this, R.anim.downtoup)
        btn_learn.animation = downtoup
        btn_quiz.animation = downtoup

        uptodown = AnimationUtils.loadAnimation(this, R.anim.uptodown)
        iv_home.animation = uptodown

        iv_home.setImageResource(R.drawable.learn_english_word)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModel.refreshData()
        observeLiveData()

        Intent(this, NotificationService::class.java).also {
            startService(it)
        }


    }

    fun observeLiveData() {

        viewModel.words.observe(this, Observer { words ->
            words?.let {
                allWordsList = it

            }
        })

    }


    fun goToQuiz(view: View) {
        val intent = Intent(this, Quiz::class.java)
        startActivity(intent)

    }

    fun goToLearn(view: View) {
        val intent = Intent(this, Learn::class.java)
        startActivity(intent)

    }
}