package com.example.telstrademo.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.telstrademo.data.model.FactDetailResponse
import com.example.telstrademo.data.repository.FactDetailRepository
import com.example.telstrademo.utility.Resource
import kotlinx.coroutines.Dispatchers


class FactDetailViewModel(private val mainRepository: FactDetailRepository?) : ViewModel() {

    /* The result of the function will be emitted as Live Data, which can be observed in the view*/
    var factsData = liveData(Dispatchers.IO) {

        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository?.getFactDetails()?.let {
                getFilteredList(
                    it
                )
            }))
        } catch (exception: Exception) {
            emit(
                Resource.error(
                    data = null,
                    message = exception.message
                        ?: "Error occurred while retrieving fact details!"
                )
            )
        }
    }

    /* Removing list item which has all fields null*/
    private fun getFilteredList(factDetails: FactDetailResponse): FactDetailResponse {
        val itr = factDetails.factDetailListItem.iterator()
        while (itr.hasNext()) {
            val current = itr.next()
            if (current.title == null && current.description == null && current.imageHref == null) {
                itr.remove()
            }
        }
        return factDetails
    }
}