package ru.skillbranch.devintensive.car

class Car3 (
  val model: String?,
  val year: Int,
  val required: String
) {

  private constructor(builder: Builder) : this(builder.model, builder.year, builder.required)

  companion object {
    inline fun build(required: String, block: Builder.() -> Unit) = Builder(required).apply(block).build()
  }

  class Builder(
    val required: String
  ) {
    var model: String? = null
    var year: Int = 0

    fun build() = Car3(this)
  }
}