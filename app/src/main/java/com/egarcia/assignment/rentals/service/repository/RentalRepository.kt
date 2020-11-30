package com.egarcia.assignment.rentals.service.repository

import androidx.lifecycle.MutableLiveData
import com.egarcia.assignment.rentals.service.model.NetworkRental
import com.egarcia.assignment.rentals.service.model.RentalsResponse
import com.egarcia.assignment.utils.BASE_URL
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Contains the implementation details for retrieving Rentals from the network.
 */
class RentalRepository {

    private val rentalApi: RentalApi
    private val mutableLiveNetworkRentalList: MutableLiveData<Result<List<NetworkRental>>>

    companion object {
        private lateinit var rentalRepository: RentalRepository

        fun getInstance(): RentalRepository {
            if (!::rentalRepository.isInitialized) {
                rentalRepository = RentalRepository()
            }
            return rentalRepository
        }
    }

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        rentalApi = retrofit.create(RentalApi::class.java)
        mutableLiveNetworkRentalList = MutableLiveData()
    }

    fun getListings(start: Int, count: Int, callback: Callback<RentalsResponse>) {
        rentalApi.getListings(start, count).enqueue(callback)
    }

}