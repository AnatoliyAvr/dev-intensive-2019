package ru.skillbranch.devintensive.models.data

data class ChatItem(
  val id: String,
  var avatar: String?,
  var initials: String,
  var title: String,
  var shortDescription: String?,
  var messageCount: Int = 0,
  var lastMessageDate: String?,
  var isOnline: Boolean = false
)