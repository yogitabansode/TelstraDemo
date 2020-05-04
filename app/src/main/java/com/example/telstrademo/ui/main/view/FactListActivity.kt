package com.example.telstrademo.ui.main.view

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.telstrademo.R
import com.example.telstrademo.data.api.ApiHelper
import com.example.telstrademo.data.api.RetrofitBuilder
import com.example.telstrademo.data.model.FactDetailItem
import com.example.telstrademo.ui.base.ViewModelFactory
import com.example.telstrademo.ui.main.adapter.FactListAdapter
import com.example.telstrademo.ui.main.viewmodel.FactDetailViewModel
import com.example.telstrademo.utility.NetworkConnection
import com.example.telstrademo.utility.ResponseStatus
import kotlinx.android.synthetic.main.activity_main.*

class FactListActivity : AppCompatActivity() {

    private lateinit var viewModel: FactDetailViewModel
    private lateinit var adapter: FactListAdapter

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
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
        ).get(FactDetailViewModel::class.java)
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

    /* Check network availability and according call api to get result from server or show the error according  */
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun setupObservers() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (NetworkConnection.isNetworkConnected()) {
                getFactDetailsFromServer()
            } else {
                if (swipeToRefreshContainer.isRefreshing) {
                    swipeToRefreshContainer.isRefreshing = false
                }
                Toast.makeText(this, getString(R.string.no_network_available), Toast.LENGTH_LONG)
                    .show()
            }
        } else {
            if (NetworkConnection.isNetworkConnectedKitkat()) {
                getFactDetailsFromServer()

            } else {
                if (swipeToRefreshContainer.isRefreshing) {
                    swipeToRefreshContainer.isRefreshing = false
                }
                Toast.makeText(this, getString(R.string.no_network_available), Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    /* call to get the fact details from the server and initiate the flow according to response status */
    private fun getFactDetailsFromServer() {
        viewModel.factsData.observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    ResponseStatus.SUCCESS -> {
                        swipeToRefreshContainer.isRefreshing = false
                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        resource.data?.let { mainResponse ->
                            supportActionBar?.title = mainResponse.title
                            retrieveList(mainResponse.factDetailListItem)
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
     * @param factItem list items to be added
     */
    private fun retrieveList(factItem: List<FactDetailItem>) {
        adapter.apply {
            updateListData(factItem)
            notifyDataSetChanged()
        }
    }
}
