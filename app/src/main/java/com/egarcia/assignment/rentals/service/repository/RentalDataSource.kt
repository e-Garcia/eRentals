package com.egarcia.assignment.rentals.service.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.egarcia.assignment.rentals.service.model.NetworkRental
import com.egarcia.assignment.rentals.service.model.RentalsResponse
import com.egarcia.assignment.utils.ERROR
import com.egarcia.assignment.utils.SUCCESS
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Source of listings for the pagination library
 */
class RentalDataSource : PageKeyedDataSource<Int, NetworkRental>() {

    private val mProgressStatus: MutableLiveData<String> = MutableLiveData()

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, NetworkRental>) {
        // Not required
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, NetworkRental>) {
        val initialPage = 0
        RentalRepository.getInstance()
                .getListings(initialPage, params.requestedLoadSize, object : Callback<RentalsResponse> {
                    override fun onFailure(call: Call<RentalsResponse>?, t: Throwable?) {
                        mProgressStatus.postValue(ERROR)
                    }

                    override fun onResponse(call: Call<RentalsResponse>?, response: Response<RentalsResponse>?) {
                        response?.body()?.let { rentalsResponse ->
                            callback.onResult(rentalsResponse.data, initialPage, initialPage + rentalsResponse.data.size)
                            mProgressStatus.postValue(SUCCESS)
                        } ?: mProgressStatus.postValue(ERROR)
                    }
                })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, NetworkRental>) {
        RentalRepository.getInstance()
                .getListings(params.key, params.requestedLoadSize, object : Callback<RentalsResponse> {
                    override fun onFailure(call: Call<RentalsResponse>?, t: Throwable?) {
                        mProgressStatus.postValue(ERROR)
                    }

                    override fun onResponse(call: Call<RentalsResponse>?, response: Response<RentalsResponse>?) {
                        response?.body()?.let { rentalsResponse ->
                            callback.onResult(rentalsResponse.data, params.key + rentalsResponse.data.size)
                            mProgressStatus.postValue(SUCCESS)
                        } ?: mProgressStatus.postValue(ERROR)
                    }
                })
    }

    fun progressStatus(): MutableLiveData<String> {
        return mProgressStatus
    }

}