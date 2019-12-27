package ru.skillbranch.devintensive.test

import ru.skillbranch.devintensive.extensions.TimeUnits
import ru.skillbranch.devintensive.extensions.stripHtml
import ru.skillbranch.devintensive.extensions.truncate
import ru.skillbranch.devintensive.models.User
import ru.skillbranch.devintensive.utils.Utils.toInitials
import java.util.*


fun main() {


  println(validate("dsds"))

  val setMotor = 4000

  val b = false

  if (!b && setMotor > 2700)
    println("2700")
  else
    println(setMotor)
}

fun validate(str: String): String {
  return if ("\\D+".toRegex().findAll(str).none())
    str
  else
    return "Год моего рождения должен содержать только цифры"
}


