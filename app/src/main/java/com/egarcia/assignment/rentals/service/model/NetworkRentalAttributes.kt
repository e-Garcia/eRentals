package com.egarcia.assignment.rentals.service.model

import com.google.gson.annotations.SerializedName

data class NetworkRentalAttributes(
        @SerializedName("name")
        val name: String,
        @SerializedName("primary_image_url")
        val primaryImageUrl: String
)
