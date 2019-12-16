package ru.skillbranch.devintensive.utils

import java.util.*

object Utils {
  fun parseFullName(fullname: String?): Pair<String?, String?> {
    val parts: List<String>? = fullname?.split(" ")

    var firstName = parts?.getOrNull(0)
    var lastName = parts?.getOrNull(1)

    if (firstName?.trim()?.length == 0) firstName = null
    if (lastName?.trim()?.length == 0) lastName = null

//    return Pair(firstName, lastName)
    return firstName to lastName
  }

  fun transliteration(payload: String, divider: String = " "): String {
    val ru = arrayOf(
      "А",
      "Б",
      "В",
      "Г",
      "Д",
      "Е",
      "Ё",
      "Ж",
      "З",
      "И",
      "Й",
      "К",
      "Л",
      "М",
      "Н",
      "О",
      "П",
      "Р",
      "С",
      "Т",
      "У",
      "Ф",
      "Х",
      "Ц",
      "Ч",
      "Ш",
      "Щ",
      "Ъ",
      "Ы",
      "Ь",
      "Э",
      "Ю",
      "Я",
      "а",
      "б",
      "в",
      "г",
      "д",
      "е",
      "ё",
      "ж",
      "з",
      "и",
      "й",
      "к",
      "л",
      "м",
      "н",
      "о",
      "п",
      "р",
      "с",
      "т",
      "у",
      "ф",
      "х",
      "ц",
      "ч",
      "ш",
      "щ",
      "ъ",
      "ы",
      "ь",
      "э",
      "ю",
      "я"
    )
    val eng = arrayOf(
      "A",
      "B",
      "V",
      "G",
      "D",
      "E",
      "E",
      "Zh",
      "Z",
      "I",
      "I",
      "K",
      "L",
      "M",
      "N",
      "O",
      "P",
      "R",
      "S",
      "T",
      "U",
      "F",
      "H",
      "C",
      "Ch",
      "Sh",
      "Sh",
      "",
      "I",
      "",
      "E",
      "Yu",
      "Ya",
      "a",
      "b",
      "v",
      "g",
      "d",
      "e",
      "e",
      "zh",
      "z",
      "i",
      "i",
      "k",
      "l",
      "m",
      "n",
      "o",
      "p",
      "r",
      "s",
      "t",
      "u",
      "f",
      "h",
      "c",
      "ch",
      "sh",
      "sh",
      "",
      "i",
      "",
      "e",
      "yu",
      "ya"
    )
    var str = ""

    for (element in payload.trim()) {
      for (j in ru.indices) {
        if (element.toString() == " ") {
          str += divider
          break
        }
        if (element.toString() == ru[j])
          str += eng[j]

        if (element.toString() == eng[j]) {
          str += eng[j]
          break
        }
      }
    }
    return str
  }

  fun toInitials(firstName: String?, lastName: String?): String? {
    return when {
      firstName?.trim().isNullOrEmpty() && lastName?.trim().isNullOrEmpty() -> null
      firstName?.trim().isNullOrEmpty() -> lastName?.toUpperCase(Locale("ru"))?.get(0).toString()
      lastName?.trim().isNullOrEmpty() -> firstName!!.toUpperCase(Locale("ru"))[0].toString()
      else -> firstName!!.trim().toUpperCase(Locale("ru"))[0].toString() +
          lastName!!.trim().toUpperCase(Locale("ru"))[0].toString()
    }
  }

//  fun toInitials(firstName: String?, lastName: String?): String? {
//    if (firstName.isNullOrBlank() && lastName.isNullOrBlank()) {
//      return null
//    }
//    return "${if (!firstName.isNullOrBlank()) firstName.toUpperCase().trim()[0] else ""}${if (!lastName.isNullOrBlank()) lastName.toUpperCase().trim()[0] else ""}"
//  }


}