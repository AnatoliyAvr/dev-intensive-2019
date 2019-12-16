package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager


fun Activity.hideKeyboard() {
  val imm =
    getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
  var view: View? = currentFocus
  if (view == null) {
    view = View(applicationContext)
  }
  imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Activity.isKeyboardOpen() {}
fun Activity.isKeyboardClosed() {}
