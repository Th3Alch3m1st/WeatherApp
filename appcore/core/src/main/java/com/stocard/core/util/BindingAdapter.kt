package com.stocard.core.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.stocard.core.R
import com.stocard.core.glide.GlideApp

/**
 * Created By Rafiqul Hasan
 */

@BindingAdapter("image_url")
fun ImageView.loadImage(url: String?) {
	GlideApp.with(this)
		.load(url)
		.placeholder(R.drawable.bg_placeholder)
		.error(R.drawable.bg_placeholder)
		.into(this)
}