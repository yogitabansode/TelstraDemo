package com.example.telstrademo.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.telstrademo.data.api.ApiHelper
import com.example.telstrademo.data.repository.MainRepository
import com.example.telstrademo.ui.main.viewmodel.MainViewModel

class ViewModelFactory (private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(MainRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}
