package com.egarcia.assignment.rentals.service.repository

import com.egarcia.assignment.rentals.service.model.RentalsResponse
import retrofit2.Callback
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Contains the implementation details for retrieving Rentals from the network.
 */
@Singleton
class RentalRepository @Inject constructor(private val rentalApi: RentalApi) {

    fun getListings(filter: String = "", start: Int, count: Int, callback: Callback<RentalsResponse>) {
        rentalApi.getListings(filter, start, count).enqueue(callback)
    }

}