package com.jeremiahai.gadsleaderboard.utils

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.jeremiahai.gadsleaderboard.R

fun AppCompatActivity.showError(message: String) {
    showSnackBar(message, false, true)
}

fun AppCompatActivity.showSnackBar(
    message: String,
    static: Boolean,
    isError: Boolean = false
) {
    var snackBarLength = Snackbar.LENGTH_LONG

    if (static) snackBarLength = Snackbar.LENGTH_INDEFINITE

    val mySnackbar = Snackbar.make(
        (findViewById<View>(android.R.id.content) as ViewGroup),
        message, snackBarLength
    )

    if (isError) {
        mySnackbar.view.setBackgroundColor(
            ContextCompat.getColor(
                this,
                R.color.transparent_red_error
            )
        )
        mySnackbar.setTextColor(ContextCompat.getColor(this, R.color.red_error))
    } else mySnackbar.setAction("OK") {
        mySnackbar.dismiss()
    }


//    mySnackbar.setActionTextColor(ContextCompat.getColor(this, android.R.color.a))


    mySnackbar.show()
}