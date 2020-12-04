package com.egarcia.assignment.rentals.service.repository

import com.egarcia.assignment.rentals.service.model.RentalsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *  The interface for the rental web service
 */
interface RentalApi {

    /**
     * Get the list of rental listings from the API
     */
    @GET("/rentals")
    fun getListings(@Query("filter[keywords]") keywords: String,
                    @Query("page[offset]") start: Int,
                    @Query("page[limit]") count: Int): Call<RentalsResponse>
}