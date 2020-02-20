package ru.skillbranch.devintensive.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ru.skillbranch.devintensive.extensions.mutableLiveData
import ru.skillbranch.devintensive.models.data.ChatItem
import ru.skillbranch.devintensive.repositories.ChatRepository

class MainViewModel : ViewModel() {

  private val chatRepository = ChatRepository

  private val query = mutableLiveData("")
  private val chats = chatRepository.loadChats()
  private val chatItems = Transformations.map(chats) { chats ->
    chats.filter { !it.isArchived }
      .map { it.toChatItem() }
      .sortedBy { it.id.toInt() }
  }


  fun getChatData(): LiveData<List<ChatItem>> {
    val result = MediatorLiveData<List<ChatItem>>()

    val filter = {
      val queryStr = query.value!!
      val chats = chatItems.value!!

      result.value =
        if (queryStr.isNotEmpty())
          chats.filter { it.title.contains(queryStr, true) }
        else chats
    }

    result.addSource(chatItems) { filter.invoke() }
    result.addSource(query) { filter.invoke() }
    return result
  }

  fun addToArchive(chatId: String) {
    val chat = chatRepository.find(chatId) ?: return
    chatRepository.update(chat.copy(isArchived = true))
  }

  fun restoreFromArchive(chatId: String) {
    val chat = chatRepository.find(chatId) ?: return
    chatRepository.update(chat.copy(isArchived = false))
  }

  fun handleSearchQuery(text: String?) {
    query.value = text
  }
}