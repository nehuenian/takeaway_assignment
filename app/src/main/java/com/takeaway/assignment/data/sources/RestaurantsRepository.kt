package com.takeaway.assignment.data.sources

import androidx.lifecycle.LiveData
import com.takeaway.assignment.data.Restaurant
import com.takeaway.assignment.data.Result

interface RestaurantsRepository {

    fun observeRestaurantList(): LiveData<Result<List<Restaurant>>>

    suspend fun refreshRestaurantList(): Result<Nothing?>

    suspend fun updateFavourite(restaurant: Restaurant): Result<Nothing?>

}
