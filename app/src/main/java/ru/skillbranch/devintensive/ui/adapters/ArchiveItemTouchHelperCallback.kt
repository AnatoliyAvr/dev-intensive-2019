package ru.skillbranch.devintensive.ui.adapters

import android.graphics.*
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import by.tolikavr.dev_intensive_recycler_view.R
import ru.skillbranch.devintensive.models.data.ChatItem
import ru.skillbranch.devintensive.utils.Utils
import kotlin.math.abs


class ArchiveItemTouchHelperCallback(
  private val adapter: ArchiveAdapter,
  private val swipeListener: (ChatItem) -> Unit
) : ItemTouchHelper.Callback() {

  private val bgRect = RectF()
  private val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG)
  private val iconBounds = Rect()

  override fun getMovementFlags(
    recyclerView: RecyclerView,
    viewHolder: RecyclerView.ViewHolder
  ): Int {
    return if (viewHolder is ItemTouchViewHolder) {
      makeFlag(ItemTouchHelper.ACTION_STATE_SWIPE, ItemTouchHelper.END)
    } else {
      makeFlag(ItemTouchHelper.ACTION_STATE_IDLE, ItemTouchHelper.START)
    }
  }

  override fun onMove(
    recyclerView: RecyclerView,
    viewHolder: RecyclerView.ViewHolder,
    target: RecyclerView.ViewHolder
  ): Boolean = false

  override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
    swipeListener.invoke(adapter.items[viewHolder.adapterPosition])
  }

  override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
    if (actionState != ItemTouchHelper.ACTION_STATE_IDLE && viewHolder is ItemTouchViewHolder) {
      viewHolder.onItemSelected()
    }
    super.onSelectedChanged(viewHolder, actionState)
  }

  override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
    if (viewHolder is ItemTouchViewHolder) viewHolder.onItemCleared()
    super.clearView(recyclerView, viewHolder)
  }

  override fun onChildDraw(
    canvas: Canvas,
    recyclerView: RecyclerView,
    viewHolder: RecyclerView.ViewHolder,
    dX: Float,
    dY: Float,
    actionState: Int,
    isCurrentlyActive: Boolean
  ) {
    if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
      val itemView = viewHolder.itemView
      drawBackground(canvas, itemView, dX)
      drawIcon(canvas, itemView, dX)
    }
    super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
  }

  private fun drawIcon(canvas: Canvas, itemView: View, dX: Float) {
    val icon = itemView.resources.getDrawable(R.drawable.ic_unarchive_black_24dp, itemView.context.theme)
    val iconSize = itemView.resources.getDimensionPixelSize(R.dimen.icon_size)
    val space = itemView.resources.getDimensionPixelSize(R.dimen.spacing_normal_16)

    val margin = (itemView.bottom - itemView.top - iconSize) / 2
    with(iconBounds) {
      left = itemView.left + dX.toInt() - iconSize - space
      top = itemView.top + margin
      right = itemView.left + dX.toInt() - space
      bottom = itemView.bottom - margin
    }

    icon.bounds = iconBounds
    icon.draw(canvas)
  }

  private fun drawBackground(canvas: Canvas, itemView: View, dX: Float) {
    with(bgRect) {
      left = itemView.left.toFloat()
      top = itemView.top.toFloat()
      right = itemView.right.toFloat() + dX
      bottom = itemView.bottom.toFloat()
    }

    with(bgPaint) {
      color = Color.argb(Utils.interpolate(abs(dX.toInt()), 1080, 0, 1, 255), 28, 14, 66)
    }
    canvas.drawRect(bgRect, bgPaint)
  }
}