package com.nahlasamir244.mvi_architecture_demo.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nahlasamir244.mvi_architecture_demo.R
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nahlasamir244.mvi_architecture_demo.data.api.ApiHelperImpl
import com.nahlasamir244.mvi_architecture_demo.data.api.RetrofitBuilder
import com.nahlasamir244.mvi_architecture_demo.data.model.User
import com.nahlasamir244.mvi_architecture_demo.ui.main.adapter.MainAdapter
import com.nahlasamir244.mvi_architecture_demo.ui.main.intent.MainIntent
import com.nahlasamir244.mvi_architecture_demo.ui.main.viewmodel.MainViewModel
import com.nahlasamir244.mvi_architecture_demo.ui.main.viewstate.MainState
import com.nahlasamir244.util.ViewModelFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

//This is the user-facing activity that takes input from the user,
// based on this MVI checks for the states mentioned in viewModel and
// loads the particular state in the view.
//we are sending the intent to fetch the data on button click(User Action).
//we are observing on the ViewModel State for the state changes.
// And, using "when" condition we are comparing the response intent state
// and loading the respective states
@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private var adapter = MainAdapter(arrayListOf())
    private lateinit var buttonFetchUser:Button
    private lateinit var recyclerView:RecyclerView
    private lateinit var progressBar:ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
        setupViewModel()
        observeViewModel()
        setupClicks()
    }

    private fun setupClicks() {
        buttonFetchUser.setOnClickListener {
            lifecycleScope.launch {
                mainViewModel.userIntent.send(MainIntent.FetchUser)
            }
        }
    }


    private fun setupUI() {
        buttonFetchUser = findViewById(R.id.buttonFetchUser)
        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.run {
            addItemDecoration(
                DividerItemDecoration(
                    recyclerView.context,
                    (recyclerView.layoutManager as LinearLayoutManager).orientation
                )
            )
        }
        recyclerView.adapter = adapter
    }


    private fun setupViewModel() {
        mainViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(
                ApiHelperImpl(
                    RetrofitBuilder.apiService
                )
            )
        ).get(MainViewModel::class.java)
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            mainViewModel.state.collect {
                when (it) {
                    is MainState.Loading -> {
                        buttonFetchUser.visibility = View.GONE
                        progressBar.visibility = View.VISIBLE
                    }

                    is MainState.Users -> {
                        progressBar.visibility = View.GONE
                        buttonFetchUser.visibility = View.GONE
                        renderList(it.users)
                    }
                    is MainState.Error -> {
                        progressBar.visibility = View.GONE
                        buttonFetchUser.visibility = View.VISIBLE
                        Toast.makeText(this@MainActivity,
                            it.error, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun renderList(users: List<User>) {
        recyclerView.visibility = View.VISIBLE
        users.let { listOfUsers -> listOfUsers.let { adapter.addData(it) } }
        adapter.notifyDataSetChanged()
    }
}