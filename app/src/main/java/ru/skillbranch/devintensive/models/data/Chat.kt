package ru.skillbranch.devintensive.models.data

import ru.skillbranch.devintensive.extensions.sohrtFormat
import ru.skillbranch.devintensive.models.BaseMessage
import ru.skillbranch.devintensive.utils.Utils
import java.util.*

class Chat(
  val id: String,
  val title: String,
  var members: List<User> = listOf(),
  val message: MutableList<BaseMessage> = mutableListOf(),
  var isArchived: Boolean = false
) {

  fun unreadebleMessageCount(): Int {
    //TODO implement me
    return 0
  }

  private fun lastMessageDate(): Date? {
    //TODO implement me
    return Date()
  }

  private fun lastMessageShort(): String {
    //TODO implement me
    return "Сообщений еще нет"
  }

  private fun isSingle(): Boolean = members.size == 1

  fun toChatItem(): ChatItem {
    return if (isSingle()) {
      val user = members.first()
      ChatItem(
        id,
        user.avatar,
        Utils.toInitials(user.firstName, user.lastName) ?: "??",
        "${user.firstName ?: ""} ${user.lastName ?: ""}",
        lastMessageShort(),
        unreadebleMessageCount(),
        lastMessageDate()?.sohrtFormat(),
        user.isOnline
      )
    } else {
      ChatItem(
        id,
        null,
        "",
        title,
        lastMessageShort(),
        unreadebleMessageCount(),
        lastMessageDate()?.sohrtFormat(),
        false
      )
    }
  }
}


