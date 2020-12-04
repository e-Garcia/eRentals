package com.egarcia.assignment.rentals.service.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.egarcia.assignment.rentals.service.model.NetworkRental

class RentalDataSourceFactory(private val query: String = "", private val rentalRepository: RentalRepository)
    : DataSource.Factory<Int, NetworkRental>() {

    val dataSource = MutableLiveData<RentalDataSource>()

    override fun create(): DataSource<Int, NetworkRental> {
        val dataSource = RentalDataSource(query, rentalRepository)
        this.dataSource.postValue(dataSource)
        return dataSource
    }
}
