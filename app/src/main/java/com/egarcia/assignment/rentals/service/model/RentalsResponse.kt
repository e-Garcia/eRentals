package com.egarcia.assignment.rentals.service.model

import com.google.gson.annotations.SerializedName

/**
 * Class which provides a model for a listing
 * @constructor Sets all properties for this listing
 * @property data a list of rentals
 * ...
 */
data class RentalsResponse(
        @SerializedName("data")
        val data: List<NetworkRental>
)