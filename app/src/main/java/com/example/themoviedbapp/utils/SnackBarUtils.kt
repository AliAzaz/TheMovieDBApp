package com.example.themoviedbapp.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar
import org.apache.commons.lang3.StringUtils

object SnackBarUtils{

    private lateinit var snackbar: Snackbar

    fun showSnackBar(view: View,
                     message: String = StringUtils.EMPTY,
                     duration: Int = Snackbar.LENGTH_LONG,
                     action: String = StringUtils.EMPTY,
                     actionListener: () -> Unit = {}) {
        if (::snackbar.isInitialized) dismissSnackBar()
        snackbar = Snackbar.make(view, message, duration)
        if (action != StringUtils.EMPTY) {
            snackbar.setAction(action) {
                actionListener()
                snackbar.dismiss()
            }
        }
        snackbar.show()
    }

    fun dismissSnackBar() {
        if (::snackbar.isInitialized)
            snackbar.dismiss()
    }

}