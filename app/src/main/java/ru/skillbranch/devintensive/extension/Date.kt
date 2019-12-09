package ru.skillbranch.devintensive.extension

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
  val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
  return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {
  var time = this.time

  time += when (units) {
    TimeUnits.SECOND -> value * SECOND
    TimeUnits.MINUTE -> value * MINUTE
    TimeUnits.HOUR -> value * HOUR
    TimeUnits.DAY -> value * DAY
  }

  this.time = time
  return this
}

enum class TimeUnits : Plural {
  SECOND {
    override fun plural(value: Int): String {
      return "$value ${seconds(value)}"
    }

  },
  MINUTE {
    override fun plural(value: Int): String {
      return "$value ${minutes(value)}"
    }
  },
  HOUR {
    override fun plural(value: Int): String {
      return "$value ${hours(value)}"
    }
  },
  DAY {
    override fun plural(value: Int): String {
      return "$value ${days(value)}"
    }
  }
}

interface Plural {
  fun plural(value: Int): String
}

fun Date.humanizeDiff(date: Date = Date()): String {
  val n = date.time - this.time

  return when {
    (date.time - this.time) / SECOND in 0..1 -> "только что"
    (date.time - this.time) / SECOND in 2..45 -> "несколько секунд назад"
    (date.time - this.time) / SECOND in 46..75 -> "минуту назад"
    (date.time - this.time) / SECOND in 76..2700 -> "${n / MINUTE} ${minutes((n / MINUTE).toInt())} назад"
    (date.time - this.time) / SECOND in 2701..4500 -> "час назад"
    (date.time - this.time) / SECOND in 4501..79200 -> "${n / HOUR} ${hours((n / HOUR).toInt())} назад"
    (date.time - this.time) / SECOND in 79201..93600 -> "день назад"
    (date.time - this.time) / SECOND in 93601..31104000 -> "${n / DAY} ${days((n / DAY).toInt())} назад"
    (date.time - this.time) / SECOND in 31104000..Int.MAX_VALUE -> "более года назад"

    (date.time - this.time) / SECOND in -1..0 -> "только что"
    (date.time - this.time) / SECOND in -45..2 -> "несколько секунд назад"
    (date.time - this.time) / SECOND in -75..46 -> "минуту назад"
    (date.time - this.time) / SECOND in -2700..76 -> "через ${abs(n) / MINUTE} ${minutes((abs(n) / MINUTE).toInt())}"
    (date.time - this.time) / SECOND in -4500..2701 -> "чqас назад"
    (date.time - this.time) / SECOND in -79200..4501 -> "через  ${abs(n) / HOUR} ${hours((abs(n) / HOUR).toInt())}"
    (date.time - this.time) / SECOND in -93600..79201 -> "день назад"
    (date.time - this.time) / SECOND in -31104000..93601 -> "через ${abs(n) / DAY} ${days((abs(n) / DAY).toInt())} "
    (date.time - this.time) / SECOND in -Int.MAX_VALUE..31104000 -> "более чем через год"

    else -> ""
  }
}

private fun seconds(n: Int): String {
  val n10 = n % 10
  return when {
    (n10 == 1 && n != 11) -> "секунду"
    ((n10 == 2 || n10 == 3 || n10 == 4) && n !in 12..14) -> "секунды"
    else -> "секунд"
  }
}

private fun minutes(n: Int): String {
  val n10 = n % 10
  return when {
    (n10 == 1 && n != 11) -> "минуту"
    ((n10 == 2 || n10 == 3 || n10 == 4) && n !in 12..14) -> "минуты"
    else -> "минут"
  }
}

private fun hours(n: Int): String {
  val n10 = n % 10
  return if (n10 == 1 && n != 11) "час"
  else if ((n10 == 2 || n10 == 3 || n10 == 4) && n !in 12..14) "часа"
  else "часов"
}

private fun days(n: Int): String {
  val n10 = n % 10
  return if (n10 == 1 && n != 11) "день"
  else if ((n10 == 2 || n10 == 3 || n10 == 4) && n !in 12..14) "дня"
  else "дней"
}