package com.sefir.learnenglishwords.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ImageView.downloadImage(url: String?) {

    Glide.with(context).setDefaultRequestOptions(RequestOptions()).load(url).into(this)

}