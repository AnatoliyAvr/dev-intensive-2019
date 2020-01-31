package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.graphics.Rect
import android.view.View
import android.view.inputmethod.InputMethodManager

import android.content.Context
import android.util.TypedValue
import kotlin.math.roundToInt

fun Activity.hideKeyboard() {
  val imm =
    getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
  var view: View? = currentFocus
  if (view == null) {
    view = View(applicationContext)
  }
  imm.hideSoftInputFromWindow(view.windowToken, 0)
}

private fun Activity.getRootView(): View {
  return findViewById(android.R.id.content)
}

private fun  Context.convertDpToPx(dp: Float): Float {
  return TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    dp,
    this.resources.displayMetrics
  )
}


fun Activity.isKeyboardOpen(): Boolean {
  val visibleBounds = Rect()
  this.getRootView().getWindowVisibleDisplayFrame(visibleBounds)
  val heightDiff = getRootView().height - visibleBounds.height()
  val marginOfError = this.convertDpToPx(50F).roundToInt()
  return heightDiff > marginOfError
}

fun Activity.isKeyboardClosed(): Boolean {
  return !this.isKeyboardOpen()
}

