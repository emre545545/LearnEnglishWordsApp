package com.sefir.learnenglishwords.view

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sefir.learnenglishwords.R
import com.sefir.learnenglishwords.util.downloadImage
import com.sefir.learnenglishwords.viewmodel.ShowOneOwnWordViewModel
import kotlinx.android.synthetic.main.activity_show_one_own_word.*
import java.util.*


class ShowOneOwnWord : AppCompatActivity() {
    private lateinit var viewModel: ShowOneOwnWordViewModel
    private var uuid: Int? = null
    private var mTTS_UK: TextToSpeech? = null
    private var mTTS_US: TextToSpeech? = null
    private lateinit var clockwise: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_one_own_word)

        clockwise = AnimationUtils.loadAnimation(this, R.anim.clockwise)

        tv_wordName_own.animation = clockwise
        tv_example_own.animation = clockwise
        tv_mean_own.animation = clockwise
        tv_translated_own.animation = clockwise
        tv_1_own.animation = clockwise
        tv_2_own.animation = clockwise
        tv_3_own.animation = clockwise
        iv_show_own_word_own.animation = clockwise

        btn_remove_own_list_own.animation = clockwise
        btn_UK_own.animation = clockwise
        btn_US_own.animation = clockwise

        viewModel = ViewModelProvider(this).get(ShowOneOwnWordViewModel::class.java)
        var _type: Int = intent.getIntExtra("type", 0)
        viewModel.takeRoomData(_type)

        observeLiveData()
        initilizeUK()
        initilizeUS()

    }

    fun observeLiveData() {


        viewModel.wordLiveData.observe(this, Observer { word ->
            word?.let {
                tv_wordName_own.text = it.wordName
                tv_translated_own.text = it.translated
                tv_example_own.text = it.example
                tv_mean_own.text = it.mean
                if (it.image!!.startsWith("http")) {
                    iv_show_own_word_own.downloadImage(it.image)
                } else {
                    val encodeByte: ByteArray = Base64.decode(it.image, Base64.DEFAULT)
                    val bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
                    iv_show_own_word_own.setImageBitmap(bitmap)
                }
                uuid = it.uuid
            }
        })
    }

    fun removeOwnList(view: View) {
        viewModel.removeOneElement(uuid!!)
        val intent = Intent(this, OwnWordList::class.java)
        intent.putExtra("type", "verb")
        startActivity(intent)
        finish()
    }


    fun initilizeUK() {

        mTTS_UK = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                val result = mTTS_UK!!.setLanguage(Locale.UK)


                if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED
                ) {
                    Log.e("TTS", "Language not supported")
                } else {
                    //speak()
                }
            } else {
                Log.e("TTS", "Initialization failed")
            }
        }

    }

    fun initilizeUS() {

        mTTS_US = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                val result = mTTS_US!!.setLanguage(Locale.US)


                if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED
                ) {
                    Log.e("TTS", "Language not supported")
                } else {
                    //speak()
                }
            } else {
                Log.e("TTS", "Initialization failed")
            }
        }

    }

    fun speakUK_own(view: View) {
        mTTS_US!!.stop()
        mTTS_UK!!.speak(tv_wordName_own.text.toString(), TextToSpeech.QUEUE_FLUSH, null)

    }

    fun speakUS_own(view: View) {
        mTTS_UK!!.stop()
        mTTS_US!!.speak(tv_wordName_own.text.toString(), TextToSpeech.QUEUE_FLUSH, null)
    }

    fun addImageOwn(view: View) {

    }

    override fun onDestroy() {
        super.onDestroy()
        mTTS_UK!!.shutdown()
        mTTS_US!!.shutdown()
    }


}