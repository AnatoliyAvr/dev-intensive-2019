package ru.skillbranch.devintensive.extensions

import android.content.Context

fun Context.pxToDp(px: Int): Float {
  return (px / resources.displayMetrics.density)
}

fun Context.dpToPx(dp: Float): Float {
  return (dp * resources.displayMetrics.density)
}

fun Context.spToPx(sp: Int): Float {
  return sp * resources.displayMetrics.scaledDensity
}

fun Context.dpToPx(dp:Int): Float {
  return dp.toFloat() * this.resources.displayMetrics.density
}