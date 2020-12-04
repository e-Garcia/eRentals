package com.egarcia.assignment.rentals.service.model

import com.google.gson.annotations.SerializedName

data class NetworkRentalAttributes(
        @SerializedName("name")
        val name: String, // Not nullable
        @SerializedName("primary_image_url")
        val primaryImageUrl: String? // nullable
)

fun NetworkRentalAttributes.isValidResponse(): Boolean {
    return name.isNotBlank() && primaryImageUrl != null && primaryImageUrl.isNotBlank()
}
