package ru.skillbranch.devintensive.ui.activitys

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.children
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import by.tolikavr.dev_intensive_recycler_view.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.activity_group.*
import ru.skillbranch.devintensive.models.data.UserItem
import ru.skillbranch.devintensive.ui.adapters.UserAdapter
import ru.skillbranch.devintensive.ui.custom.LinearItemDecoration
import ru.skillbranch.devintensive.viewmodels.GroupViewModel

class GroupActivity : AppCompatActivity() {

  private lateinit var userAdapter: UserAdapter
  private lateinit var viewModel: GroupViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_group)

    initToolbar()
    initViews()
    initViewModel()
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_search, menu)
    val searchItem = menu?.findItem(R.id.action_search)
    val searchView = searchItem?.actionView as SearchView
    searchView.queryHint = "Введите имя пользователя"
    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
      override fun onQueryTextSubmit(query: String): Boolean {
        viewModel.handleSearchQuery(query)
        return true
      }

      override fun onQueryTextChange(query: String): Boolean {
        viewModel.handleSearchQuery(query)
        return true
      }
    })
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    return if (item?.itemId == android.R.id.home) {
      finish()
      overridePendingTransition(R.anim.idle, R.anim.bottom_down)
      true
    } else {
      super.onOptionsItemSelected(item!!)
    }
  }

  private fun initToolbar() {
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
  }

  private fun initViews() {
//    userAdapter = UserAdapter { viewModel.handleSelectedItem(it.id) }
    userAdapter = UserAdapter { viewModel.handleSelectedItem(it.id) }
    val divider = LinearItemDecoration()
    with(rv_user_list) {
      adapter = userAdapter
      layoutManager = LinearLayoutManager(this@GroupActivity)
      addItemDecoration(divider)
    }

    fab.setOnClickListener {
      viewModel.handleCreateGroup()
      finish()
      overridePendingTransition(R.anim.idle, R.anim.bottom_down) //animation
    }
  }

  private fun initViewModel() {
    viewModel = ViewModelProvider(this).get(GroupViewModel::class.java)
    viewModel.getUserData().observe(this, Observer { userAdapter.updateData(it) })
    viewModel.getSelectedData().observe(this, Observer {
      updateChips(it)
      toggleFab(it.size > 1)
    })
  }

  private fun toggleFab(isShow: Boolean) {
    if (isShow) fab.show()
    else fab.hide()
  }

  private fun addChipToGroup(user: UserItem) {
    val chip = Chip(this).apply {
      text = user.fullName
      if (user.avatar != null) {
        Glide.with(context)
          .load(user.avatar)
          .circleCrop()
          .into(object : CustomTarget<Drawable>() {
            override fun onLoadCleared(placeholder: Drawable?) {}
            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
              chipIcon = resource
            }
          })
      } else {
        Glide.with(context).clear(user.avatar)
        chipIcon = resources.getDrawable(R.drawable.avatar_default, theme)
      }
      isCloseIconVisible = true
      tag = user.id
      isClickable = false
      closeIconTint = ColorStateList.valueOf(Color.WHITE)
      chipBackgroundColor = ColorStateList.valueOf(getColor(R.color.color_primary_light))
      setTextColor(Color.WHITE)
    }
    chip.setOnCloseIconClickListener { viewModel.handleRemoveChip(it.tag.toString()) }
    chip_group.addView(chip)
  }

  private fun updateChips(listUsers: List<UserItem>) {
    chip_group.visibility = if (listUsers.isEmpty()) View.GONE else View.VISIBLE

    val users = listUsers.associateBy { user -> user.id }
      .toMutableMap()

    val views = chip_group.children.associateBy { view -> view.tag }
    for ((k, v) in views) {
      if (!users.containsKey(k)) chip_group.removeView(v)
      else users.remove(k)
    }

    users.forEach { (_, v) -> addChipToGroup(v) }
  }
}
