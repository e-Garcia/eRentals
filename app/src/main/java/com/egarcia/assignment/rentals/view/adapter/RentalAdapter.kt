package com.egarcia.assignment.rentals.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.egarcia.assignment.databinding.ListingListItemBinding
import com.egarcia.assignment.rentals.service.model.NetworkRental
import com.egarcia.assignment.rentals.view.callback.RentalDiffCallback
import com.squareup.picasso.Picasso

/**
 * Used to display Listing objects
 */
class RentalAdapter :
    PagedListAdapter<NetworkRental, RentalAdapter.ViewHolder>(RentalDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListingListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { item -> holder.bind(item) }
    }

    inner class ViewHolder(private val binding: ListingListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(networkRental: NetworkRental) {
            binding.name.text = networkRental.attributes.name
            networkRental.attributes.primaryImageUrl?.let {
                Picasso.with(itemView.context).load(it)
                    .fit().centerCrop().into(binding.primaryImage)
            }
        }
    }
}