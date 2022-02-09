package com.example.themoviedbapp.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import org.apache.commons.lang3.StringUtils
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.Duration
import java.util.*


fun String.convertStringToUpperCase(): String {
    /*
     * Program that first convert all uper case into lower case then
     * convert fist letter into uppercase
     */
    val calStr = this.split(" ").map { it.toLowerCase(Locale.ENGLISH).capitalize(Locale.ENGLISH) }
    return calStr.joinToString(separator = " ")
}

fun String.shortStringLength(): String {
    /*
     * Program that first convert all uper case into lower case then
     * convert fist letter into uppercase
     */
    var calStr = this
    if (this.length > 32)
        calStr = this.substring(0, 32).plus("...")
    return calStr
}

fun View.showSnackBar(
    message: String,
    duration: Int = Snackbar.LENGTH_LONG,
    action: String = StringUtils.EMPTY,
    actionListener: () -> Unit = {}
) {
    SnackBarUtils.showSnackBar(this, message, duration, action, actionListener)
}

fun View.dismissSnackBar(){
    SnackBarUtils.dismissSnackBar()
}

fun EditText.hideKeyboard() {
    val keyboard: InputMethodManager? =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    keyboard?.hideSoftInputFromWindow(windowToken, 0)
}

fun Calendar.addDays(day: Int): String {
    this.add(Calendar.DAY_OF_YEAR, day)
    return SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).format(this.time) //"dd-MM-yyyy HH:mm"
}

fun String.getCalendarDate(): Calendar {
    val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
    val calendar = Calendar.getInstance()
    try {
        val date = sdf.parse(this)
        date?.let {
            calendar.time = date
        }
        return calendar
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return calendar
}