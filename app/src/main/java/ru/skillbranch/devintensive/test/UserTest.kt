package ru.skillbranch.devintensive.test

import ru.skillbranch.devintensive.utils.Utils.parseFullName
import java.util.*

data class UserTest(
  val id : String,
  var firstName : String?,
  var lastName : String?,
  var avatar : String?,
  var rating : Int = 0,
  var respect : Int = 0,
  var lastVisit : Date? = Date(),
  var isOnline : Boolean = false
) {

  var introBit: String = ""

  constructor(
    id: String,
    firstName: String?,
    lastName: String?
  ) : this(id = id, firstName = firstName, lastName = lastName, avatar = null)

  constructor(id: String) : this(id = id, firstName = "John", lastName = "Doe")

  init {
    //Равенство ссылок проверяется с помощью оператора === (и его отрицания !==).
    //Выражение a === b является истиной тогда и только тогда, когда a и b указывают на один и тот же объект.
    //Структурное равенство проверяется оператором == (и его отрицанием !=). Условно, выражение a == b транслируется в:
    //a?.equals(b) ?: (b === null)
//    introBit = getIntro()

    println(
//      "It's Alive!!! \n" +
//          "${if (lastName === "Doe") "His name id $firstName $lastName" else "And his name is $firstName $lastName!!!"}\n" +
//          "${getIntro()}!!!"

      "It's Alive!!! \n" +
          "${if (lastName === "Doe") "His name id $firstName $lastName" else "And his name is $firstName $lastName!!!"}\n"
    )
  }

  companion object Factory {
    private var lastId = -1
    fun makeUser(fullname: String?): UserTest {
      lastId++

      val (firstName, lastName) = parseFullName(fullname)

      return UserTest(
        id = "$lastId",
        firstName = firstName,
        lastName = lastName
      )
    }
  }

  private fun getIntro() = """
    tu tu ru tuuuuu !!!
    tu tu ru tuuuuuuuuu ...
    
    tu tu ru tuuuuu !!!
    tu tu ru tuuuuuuuuu ...
    ${"\n\n\n"}   
    $firstName $lastName    

  """.trimIndent()

  // Unit аналог void в java (shift + alt + click/wheel)
  fun printMe() = println(
    """
      id: $id
      firstName: $firstName
      lastName: $lastName
      avatar: $avatar
      rating: $rating
      respect: $respect
      lastVisit: $lastVisit
      isOnline: $isOnline
    """.trimIndent()
  )

}