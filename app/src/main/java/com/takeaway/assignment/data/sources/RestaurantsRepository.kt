package com.takeaway.assignment.data.sources

import androidx.lifecycle.LiveData
import com.takeaway.assignment.data.Restaurant
import com.takeaway.assignment.data.Result

interface RestaurantsRepository {
    fun observeRestaurants(): LiveData<Result<List<Restaurant>>>
}