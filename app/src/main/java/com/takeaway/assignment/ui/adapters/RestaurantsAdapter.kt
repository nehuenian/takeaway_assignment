package com.takeaway.assignment.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.takeaway.assignment.R
import com.takeaway.assignment.data.Restaurant
import com.takeaway.assignment.data.Result
import com.takeaway.assignment.data.SortCondition
import com.takeaway.assignment.data.SortingValues
import com.takeaway.assignment.databinding.RestaurantItemBinding
import com.takeaway.assignment.viewmodels.RestaurantsViewModel

class RestaurantsAdapter(private val viewModel: RestaurantsViewModel) :
    ListAdapter<Restaurant, RestaurantsAdapter.RestaurantViewHolder>(RestaurantDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        return RestaurantViewHolder.from(parent, viewModel)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class RestaurantViewHolder private constructor(
        private val viewBinding: RestaurantItemBinding,
        private val viewModel: RestaurantsViewModel,
    ) : RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(restaurant: Restaurant) {
            with(viewBinding) {
                restaurantName.text = restaurant.name
                restaurantStatus.text = restaurant.status.toString()
                when (restaurant.status) {
                    Restaurant.Status.OPEN -> {
                        restaurantStatus.background = ContextCompat.getDrawable(
                            itemView.context,
                            R.drawable.open_tag_background
                        )
                    }
                    Restaurant.Status.CLOSED -> {
                        restaurantStatus.background = ContextCompat.getDrawable(
                            itemView.context,
                            R.drawable.closed_tag_background
                        )
                    }
                    Restaurant.Status.ORDER_AHEAD -> {
                        restaurantStatus.background = ContextCompat.getDrawable(
                            itemView.context,
                            R.drawable.order_ahead_tag_background
                        )
                    }
                }
                favouriteCheck.isChecked = restaurant.isFavourite
                favouriteCheck.setOnCheckedChangeListener { _, isChecked ->
                    (viewModel.restaurantListState.value as? Result.Success)
                        ?.data
                        ?.get(adapterPosition)
                        ?.let { restaurantChecked ->
                            viewModel.updateFavouriteStatus(restaurantChecked.copy(isFavourite = isChecked))
                        }
                }

                viewModel.searchFilterPlusSortCondition.value?.sortCondition?.let {
                    sortingValue.setSortingValue(it, restaurant.sortingValues)
                }
            }
        }

        companion object {
            fun from(parent: ViewGroup, viewModel: RestaurantsViewModel): RestaurantViewHolder {
                val viewBinding = RestaurantItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return RestaurantViewHolder(viewBinding, viewModel)
            }
        }
    }
}

private fun TextView.setSortingValue(sortCondition: SortCondition, sortingValues: SortingValues) {
    text = when (sortCondition) {
        SortCondition.BEST_MATCH -> {
            context.getString(
                R.string.sorting_value,
                sortCondition.toString(),
                sortingValues.bestMatch.toString()
            )
        }
        SortCondition.NEWEST -> {
            context.getString(
                R.string.sorting_value,
                sortCondition.toString(),
                sortingValues.newest.toString()
            )
        }
        SortCondition.RATING_AVERAGE -> {
            context.getString(
                R.string.sorting_value,
                sortCondition.toString(),
                sortingValues.ratingAverage.toString()
            )
        }
        SortCondition.DISTANCE -> {
            context.getString(
                R.string.sorting_value_distance,
                sortCondition.toString(),
                sortingValues.distance.toString()
            )
        }
        SortCondition.POPULARITY -> {
            context.getString(
                R.string.sorting_value,
                sortCondition.toString(),
                sortingValues.popularity.toString()
            )
        }
        SortCondition.AVERAGE_PRODUCT_PRICE -> {
            context.getString(
                R.string.sorting_value_money,
                sortCondition.toString(),
                sortingValues.averageProductPrice.toString()
            )
        }
        SortCondition.DELIVERY_COSTS -> {
            context.getString(
                R.string.sorting_value_money,
                sortCondition.toString(),
                sortingValues.deliveryCosts.toString()
            )
        }
        SortCondition.MIN_COST -> {
            context.getString(
                R.string.sorting_value_money,
                sortCondition.toString(),
                sortingValues.minCost.toString()
            )
        }
    }
}
