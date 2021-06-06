package com.sefir.learnenglishwords.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.lifecycle.ViewModelProvider
import com.sefir.learnenglishwords.R
import com.sefir.learnenglishwords.util.PrivateSharedPreferences
import com.sefir.learnenglishwords.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.activity_quiz.*

class Quiz : AppCompatActivity() {

    private lateinit var privateSharedPreferences: PrivateSharedPreferences
    private lateinit var viewModel: HomeViewModel
    private lateinit var downtoup: Animation
    private lateinit var uptodown: Animation
    private lateinit var lefttoright: Animation
    private lateinit var righttoleft: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        downtoup = AnimationUtils.loadAnimation(this, R.anim.downtoup)
        uptodown = AnimationUtils.loadAnimation(this, R.anim.uptodown)
        lefttoright = AnimationUtils.loadAnimation(this, R.anim.lefttoright)
        righttoleft = AnimationUtils.loadAnimation(this, R.anim.righttoleft)

        btn_quiz_verb_quiz.animation = uptodown
        btn_quiz_adjective_quiz.animation = lefttoright
        btn_quiz_adverb_quiz.animation = lefttoright
        btn_quiz_idiom_quiz.animation = righttoleft
        btn_quiz_own_list_quiz.animation = downtoup

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        privateSharedPreferences = viewModel.privateSharedPreferences

    }

    fun makeQuizVerb(view: View) {
        privateSharedPreferences.savePoint(0)
        privateSharedPreferences.saveCount(1)
        val intent = Intent(this, QuizQuestion::class.java)
        intent.putExtra("type", "verb")
        startActivity(intent)
        finish()

    }

    fun makeQuizAdjective(view: View) {
        privateSharedPreferences.savePoint(0)
        privateSharedPreferences.saveCount(1)
        val intent = Intent(this, QuizQuestion::class.java)
        intent.putExtra("type", "adjective")
        startActivity(intent)
        finish()

    }

    fun makeQuizAdverb(view: View) {
        privateSharedPreferences.savePoint(0)
        privateSharedPreferences.saveCount(1)
        val intent = Intent(this, QuizQuestion::class.java)
        intent.putExtra("type", "adverb")
        startActivity(intent)
        finish()

    }

    fun makeQuizIdiom(view: View) {
        privateSharedPreferences.savePoint(0)
        privateSharedPreferences.saveCount(1)
        val intent = Intent(this, QuizQuestion::class.java)
        intent.putExtra("type", "idiom")
        startActivity(intent)
        finish()

    }


    fun makeQuizOwn(view: View) {
        privateSharedPreferences.savePoint(0)
        privateSharedPreferences.saveCount(1)
        val intent = Intent(this, QuizQuestion::class.java)
        intent.putExtra("type", "own")
        startActivity(intent)
        finish()

    }
}