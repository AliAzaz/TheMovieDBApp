package com.example.themoviedbapp.utils

import android.util.Log
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BaseObservable
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.themoviedbapp.R


object BindingAdapters : BaseObservable() {

    @BindingAdapter("bind:loadGroupImage", "bind:loadType", requireAll = false)
    @JvmStatic
    fun loadGroupImage(imageView: AppCompatImageView, url: String?, type: Int) {

        val circleProgress = CircularProgressDrawable(imageView.context)
        circleProgress.strokeWidth = 5f
        circleProgress.centerRadius = 40f
        circleProgress.start()

        val buildUrl = CONSTANTS.IMAGE_URL + (if (type == 1) "w185" else "w342") + url

        Log.d("loadGroupImage: ", buildUrl)

        Glide.with(imageView.context)
            .asBitmap()
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .load(buildUrl)
            .placeholder(circleProgress)
            .error(R.drawable.loading)
            .into(imageView)

    }


    @BindingAdapter("loadShortString")
    @JvmStatic
    fun loadShortString(txt: AppCompatTextView, name: String?) {
        txt.text = name?.shortStringLength()?.convertStringToUpperCase()
    }


    @BindingAdapter("bind:firstString", "bind:secondString")
    @JvmStatic
    fun concateString(txt: AppCompatTextView, first: String, second: String) {
        txt.text = "$first: $second"
    }

}