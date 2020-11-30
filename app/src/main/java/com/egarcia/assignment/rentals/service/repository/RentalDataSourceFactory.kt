package com.egarcia.assignment.rentals.service.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.egarcia.assignment.rentals.service.model.NetworkRental

class RentalDataSourceFactory : DataSource.Factory<Int, NetworkRental>() {

    val mDataSource = MutableLiveData<RentalDataSource>()

    override fun create(): DataSource<Int, NetworkRental> {
        val dataSource = RentalDataSource()
        mDataSource.postValue(dataSource)
        return dataSource
    }
}
