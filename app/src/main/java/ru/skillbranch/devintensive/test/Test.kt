package ru.skillbranch.devintensive.test


fun main() {


  println(validate("dsds"))

  val setMotor = 4000

  val b = false

  if (!b && setMotor > 2700)
    println("2700")
  else
    println(setMotor)


  listOf(0, 1, 2, null, 4, null, 6, 7).forEach {
    it?.let {
      println(it)
    } ?: println("null detected")
  }

  listOf(0, 1, 2, null, 4, null, 6, 7).forEach {
    it?.also {
      println(it)
    }?.also {
      println(it.toString())
    } ?: println("null detected")
  }

  fun checkServerResponse(code: Int): Boolean = code == 200

  fun showServerResponseMessage(isCodeValid: Boolean) = if (isCodeValid) {
    "с сервером всё в порядке"
  } else {
    "с сервером есть проблемы, выходные в опасности"
  }

  fun printMessage(message: String) = println(message)

  printMessage(showServerResponseMessage(checkServerResponse(200)))

  200
    .run(::checkServerResponse)
    .run(::showServerResponseMessage)
    .run(::printMessage)


  val startWithSpace = with("Зима, холода, одинокие дома") {
    if (this.startsWith(" ")) {
      "строка начинается с пробела"
    } else {
      "корректное начало строки"
    }
  }

  println(startWithSpace)

  val startWith = with(345) {
     this - 25
  }

  println(startWith)
}

fun validate(str: String): String {
  return if ("\\D+".toRegex().findAll(str).none())
    str
  else
    return "Год моего рождения должен содержать только цифры"
}


