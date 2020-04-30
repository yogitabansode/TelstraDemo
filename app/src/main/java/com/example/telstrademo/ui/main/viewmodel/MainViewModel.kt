package com.example.telstrademo.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.telstrademo.data.model.MainResponse
import com.example.telstrademo.data.repository.MainRepository
import com.example.telstrademo.utility.Resource
import kotlinx.coroutines.Dispatchers


class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    /* The result of the function will be emitted as Live Data, which can be observed in the view*/
    fun getFactDetails() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = getFilteredList(mainRepository.getFactDetails())))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    /* Removing list item which has all fields null*/
    private fun getFilteredList(factDetails: MainResponse): MainResponse {
        val itr = factDetails.rows.iterator()
        while (itr.hasNext()) {
            val current = itr.next()
            if (current.title == null && current.description == null && current.imageHref == null) {
                itr.remove()
            }
        }
        return factDetails
    }
}