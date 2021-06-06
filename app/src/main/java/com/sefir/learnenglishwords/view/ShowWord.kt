package com.sefir.learnenglishwords.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.speech.tts.TextToSpeech
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sefir.learnenglishwords.R
import com.sefir.learnenglishwords.util.downloadImage
import com.sefir.learnenglishwords.viewmodel.ShowWordViewModel
import kotlinx.android.synthetic.main.activity_show_one_own_word.*
import kotlinx.android.synthetic.main.activity_show_word.*
import kotlinx.android.synthetic.main.activity_show_word.tv_example
import kotlinx.android.synthetic.main.activity_show_word.tv_mean
import kotlinx.android.synthetic.main.activity_show_word.tv_translated
import kotlinx.android.synthetic.main.activity_show_word.tv_wordName
import java.io.ByteArrayOutputStream
import java.util.*

class ShowWord : AppCompatActivity() {

    private lateinit var viewModel: ShowWordViewModel
    var _type: String? = ""
    private var mTTS_UK: TextToSpeech? = null
    private var mTTS_US: TextToSpeech? = null
    private lateinit var clockwise: Animation
    var selected_image: Uri? = null
    var image_bitmap: Bitmap? = null
    lateinit var im: ImageView
    var ret: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_word)
        im = findViewById(R.id.iv_show_word);


        clockwise = AnimationUtils.loadAnimation(this, R.anim.clockwise)

        tv_wordName.animation = clockwise
        tv_example.animation = clockwise
        tv_mean.animation = clockwise
        tv_translated.animation = clockwise
        tv_1.animation = clockwise
        tv_2.animation = clockwise
        tv_3.animation = clockwise
        iv_show_word.animation = clockwise

        btn_add_own_list.animation = clockwise
        btn_UK.animation = clockwise
        btn_US.animation = clockwise
        btn_next_word.animation = clockwise
        btn_go_learn.animation



        viewModel = ViewModelProvider(this).get(ShowWordViewModel::class.java)
        _type = intent.getStringExtra("type")
        viewModel.takeRoomData(_type!!)

        observeLiveData()
        initilizeUK()
        initilizeUS()

    }

    fun observeLiveData() {


        viewModel.wordLiveData.observe(this, Observer { word ->
            word?.let {
                tv_wordName.text = it.wordName
                tv_translated.text = it.translated
                tv_example.text = it.example
                tv_mean.text = it.mean
                if (it.image!!.startsWith("http")) {
                    iv_show_word.downloadImage(it.image)
                } else {
                    val encodeByte: ByteArray = Base64.decode(it.image, Base64.DEFAULT)
                    val bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
                    iv_show_word.setImageBitmap(bitmap)
                }
            }
        })

    }


    fun addOwnList(view: View) {

        viewModel.addToList()

    }

    fun goToLearn(view: View) {
        val intent = Intent(this, Learn::class.java)
        startActivity(intent)
        finish()
    }

    fun nextWord(view: View) {
        val intent = Intent(this, ShowWord::class.java)
        intent.putExtra("type", _type)
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

    fun speakUK(view: View) {
        mTTS_US!!.stop()
        mTTS_UK!!.speak(tv_wordName.text.toString(), TextToSpeech.QUEUE_FLUSH, null)

    }

    fun speakUS(view: View) {
        mTTS_UK!!.stop()
        mTTS_US!!.speak(tv_wordName.text.toString(), TextToSpeech.QUEUE_FLUSH, null)
    }

    fun addImage(view: View) {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                123
            )
        } else {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, 456)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 123) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent, 456)
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 456 && resultCode == RESULT_OK && data != null) {
            selected_image = data.data
            if (selected_image != null) {
                if (Build.VERSION.SDK_INT >= 28) {
                    val source = ImageDecoder.createSource(this.contentResolver, selected_image!!)
                    image_bitmap = ImageDecoder.decodeBitmap(source)

                    im.setImageBitmap(image_bitmap)
                    val baos = ByteArrayOutputStream()
                    image_bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                    val b = baos.toByteArray()
                    ret = Base64.encodeToString(b, Base64.DEFAULT)
                    viewModel.changeImage(ret!!)

                } else {
                    image_bitmap =
                        MediaStore.Images.Media.getBitmap(this.contentResolver, selected_image)
                    iv_show_own_word_own.setImageBitmap(image_bitmap)
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        super.onDestroy()
        mTTS_UK!!.shutdown()
        mTTS_US!!.shutdown()
    }

}