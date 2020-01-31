package ru.skillbranch.devintensive.utils

import ru.skillbranch.devintensive.models.data.Chat

object DataGenerator {

  private val maleNames = listOf(
    "Robin",
    "James",
    "John",
    "Robert",
    "Michael",
    "William",
    "David",
    "Richard",
    "Charles",
    "Joseph",
    "Tomas",
    "Christopher",
    "Daniel",
    "Paul",
    "Mark",
    "Donald",
    "George",
    "Kenneth",
    "Steven",
    "Edward",
    "Brian",
    "Ronald",
    "Anthony",
    "Kevin",
    "Jason",
    "Matthew",
    "Gary"
  )

  fun generateChats(names: Int): List<Chat> {
    val chat = mutableListOf<Chat>()
    return chat
  }

  fun generateChatsWithOffset(names: Int, id: Int): List<Chat> {
    val chat = mutableListOf<Chat>()
    return chat
  }
}