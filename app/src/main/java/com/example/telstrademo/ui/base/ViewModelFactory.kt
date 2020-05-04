package com.example.telstrademo.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.telstrademo.data.api.ApiHelper
import com.example.telstrademo.data.repository.FactDetailRepository
import com.example.telstrademo.ui.main.viewmodel.FactDetailViewModel

/*This code is used to pass value to view model like we pass repository object*/
class ViewModelFactory(private val apiHelper: ApiHelper?) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T?>): T {
        if (modelClass.isAssignableFrom(FactDetailViewModel::class.java)) {
            apiHelper?.let{
                return FactDetailViewModel(FactDetailRepository(it)) as T
            }
        }
        throw IllegalArgumentException("Unknown class name")
    }

}
