package ru.skillbranch.devintensive.extensions

fun String.truncate(value: Int = 16): String {
  return when {
    this.trim() != this.substring(0, value).trim() -> this.substring(0, value).trim() + "..."
    else -> this.trim()
  }
}

fun String.stripHtml() =
  this.trim()
    .replace("<(?!\\/?a(?=>|\\s.*>))/?.*?>".toRegex(), "")
    .replace("\\s{2,}".toRegex(), " ")
