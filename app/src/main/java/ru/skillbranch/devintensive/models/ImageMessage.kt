package ru.skillbranch.devintensive.models

import ru.skillbranch.devintensive.extension.humanizeDiff
import ru.skillbranch.devintensive.test.UserTest
import java.util.*

class ImageMessage(
  id: String,
  from: UserTest?,
  chat: Chat,
  isIncoming: Boolean = false,
  date: Date = Date(),
  var image: String?
) : BaseMessage(id, from, chat, isIncoming, date) {

  override fun formatMessage() =
    "id:$id ${from?.firstName} ${if (isIncoming) "получил" else "отправил"} изображение \"$image\" ${date.humanizeDiff()}"
}