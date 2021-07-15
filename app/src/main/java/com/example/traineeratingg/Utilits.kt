package com.example.traineeratingg

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment

fun makeToast(context: Context, arg: String) {
    Toast.makeText(context, arg, Toast.LENGTH_LONG).show()
}

fun split(string: String): List<String>{
    return string.split(' ')
}

fun Fragment.replaceFragment(fragment: Fragment) {
    /* Функция расширения для Fragment, позволяет устанавливать фрагменты */
    this.fragmentManager?.beginTransaction()
            ?.addToBackStack(null)
            ?.replace(
                    R.id.frame,
                    fragment
            )?.commit()
}