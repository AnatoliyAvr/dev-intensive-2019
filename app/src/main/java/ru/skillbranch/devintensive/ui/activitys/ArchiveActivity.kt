package ru.skillbranch.devintensive.ui.activitys

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import by.tolikavr.dev_intensive_recycler_view.R
import kotlinx.android.synthetic.main.active_archive.*
import kotlinx.android.synthetic.main.activity_main.toolbar
import ru.skillbranch.devintensive.ui.adapters.ArchiveAdapter
import ru.skillbranch.devintensive.ui.adapters.ArchiveItemTouchHelperCallback
import ru.skillbranch.devintensive.ui.custom.LinearItemDecoration
import ru.skillbranch.devintensive.viewmodels.ArchiveViewModel

class ArchiveActivity : AppCompatActivity() {

  private lateinit var archiveAdapter: ArchiveAdapter
  private lateinit var viewModel: ArchiveViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.active_archive)

    initToolbar()
    initViews()
    initViewModel()
    closeActivity()

    Log.d("AAA", "${archiveAdapter.itemCount}")
  }

  private fun closeActivity() {
    if (archiveAdapter.items.isNotEmpty()) finish()
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_search, menu)
    val searchItem = menu?.findItem(R.id.action_search)
    val searchView = searchItem?.actionView as SearchView
    searchView.queryHint = "Введите имя пользователя"
    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
      override fun onQueryTextSubmit(query: String): Boolean {
//        viewModel.handleSearchQuery(query)
        return true
      }

      override fun onQueryTextChange(query: String): Boolean {
//        viewModel.handleSearchQuery(query)
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
  }

  private fun initViews() {
    archiveAdapter = ArchiveAdapter {
      //Snackbar.make(rv_chat_list, "Click on ${it.id}", Snackbar.LENGTH_LONG).show()
    }

    val divider = LinearItemDecoration()

    val touchCallback = ArchiveItemTouchHelperCallback(archiveAdapter) {
      if (viewModel.restoreFromArchive(it.id) == 0) finish()
    }
    val touchHelper = ItemTouchHelper(touchCallback)
    touchHelper.attachToRecyclerView(rv_archive_list)

    with(rv_archive_list) {
      adapter = archiveAdapter
      layoutManager = LinearLayoutManager(this@ArchiveActivity)
      addItemDecoration(divider)
    }
  }

  private fun initViewModel() {
    viewModel = ViewModelProvider(this).get(ArchiveViewModel::class.java)
    viewModel.getChatData().observe(this, Observer { archiveAdapter.updateData(it) })
  }
}