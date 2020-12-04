package com.egarcia.assignment.rentals.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.egarcia.assignment.R
import com.egarcia.assignment.rentals.service.model.NetworkRental
import com.egarcia.assignment.rentals.view.callback.RentalDiffCallback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.listing_list_item.view.*

/**
 * Used to display Listing objects
 */
class RentalAdapter : PagedListAdapter<NetworkRental, RentalAdapter.ViewHolder>(RentalDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.listing_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { item -> holder.bind(item) }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(networkRental: NetworkRental) {
            itemView.name.text = networkRental.attributes.name
            Picasso.with(itemView.context).load(networkRental.attributes.primaryImageUrl)
                    .fit().centerCrop().into(itemView.primary_image)
        }
    }
}