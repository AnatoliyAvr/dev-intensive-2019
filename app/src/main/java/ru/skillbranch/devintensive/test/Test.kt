package ru.skillbranch.devintensive.test

import ru.skillbranch.devintensive.extensions.TimeUnits
import ru.skillbranch.devintensive.extensions.stripHtml
import ru.skillbranch.devintensive.extensions.truncate
import ru.skillbranch.devintensive.models.User
import ru.skillbranch.devintensive.utils.Utils.toInitials
import java.util.*


fun main() {
  val id = "0"
  val firstName = "John"
  val lastName = "Doу"
  val avatar = "36548"
  val rating = 56
  val respect = 31
  val lastVisit = Date()
  val isOnline = true


  val u = User.Builder().id(id)
    .firstName(firstName)
    .lastName(lastName)
    .avatar(avatar)
    .rating(rating)
    .respect(respect)
    .lastVisit(lastVisit)
    .isOnline(isOnline)
    .build()

  println(u)

  println(TimeUnits.SECOND.plural(1))
  println(TimeUnits.MINUTE.plural(4))
  println(TimeUnits.HOUR.plural(19))
  println(TimeUnits.DAY.plural(222))
  println(TimeUnits.SECOND.plural(13))
  println(TimeUnits.MINUTE.plural(41))
  println(TimeUnits.HOUR.plural(11))
  println(TimeUnits.DAY.plural(47347))
  println()

  println("Bender Bending Rodriguez — дословно «Сгибальщик Сгибающий Родригес»".truncate()) //Bender Bending R...
  println("Bender Bending Rodriguez — дословно «Сгибальщик Сгибающий Родригес»".truncate(15)) //Bender Bending...
  println("A     ".truncate(3)) //A
  println("A dsd ds sd r9   ".truncate(13)) //A


  println("<p class=\"title\">Образовательное IT-сообщество Skill Branch</p>".stripHtml()) //Образовательное IT-сообщество Skill Branch
  println("<p>Образовательное       IT-сообщество   Skill Branch</p>".stripHtml()) //Образовательное IT-сообщество Skill Branch

  println(toInitials("John","Doe"))
  println(toInitials(null,"Doe"))
  println(toInitials("John",null))
}

