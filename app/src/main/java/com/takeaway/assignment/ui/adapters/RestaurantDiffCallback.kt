package com.takeaway.assignment.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import com.takeaway.assignment.data.Restaurant

class RestaurantDiffCallback : DiffUtil.ItemCallback<Restaurant>() {
    override fun areItemsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
        return oldItem == newItem
    }
}
