package ru.skillbranch.devintensive.models

class UserView(
  val id: String,
  val fullname: String,
  val nickname: String,
  var avatar: String?,
  var status: String? = "offline",
  val initials: String?
) {
  fun printMe() {
    println(
      """
      id: $id
      fullname: $fullname
      nickname: $nickname
      avatar: $avatar
      status: $status
      initials: $initials
    """.trimIndent()
    )
  }
}