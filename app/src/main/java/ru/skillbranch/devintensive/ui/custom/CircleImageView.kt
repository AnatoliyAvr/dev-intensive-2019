package ru.skillbranch.devintensive.ui.custom

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff.Mode
import android.graphics.PorterDuffXfermode
import android.util.AttributeSet
import android.widget.ImageView
import ru.skillbranch.devintensive.extensions.dpToPx

class CircleImageView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : ImageView(context, attrs, defStyleAttr) {

  companion object {
    private const val DEFAULT_BORDER_COLOR: Int = Color.WHITE
  }

  private var borderColor = DEFAULT_BORDER_COLOR
  private var borderWidth = context.dpToPx(2F)
  private val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG)
  private val circlePaint = Paint(Paint.ANTI_ALIAS_FLAG)
  private val srcMode = PorterDuffXfermode(Mode.SRC)
  private val srcInMode = PorterDuffXfermode(Mode.SRC_IN)
}