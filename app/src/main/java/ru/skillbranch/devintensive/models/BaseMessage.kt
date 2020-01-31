package ru.skillbranch.devintensive.models

import ru.skillbranch.devintensive.models.data.Chat
import ru.skillbranch.devintensive.test.UserTest
import java.util.*

abstract class BaseMessage(
  val id: String,
  val from: UserTest?,
  val chat: Chat,
  val isIncoming: Boolean = false,
  val date: Date = Date()
) {
  abstract fun formatMessage(): String

  companion object AbstractFactory {
    private var lastId = -1
    fun makeMessage(
      from: UserTest?,
      chat: Chat,
      date: Date = Date(),
      type: String = "text",
      payload: Any?
    ): BaseMessage {
      lastId++

      return when (type) {
        "image" -> ImageMessage("$lastId", from, chat, date = date, image = payload as String)
        else -> TextMessage("$lastId", from, chat, date = date, text = payload as String)
      }
    }
  }
}