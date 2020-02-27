package ru.skillbranch.devintensive.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import by.tolikavr.dev_intensive_recycler_view.R
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_chat_archive.*
import ru.skillbranch.devintensive.models.data.ChatItem

class ArchiveAdapter(private val listener: (ChatItem) -> Unit) : RecyclerView.Adapter<ArchiveAdapter.ArchiveViewHolder>() {

  var items: List<ChatItem> = listOf()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArchiveViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    val convertView = inflater.inflate(R.layout.item_chat_archive, parent, false)
    return ArchiveViewHolder(convertView)
  }

  override fun getItemCount(): Int = items.size

  override fun onBindViewHolder(holder: ArchiveViewHolder, position: Int) = holder.bind(items[position], listener)

  fun updateData(data: List<ChatItem>) {
    val diffCallback = object : DiffUtil.Callback() {

      override fun areContentsTheSame(oldPos: Int, newPos: Int): Boolean =
        items[oldPos].id == data[newPos].id

      override fun areItemsTheSame(oldPos: Int, newPos: Int): Boolean =
        items[oldPos].hashCode() == data[newPos].hashCode()

      override fun getOldListSize(): Int = items.size

      override fun getNewListSize(): Int = data.size
    }

    val diffResult = DiffUtil.calculateDiff(diffCallback)
    items = data
    diffResult.dispatchUpdatesTo(this)
  }

  inner class ArchiveViewHolder(convertView: View) : RecyclerView.ViewHolder(convertView), LayoutContainer, ItemTouchViewHolder {

    override fun onItemSelected() {
      itemView.setBackgroundColor(Color.LTGRAY)
    }

    override fun onItemCleared() {
      itemView.setBackgroundColor(Color.WHITE)
    }

    override val containerView: View?
      get() = itemView


    fun bind(user: ChatItem, listener: (ChatItem) -> Unit) {
      if (user.avatar == null) {
        Glide.with(itemView).clear(user.avatar)
        iv_avatar_archive.setInitials(user.initials)
      }
      Glide.with(itemView)
        .load(user.avatar)
        .into(iv_avatar_archive)

      sv_indicator_archive.visibility = if (user.isOnline) View.VISIBLE else View.GONE
      with(tv_date_archive) {
        visibility = if (user.lastMessageDate != null) View.VISIBLE else View.GONE
        text = user.lastMessageDate
      }

      with(tv_counter_archive) {
        visibility = if (user.messageCount > 0) View.VISIBLE else View.GONE
        text = user.messageCount.toString()
      }
      tv_title_archive.text = user.title
      tv_message_archive.text = user.shortDescription
      itemView.setOnClickListener {
        listener.invoke(user)
      }
    }
  }
}