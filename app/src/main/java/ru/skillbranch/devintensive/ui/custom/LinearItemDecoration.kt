package ru.skillbranch.devintensive.ui.custom

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.skillbranch.devintensive.extensions.dpToPx

class LinearItemDecoration : RecyclerView.ItemDecoration() {

  companion object {
    private const val TAG = "LinearItemDecoration"
  }

  private val paint: Paint
  private val dividerPaint: Paint

  init {
    paint = Paint(Paint.ANTI_ALIAS_FLAG)
    paint.color = Color.BLUE
    paint.style = Paint.Style.STROKE
    paint.strokeWidth = 5f

    dividerPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    dividerPaint.color = Color.parseColor("#e6e6e6")
    dividerPaint.style = Paint.Style.FILL
  }

  override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
    super.getItemOffsets(outRect, view, parent, state)
    Log.e("ItemOffsets", "getItemOffsets: ")
    outRect.bottom = 5
    outRect.left = 0//100
  }

  override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
    super.onDraw(canvas, parent, state)
    Log.e(TAG, "onDraw: ")
    val layoutManager = parent.layoutManager
    for (i in 0 until parent.childCount) {
      val childView = parent.getChildAt(i)
      val leftDecorationWidth = layoutManager!!.getLeftDecorationWidth(childView) + parent.context.dpToPx(72f).toInt()
      layoutManager.getTopDecorationHeight(childView)
      layoutManager.getRightDecorationWidth(childView)
      val bottomDecorationHeight = layoutManager.getBottomDecorationHeight(childView)
      canvas.drawRect(
        Rect(
          leftDecorationWidth,
          childView.bottom,
          childView.width + leftDecorationWidth,
          childView.bottom + bottomDecorationHeight
        ), dividerPaint
      )
    }
  }
}
