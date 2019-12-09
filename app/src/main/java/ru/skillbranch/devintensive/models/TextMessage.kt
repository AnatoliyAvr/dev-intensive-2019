package ru.skillbranch.devintensive.models

import ru.skillbranch.devintensive.extension.humanizeDiff
import ru.skillbranch.devintensive.test.UserTest
import java.util.*

class TextMessage(
  id: String,
  from: UserTest?,
  chat: Chat,
  isIncoming: Boolean = false,
  date: Date = Date(),
  var text: String?
) : BaseMessage(id, from, chat, isIncoming, date) {

  override fun formatMessage(): String =
    "id:$id ${from?.firstName} ${if (isIncoming) "получил" else "отправил"} сообщение \"$text\" ${date.humanizeDiff()}"
}