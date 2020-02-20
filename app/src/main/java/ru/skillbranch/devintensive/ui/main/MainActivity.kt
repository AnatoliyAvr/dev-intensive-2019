package ru.skillbranch.devintensive.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.*
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import by.tolikavr.dev_intensive_recycler_view.R
import ru.skillbranch.devintensive.models.data.User
import ru.skillbranch.devintensive.ui.adapters.ChatAdapter
import ru.skillbranch.devintensive.ui.adapters.ChatItemTouchHelperCallback
import ru.skillbranch.devintensive.ui.custom.LinearItemDecoration
import ru.skillbranch.devintensive.ui.group.GroupActivity
import ru.skillbranch.devintensive.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

  private lateinit var chatAdapter: ChatAdapter
  private lateinit var viewModel: MainViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    //testMutableLiveData()

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
  }

  private fun initViews() {
    chatAdapter = ChatAdapter {
      Snackbar.make(rv_chat_list, "Click on ${it.title}", Snackbar.LENGTH_LONG).show()
    }

    val divider = LinearItemDecoration()

    val touchCallback = ChatItemTouchHelperCallback(chatAdapter) {
      viewModel.addToArchive(it.id)
      Snackbar.make(rv_chat_list, "Вы точно хотите добавить ${it.title} в архив?", Snackbar.LENGTH_LONG)
        .setAction(R.string.snackbar_archive_cancel) { _ ->
          viewModel.restoreFromArchive(it.id)
        }.show()
    }
    val touchHelper = ItemTouchHelper(touchCallback)
    touchHelper.attachToRecyclerView(rv_chat_list)

    with(rv_chat_list) {
      adapter = chatAdapter
      layoutManager = LinearLayoutManager(this@MainActivity)
      addItemDecoration(divider)
    }

    fab.setOnClickListener {
      val intent = Intent(this, GroupActivity::class.java)
      startActivity(intent)
    }
  }

  private fun initViewModel() {
    viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    viewModel.getChatData().observe(this, Observer { chatAdapter.updateData(it) })
  }

  //---------------------------------------------------------------------------------------------------------------------//
  private fun testMutableLiveData() {
    val liveDataStr: LiveData<String> = MutableLiveData("35682")
    val liveDataInt: LiveData<Int>

    liveDataInt = Transformations.map(liveDataStr) {
      return@map it.toInt()
    }

    liveDataInt.observe(this, Observer { Log.d("LiveData", "$it") })

    //-----------------------------------------------------------------------//

    val liveDataLong1: LiveData<Long> = MutableLiveData(1L)
    val liveDataLong2: LiveData<Long> = MutableLiveData(2L)
    val liveDataUser1: LiveData<LiveData<User>>
    val liveDataUser2: LiveData<User>
    val liveDataInt1: LiveData<LiveData<Int>>
    val liveDataInt2: LiveData<Int>

    liveDataUser1 = Transformations.map(liveDataLong1) { return@map getUser(it) }
    liveDataUser2 = Transformations.switchMap(liveDataLong2) { return@switchMap getUser(it) }

    liveDataUser1.observe(this, Observer {
      Log.d("LiveData", "${it.value}")
    })

    liveDataUser2.observe(this, Observer {
      Log.d("LiveData", "${it.id}")
    })

    liveDataInt1 = Transformations.map(liveDataLong1) { return@map getInt(it) }
    liveDataInt1.observe(this@MainActivity, Observer {
      Log.d("LiveData", "${it.value}")
    })

    liveDataInt2 = Transformations.switchMap(liveDataLong2) { return@switchMap getInt(it) }
    liveDataInt2.observe(this@MainActivity, Observer {
      Log.d("LiveData", "${it}")
    })
    //-----------------------------------------------------------------------//

    val mediatorLiveData = MediatorLiveData<LiveData<User>>()

    mediatorLiveData.addSource(liveDataLong1) {
      mediatorLiveData.value = getUser(it)
    }

    mediatorLiveData.addSource(liveDataLong2) {
      mediatorLiveData.value = getUser(it)
    }

    mediatorLiveData.observe(this, Observer {
      Log.d("LiveData", "${it.value}")
    })
  }

  private fun getUser(id: Long): LiveData<User> {
    val usr1 = User(
      "1",
      "Jon",
      "Doe"
    )

    val usr2 = User(
      "2",
      "Bill",
      "Bole"
    )
    return when (id) {
      1L -> MutableLiveData(usr1)
      2L -> MutableLiveData(usr2)
      else -> MutableLiveData(null)
    }
  }

  private fun getInt(id: Long): LiveData<Int> {
    return MutableLiveData(id.toInt())
  }
}
