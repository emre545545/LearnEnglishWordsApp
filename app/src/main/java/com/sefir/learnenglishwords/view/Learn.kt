package com.sefir.learnenglishwords.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.sefir.learnenglishwords.R
import kotlinx.android.synthetic.main.activity_learn.*

class Learn : AppCompatActivity() {

    private lateinit var downtoup: Animation
    private lateinit var uptodown: Animation
    private lateinit var lefttoright: Animation
    private lateinit var righttoleft: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learn)

        downtoup = AnimationUtils.loadAnimation(this, R.anim.downtoup)
        uptodown = AnimationUtils.loadAnimation(this, R.anim.uptodown)
        lefttoright = AnimationUtils.loadAnimation(this, R.anim.lefttoright)
        righttoleft = AnimationUtils.loadAnimation(this, R.anim.righttoleft)

        btn_learn_verb_learn.animation = uptodown
        btn_learn_adjective_learn.animation = lefttoright
        btn_learn_adverb_learn.animation = lefttoright
        btn_learn_idiom_learn.animation = righttoleft
        btn_own_list_learn.animation = downtoup


    }

    fun learnVerb(view: View) {
        val intent = Intent(this, ShowWord::class.java)
        intent.putExtra("type", "verb")
        startActivity(intent)
        finish()
    }

    fun learnAdjective(view: View) {
        val intent = Intent(this, ShowWord::class.java)
        intent.putExtra("type", "adjective")
        startActivity(intent)
        finish()
    }

    fun learnAdverb(view: View) {
        val intent = Intent(this, ShowWord::class.java)
        intent.putExtra("type", "adverb")
        startActivity(intent)
        finish()
    }

    fun learnIdiom(view: View) {
        val intent = Intent(this, ShowWord::class.java)
        intent.putExtra("type", "idiom")
        startActivity(intent)
        finish()
    }


    fun openOwnWords(view: View) {
        val intent = Intent(this, OwnWordList::class.java)
        startActivity(intent)
        finish()

    }
}