package ru.skillbranch.devintensive.extensions

import ru.skillbranch.devintensive.test.UserTest
import ru.skillbranch.devintensive.models.UserView
import ru.skillbranch.devintensive.utils.Utils

fun UserTest.toUserView(): UserView {

  val nickname = Utils.transliteration("$firstName $lastName")
  val initials = Utils.toInitials(firstName, lastName)
  val status = if (lastVisit == null) "Ещк ни разу не был" else if (isOnline) "online" else
    "Последний раз был ${lastVisit?.humanizeDiff()}"

  return UserView(
    id,
    fullname = "$firstName $lastName",
    nickname = nickname,
    initials = initials,
    avatar = avatar,
    status = status
  )
}


