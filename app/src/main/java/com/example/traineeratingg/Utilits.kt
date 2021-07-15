package com.example.traineeratingg

import android.content.Context
import android.widget.Toast

fun makeToast(context: Context, arg: String) {
    Toast.makeText(context, arg, Toast.LENGTH_LONG).show()
}

fun split(string: String): List<String>{
    return string.split(' ')
}