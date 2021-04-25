package com.takeaway.assignment.data.sources

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.takeaway.assignment.data.Restaurant
import com.takeaway.assignment.data.Result
import kotlinx.coroutines.runBlocking

class FakeRestaurantsRepository() : RestaurantsRepository {

    var shouldReturnError = false
    var restaurantsList: ArrayList<Restaurant> = ArrayList()

    private val _observableRestaurantList = MutableLiveData<Result<List<Restaurant>>>()

    override fun observeRestaurantList(): LiveData<Result<List<Restaurant>>> {
        runBlocking { refreshRestaurantList() }
        return _observableRestaurantList
    }


    override suspend fun refreshRestaurantList(): Result<Nothing?> {
        return if (shouldReturnError) {
            _observableRestaurantList.value = Result.Error(java.lang.IllegalArgumentException())
            Result.Error(java.lang.IllegalArgumentException())
        } else {
            _observableRestaurantList.value = Result.Success(restaurantsList)
            Result.Success(null)
        }
    }

    override suspend fun updateFavourite(restaurant: Restaurant): Result<Nothing?> {
        if (shouldReturnError.not()) {
            restaurantsList.forEach { restaurantEntity ->
                if (restaurant.name == restaurantEntity.name) {
                    restaurantEntity.isFavourite = restaurant.isFavourite
                }
            }
        }
        return refreshRestaurantList()
    }
}