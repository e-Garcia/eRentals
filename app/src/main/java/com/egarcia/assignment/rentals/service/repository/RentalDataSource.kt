package com.egarcia.assignment.rentals.service.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.egarcia.assignment.rentals.service.model.NetworkRental
import com.egarcia.assignment.rentals.service.model.RentalsResponse
import com.egarcia.assignment.rentals.service.model.isValidResponse
import com.egarcia.assignment.utils.ProgressStatus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.InvalidObjectException

/**
 * Source of listings for the pagination library
 */
class RentalDataSource(
        private val currentQuery: String,
        private val rentalRepository: RentalRepository
) : PageKeyedDataSource<Int, NetworkRental>() {

    private val progressStatus: MutableLiveData<ProgressStatus> = MutableLiveData()
    private val invalidResponseError = ProgressStatus.Error(InvalidObjectException("Invalid response"))

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, NetworkRental>) {
        // Not required
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, NetworkRental>) {
        val initialPage = 0
        progressStatus.postValue(ProgressStatus.Loading)
        rentalRepository
                .getListings(currentQuery, initialPage, params.requestedLoadSize, object : Callback<RentalsResponse> {
                    override fun onFailure(call: Call<RentalsResponse>?, throwable: Throwable?) {
                        progressStatus.postValue(ProgressStatus.Error(throwable))
                    }

                    override fun onResponse(call: Call<RentalsResponse>?, response: Response<RentalsResponse>?) {
                        response?.body()?.let { rentalsResponse ->
                            if (rentalsResponse.isValidResponse()) {
                                callback.onResult(rentalsResponse.data, initialPage, initialPage + rentalsResponse.data.size)
                                progressStatus.postValue(ProgressStatus.Success)
                            } else {
                                progressStatus.postValue(invalidResponseError)
                            }
                        } ?: progressStatus.postValue(invalidResponseError)
                    }
                })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, NetworkRental>) {
        progressStatus.postValue(ProgressStatus.Loading)
        rentalRepository
                .getListings(currentQuery, params.key, params.requestedLoadSize, object : Callback<RentalsResponse> {
                    override fun onFailure(call: Call<RentalsResponse>?, throwable: Throwable?) {
                        progressStatus.postValue(ProgressStatus.Error(throwable))
                    }

                    override fun onResponse(call: Call<RentalsResponse>?, response: Response<RentalsResponse>?) {
                        response?.body()?.let { rentalsResponse ->
                            if (rentalsResponse.isValidResponse()) {
                                callback.onResult(rentalsResponse.data, params.key + rentalsResponse.data.size)
                                progressStatus.postValue(ProgressStatus.Success)
                            } else {
                                progressStatus.postValue(invalidResponseError)
                            }
                        } ?: progressStatus.postValue(invalidResponseError)
                    }
                })
    }

    fun progressStatus(): MutableLiveData<ProgressStatus> {
        return progressStatus
    }

}