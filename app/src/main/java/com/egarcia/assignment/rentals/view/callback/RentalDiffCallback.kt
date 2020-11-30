package com.egarcia.assignment.rentals.view.callback

import androidx.recyclerview.widget.DiffUtil
import com.egarcia.assignment.rentals.service.model.NetworkRental

class RentalDiffCallback : DiffUtil.ItemCallback<NetworkRental>() {
    override fun areItemsTheSame(oldItem: NetworkRental, newItem: NetworkRental): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: NetworkRental, newItem: NetworkRental): Boolean {
        return oldItem.attributes.name == newItem.attributes.name
                && oldItem.attributes.primaryImageUrl == newItem.attributes.primaryImageUrl
    }
}
