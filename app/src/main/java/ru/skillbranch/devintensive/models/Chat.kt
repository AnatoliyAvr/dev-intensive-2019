package ru.skillbranch.devintensive.models

import ru.skillbranch.devintensive.test.UserTest

class Chat(
  val id: String,
  val members: MutableList<UserTest> = mutableListOf(),
  val message: MutableList<BaseMessage> = mutableListOf()
)