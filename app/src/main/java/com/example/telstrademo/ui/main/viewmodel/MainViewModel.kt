package com.example.telstrademo.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.telstrademo.data.repository.MainRepository
import com.example.telstrademo.utility.Resource
import kotlinx.coroutines.Dispatchers

class MainViewModel (private val mainRepository: MainRepository) : ViewModel() {

    fun getUsers() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.getFactDetails()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}