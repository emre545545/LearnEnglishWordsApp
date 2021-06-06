package com.sefir.learnenglishwords.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sefir.learnenglishwords.R
import com.sefir.learnenglishwords.util.PrivateSharedPreferences
import com.sefir.learnenglishwords.viewmodel.QuizQuestionViewModel
import kotlinx.android.synthetic.main.activity_quiz_question.*
import java.util.*

class QuizQuestion : AppCompatActivity() {

    private lateinit var viewModel: QuizQuestionViewModel
    private var trueAnswer: String = ""
    private lateinit var privateSharedPreferences: PrivateSharedPreferences
    private var point: Int? = null
    private var str = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_question)

        val type: String = intent.getStringExtra("type").toString()
        str = type
        viewModel = ViewModelProvider(this).get(QuizQuestionViewModel::class.java)
        privateSharedPreferences = viewModel.privateSharedPreferences
        point = privateSharedPreferences.getPoint()
        viewModel.takeRoomData(type)
        observeLiveData()

    }

    fun observeLiveData() {

        tv_point.text = "POINT : " + point.toString()

        viewModel.wordLiveData.observe(this, Observer { word ->
            word?.let {
                tv_answer_1.text = it[0].translated.toString()
                tv_answer_2.text = it[1].translated.toString()
                tv_answer_3.text = it[2].translated.toString()
                tv_answer_4.text = it[3].translated.toString()


                val random = Random().nextInt(4)
                trueAnswer = it[random].translated.toString()
                tv_question.text = it[random].wordName

            }

        })

    }

    fun checkAnswer(view: View) {

        when (view.id) {
            R.id.tv_answer_1 ->
                if (tv_answer_1.text == trueAnswer) {

                    privateSharedPreferences.savePoint(point!! + 10)

                } else {
                    Toast.makeText(this, "Wrong Answer", Toast.LENGTH_SHORT).show()
                }
            R.id.tv_answer_2 ->
                if (tv_answer_2.text == trueAnswer) {

                    privateSharedPreferences.savePoint(point!! + 10)

                } else {
                    Toast.makeText(this, "Wrong Answer", Toast.LENGTH_SHORT).show()
                }
            R.id.tv_answer_3 ->
                if (tv_answer_3.text == trueAnswer) {

                    privateSharedPreferences.savePoint(point!! + 10)

                } else {
                    Toast.makeText(this, "Wrong Answer", Toast.LENGTH_SHORT).show()
                }
            R.id.tv_answer_4 ->
                if (tv_answer_4.text == trueAnswer) {

                    privateSharedPreferences.savePoint(point!! + 10)

                } else {
                    Toast.makeText(this, "Wrong Answer", Toast.LENGTH_SHORT).show()
                }
            else -> Toast.makeText(this, "else", Toast.LENGTH_SHORT).show()
        }
        Toast.makeText(this, privateSharedPreferences.getCount()!!.toString(), Toast.LENGTH_LONG)
            .show()
        if (privateSharedPreferences.getCount()!! < 5) {
            privateSharedPreferences.saveCount(privateSharedPreferences.getCount()!! + 1)
            val intent = Intent(this, QuizQuestion::class.java)
            intent.putExtra("type", str)
            startActivity(intent)
            finish()

        } else {

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Quiz Result")
            builder.setMessage("Your Score \n    ${privateSharedPreferences.getPoint()}")
            builder.setIcon(R.drawable.learn_english_word)
            builder.setPositiveButton("OK") { dialogInterface, which ->
                val intent = Intent(this, Quiz::class.java)
                startActivity(intent)
                finish()
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()


        }

    }


}