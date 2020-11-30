package com.egarcia.assignment.rentals.view.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.egarcia.assignment.R
import com.egarcia.assignment.databinding.ActivityRentalListBinding
import com.egarcia.assignment.rentals.view.adapter.RentalAdapter
import com.egarcia.assignment.rentals.viewmodel.ListingViewModel
import com.egarcia.assignment.utils.ERROR
import com.google.android.material.snackbar.Snackbar

/**
 * Displays a list of Listings
 */
class RentalListActivity : AppCompatActivity() {

    //region Member variables
    private lateinit var mViewModel: ListingViewModel
    private lateinit var mAdapter: RentalAdapter
    private lateinit var binding: ActivityRentalListBinding
    private lateinit var mSnackBar: Snackbar
    //endregion

    //region Lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRentalListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setSupportActionBar(binding.toolbar)
        binding.toolbar.title = title

        mViewModel = ViewModelProviders.of(this).get(ListingViewModel::class.java)
        setupRecyclerView(binding.listLayout.rentalList)
    }
    //endregion

    //region Private methods
    private fun setupRecyclerView(recyclerView: RecyclerView) {

        mAdapter = RentalAdapter()
        recyclerView.adapter = mAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        loadData()
    }

    private fun loadData() {
        val listingsLiveData = mViewModel.paginatedListings()
        val loadingLiveData = mViewModel.loadingStatus()

        listingsLiveData.observe(this, { pagedList ->
            mAdapter.submitList(pagedList)
        })

        loadingLiveData.observe(this, { loadingStatus ->
            mSnackBar = Snackbar.make(binding.coordinator,
                    R.string.error_message_generic, Snackbar.LENGTH_INDEFINITE)

            mSnackBar.setAction(R.string.retry) { mViewModel.refresh() }

            if (loadingStatus == ERROR) {
                mSnackBar.show()

            } else {
                mSnackBar.dismiss()
            }
        })
    }
    //endregion

}
