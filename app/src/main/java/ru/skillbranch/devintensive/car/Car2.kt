package ru.skillbranch.devintensive.car

class Car2 (
  val model: String?,
  val year: Int
) {

  private constructor(builder: Builder) : this(builder.model, builder.year)

  companion object {
    inline fun build(block: Builder.() -> Unit) = Builder().apply(block).build()
  }

  class Builder {
    var model: String? = null
    var year: Int = 0

    fun build() = Car2(this)
  }
}