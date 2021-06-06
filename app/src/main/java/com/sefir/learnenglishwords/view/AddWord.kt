package com.sefir.learnenglishwords.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.sefir.learnenglishwords.R
import com.sefir.learnenglishwords.model.Word
import com.sefir.learnenglishwords.viewmodel.ShowWordViewModel
import kotlinx.android.synthetic.main.activity_add_word.*
import kotlinx.android.synthetic.main.activity_show_one_own_word.*
import java.io.ByteArrayOutputStream


class AddWord : AppCompatActivity() {
    var selected_image: Uri? = null
    var image_bitmap: Bitmap? = null
    private lateinit var viewModel: ShowWordViewModel
    lateinit var im: ImageView
    var ret: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_word)
        viewModel = ViewModelProvider(this).get(ShowWordViewModel::class.java)
        im = findViewById(R.id.iv_add_image);

    }

    fun selectImage(view: View) {

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


                } else {
                    image_bitmap =
                        MediaStore.Images.Media.getBitmap(this.contentResolver, selected_image)
                    iv_show_own_word_own.setImageBitmap(image_bitmap)
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun addOwnWord(view: View) {
        var word = Word(
            type = "own_",
            wordName = et_word_name.text.toString(),
            translated = et_translated.text.toString(),
            mean = et_mean.text.toString(),
            example = et_example.text.toString(),
            image = ret!!
        )
        viewModel.addOwnWordToList(word)

        val intent = Intent(this, OwnWordList::class.java)
        startActivity(intent)
        finish()
    }
}