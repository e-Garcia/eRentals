package com.egarcia.assignment.rentals.view.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.egarcia.assignment.R
import com.egarcia.assignment.databinding.ActivityRentalListBinding
import com.egarcia.assignment.rentals.view.adapter.RentalAdapter
import com.egarcia.assignment.rentals.viewmodel.ListingViewModel
import com.egarcia.assignment.utils.ProgressStatus
import com.egarcia.assignment.view.BaseActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.rental_list.*

/**
 * Displays a list of Listings
 */
class RentalListActivity : BaseActivity(), androidx.appcompat.widget.SearchView.OnQueryTextListener {

    //region Member variables
    private lateinit var viewModel: ListingViewModel
    private lateinit var adapter: RentalAdapter
    private lateinit var binding: ActivityRentalListBinding
    private lateinit var snackbar: Snackbar
    //endregion

    //region Lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRentalListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setSupportActionBar(binding.toolbar)
        binding.toolbar.title = title

        viewModel = ViewModelProviders.of(this).get(ListingViewModel::class.java)
        setupRecyclerView(binding.listLayout.rentalList)
        snackbar = Snackbar.make(binding.coordinator,
                R.string.error_message_generic, Snackbar.LENGTH_INDEFINITE)
        snackbar.setAction(R.string.retry) { viewModel.refresh() }
        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntent(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)

        // Associate searchable configuration with the SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.search).actionView as androidx.appcompat.widget.SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            setOnQueryTextListener(this@RentalListActivity)
        }
        return true
    }
    //endregion

    //region Private methods
    private fun setupRecyclerView(recyclerView: RecyclerView) {

        adapter = RentalAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        initObservers()
    }

    private fun initObservers() {
        val listingsLiveData = viewModel.paginatedListings()
        val loadingLiveData = viewModel.loadingStatus()

        listingsLiveData.observe(this, { pagedList ->
            progressBar.visibility = View.GONE
            adapter.submitList(pagedList)
        })

        loadingLiveData.observe(this, { loadingStatus ->

            when (loadingStatus) {
                is ProgressStatus.Loading -> {
                    progressBar.visibility = View.VISIBLE
                }
                is ProgressStatus.Success -> {
                    snackbar.dismiss()
                    progressBar.visibility = View.GONE
                }
                is ProgressStatus.Error -> {
                    snackbar.show()
                    progressBar.visibility = View.GONE
                }
            }
        })
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                viewModel.search(query)
            }
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        hideKeyboard()
        return true // Search already handled in onQueryTextChange
    }

    override fun onQueryTextChange(query: String?): Boolean {
        query?.let { viewModel.search(it) }
        return true
    }
    //endregion

}
