package com.egarcia.assignment

import com.egarcia.assignment.rentals.service.model.NetworkRentalAttributes
import com.egarcia.assignment.rentals.service.model.isValidResponse
import org.junit.Assert.assertEquals
import org.junit.Test

class NetworkRentalAttributesTest {
    @Test
    fun `Given valid rental attributes, when isValidResponse is called, then it returns true`() {
        // Given
        val name = "Forest River Forester"
        val primaryImageUrl = "https://res.cloudinary.com/outdoorsy/image/upload/v1595468574/p/rentals/164539/images/rq7dpel775iry3bxij4p.jpg"
        val networkRentalAttributes = NetworkRentalAttributes(name, primaryImageUrl)
        val expectedResult = true
        // When
        val result = networkRentalAttributes.isValidResponse()
        // Then
        assertEquals(expectedResult, result)
    }

    @Test
    fun `Given an empty name and a valid primary image url, when isValidResponse is called, then it returns false`() {
        // Given
        val name = ""
        val primaryImageUrl = "https://res.cloudinary.com/outdoorsy/image/upload/v1595468574/p/rentals/164539/images/rq7dpel775iry3bxij4p.jpg"
        val networkRentalAttributes = NetworkRentalAttributes(name, primaryImageUrl)
        val expectedResult = false
        // When
        val result = networkRentalAttributes.isValidResponse()
        // Then
        assertEquals(expectedResult, result)
    }

    @Test
    fun `Given a valid name and a null primary image url, when isValidResponse is called, then it returns false`() {
        // Given
        val name = "Forest River Forester"
        val primaryImageUrl = null
        val networkRentalAttributes = NetworkRentalAttributes(name, primaryImageUrl)
        val expectedResult = false
        // When
        val result = networkRentalAttributes.isValidResponse()
        // Then
        assertEquals(expectedResult, result)
    }

    @Test
    fun `Given an empty name and a null primary image url, when isValidResponse is called, then it returns false`() {
        // Given
        val name = ""
        val primaryImageUrl = null
        val networkRentalAttributes = NetworkRentalAttributes(name, primaryImageUrl)
        val expectedResult = false
        // When
        val result = networkRentalAttributes.isValidResponse()
        // Then
        assertEquals(expectedResult, result)
    }
}
