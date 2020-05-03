package com.example.telstrademo.ui.main.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.telstrademo.data.model.FactDetailResponse
import com.example.telstrademo.data.repository.FactDetailRepository
import com.example.telstrademo.utility.NetworkConnection
import com.example.telstrademo.utility.Resource
import kotlinx.coroutines.Dispatchers


class FactDetailViewModel(private val mainRepository: FactDetailRepository) : ViewModel() {

    /* The result of the function will be emitted as Live Data, which can be observed in the view*/
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    var factsData = liveData(Dispatchers.IO) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (NetworkConnection.isNetworkConnected()) {
                emit(Resource.loading(data = null))
                try {
                    emit(Resource.success(data = mainRepository.getFactDetails()?.let {
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
            } else {
                emit(
                    Resource.error(
                        data = null,
                        message = "No internet connectivity available.Please try after sometime."
                    )
                )
            }
        } else {
            if (NetworkConnection.isNetworkConnectedKitkat()) {
                emit(Resource.loading(data = null))
                try {
                    emit(Resource.success(data = mainRepository.getFactDetails()?.let {
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
            } else {
                emit(
                    Resource.error(
                        data = null,
                        message = "No internet connectivity available.Please try after sometime."
                    )
                )
            }
        }
    }

    /* Removing list item which has all fields null*/
    private fun getFilteredList(factDetails: FactDetailResponse): FactDetailResponse {
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