package com.example.telstrademo.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.telstrademo.R
import com.example.telstrademo.data.api.ApiHelper
import com.example.telstrademo.data.api.RetrofitBuilder
import com.example.telstrademo.data.model.Rows
import com.example.telstrademo.ui.base.ViewModelFactory
import com.example.telstrademo.ui.main.adapter.FactListAdapter
import com.example.telstrademo.ui.main.viewmodel.MainViewModel
import com.example.telstrademo.utility.ResponseStatus
import kotlinx.android.synthetic.main.activity_main.*

class FactListActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: FactListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewModel()
        setupUI()
        setupObservers()
        /*Pull to refresh event */
        swipeToRefreshContainer.setOnRefreshListener {
            setupObservers()
        }

    }

    /* Initialise viewModel object */
    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(MainViewModel::class.java)
    }

    /* Initialise UI component  */
    private fun setupUI() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = FactListAdapter(arrayListOf())
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter
    }

    /* Observes tha data from the viewmodel and initiate flow according to response status  */
    private fun setupObservers() {
        viewModel.getFactDetails().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    ResponseStatus.SUCCESS -> {
                        swipeToRefreshContainer.isRefreshing = false
                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        resource.data?.let { mainResponse ->
                            supportActionBar?.title = mainResponse.title
                            retrieveList(mainResponse.rows)
                        }
                    }
                    ResponseStatus.ERROR -> {
                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    ResponseStatus.LOADING -> {
                        if (!swipeToRefreshContainer.isRefreshing) {
                            progressBar.visibility = View.VISIBLE
                            recyclerView.visibility = View.GONE
                        } else {
                            recyclerView.visibility = View.VISIBLE
                            progressBar.visibility = View.GONE
                        }
                    }

                }
            }
        })
    }

    /** function used to refresh data of recyclerview
     * @param factList list items to be added
     */
    private fun retrieveList(factList: List<Rows>) {
        adapter.apply {
            updateListData(factList)
            notifyDataSetChanged()
        }
    }
}
