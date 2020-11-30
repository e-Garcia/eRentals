package com.egarcia.assignment.rentals.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.egarcia.assignment.rentals.service.model.NetworkRental
import com.egarcia.assignment.rentals.service.repository.RentalDataSource
import com.egarcia.assignment.rentals.service.repository.RentalDataSourceFactory


const val PAGE_SIZE = 5

/**
 * A collection of Listings
 */
class ListingViewModel : BaseViewModel() {

    private val mListings: LiveData<PagedList<NetworkRental>>
    private var mLoadingStatus: LiveData<String>
    private val mFactory: RentalDataSourceFactory

    init {
        val config = PagedList.Config.Builder()
                .setInitialLoadSizeHint(PAGE_SIZE)
                .setPageSize(PAGE_SIZE)
                .build()

        mFactory = RentalDataSourceFactory()
        mLoadingStatus = Transformations.switchMap(mFactory.mDataSource, ::loadNetworkState)
        mListings = listBuilder(config, mFactory).build()
    }

    fun refresh() {
        mFactory.mDataSource.value?.invalidate()
    }

    fun paginatedListings(): LiveData<PagedList<NetworkRental>> {
        return mListings
    }

    fun loadingStatus(): LiveData<String> {
        return mLoadingStatus
    }

    private fun listBuilder(config: PagedList.Config, dataSourceFactory: DataSource.Factory<Int, NetworkRental>)
            : LivePagedListBuilder<Int, NetworkRental> {
        return LivePagedListBuilder<Int, NetworkRental>(dataSourceFactory, config).setInitialLoadKey(0)
    }

    private fun loadNetworkState(dataSource: RentalDataSource): MutableLiveData<String> {
        return dataSource.progressStatus()
    }

}