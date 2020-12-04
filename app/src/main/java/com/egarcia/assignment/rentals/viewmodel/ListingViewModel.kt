package com.egarcia.assignment.rentals.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.egarcia.assignment.rentals.service.model.NetworkRental
import com.egarcia.assignment.rentals.service.repository.RentalDataSource
import com.egarcia.assignment.rentals.service.repository.RentalDataSourceFactory
import com.egarcia.assignment.utils.PAGE_SIZE
import com.egarcia.assignment.utils.ProgressStatus

/**
 * A collection of Listings
 */
class ListingViewModel : BaseViewModel() {

    private var listings: LiveData<PagedList<NetworkRental>>
    private var loadingStatus: LiveData<ProgressStatus>
    private val queryKeywords = MutableLiveData("")
    private var factory: RentalDataSourceFactory = RentalDataSourceFactory()
    private val config = PagedList.Config.Builder()
            .setInitialLoadSizeHint(PAGE_SIZE)
            .setPageSize(PAGE_SIZE)
            .build()

    init {
        loadingStatus = Transformations.switchMap(factory.dataSource, ::loadNetworkState)
        listings = Transformations.switchMap(queryKeywords) {
            if (it.isEmpty()) {
                LivePagedListBuilder(factory, config).setInitialLoadKey(0).build()
            } else {
                LivePagedListBuilder(RentalDataSourceFactory(it), config).build()
            }
        }
    }

    fun refresh() {
        factory.dataSource.value?.invalidate()
    }

    fun search(keywords: String) {
        queryKeywords.value = keywords
    }

    fun paginatedListings(): LiveData<PagedList<NetworkRental>> {
        return listings
    }

    fun loadingStatus(): LiveData<ProgressStatus> {
        return loadingStatus
    }

    private fun loadNetworkState(dataSource: RentalDataSource): MutableLiveData<ProgressStatus> {
        return dataSource.progressStatus()
    }

}