package com.example.telstrademo.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.telstrademo.R
import com.example.telstrademo.data.api.ApiHelper
import com.example.telstrademo.data.api.RetrofitBuilder
import com.example.telstrademo.ui.base.ViewModelFactory
import com.example.telstrademo.ui.main.viewmodel.MainViewModel
import com.example.telstrademo.utility.ResponseStatus
import kotlinx.android.synthetic.main.activity_main.*

class FactListActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewModel()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.getUsers().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    ResponseStatus.SUCCESS -> {
                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        resource.data?.let {  users ->
                            Log.d("RESPONSE",users.toString())
                        }
                    }
                    ResponseStatus.ERROR -> {
                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    ResponseStatus.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(MainViewModel::class.java)    }
}
