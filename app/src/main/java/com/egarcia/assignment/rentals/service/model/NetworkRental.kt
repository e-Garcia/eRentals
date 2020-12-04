package com.egarcia.assignment.rentals.service.model

import com.google.gson.annotations.SerializedName

/**
 * Class which provides a model for a rental
 * @constructor Sets all properties for this rental
 * @property id a unique String identifier for this rental.
 * ...
 */
data class NetworkRental(
        @SerializedName("id")
        val id: String,
        @SerializedName("attributes")
        val attributes: NetworkRentalAttributes
)

fun NetworkRental.isValidResponse(): Boolean {
    return id.isNotBlank() && attributes.isValidResponse()
}