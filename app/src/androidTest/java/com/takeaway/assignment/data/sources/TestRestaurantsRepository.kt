package com.takeaway.assignment.data.sources

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.takeaway.assignment.R
import com.takeaway.assignment.data.Restaurant
import com.takeaway.assignment.data.Result
import com.takeaway.assignment.util.READER_CHARSET
import com.takeaway.assignment.util.fromJson
import javax.inject.Inject

class TestRestaurantsRepository @Inject constructor(context: Context) : RestaurantsRepository {

    private var _shouldReturnError = false
    private val restaurantsList: List<Restaurant>? =
        Gson().fromJson<List<Restaurant>>(context, R.raw.sample_restaurant_list, READER_CHARSET)

    private val _observableRestaurantList = MutableLiveData<Result<List<Restaurant>>>()

    override fun observeRestaurantList(): LiveData<Result<List<Restaurant>>> =
        _observableRestaurantList

    fun setReturnError(value: Boolean) {
        _shouldReturnError = value
    }

    override suspend fun refreshRestaurantList(): Result<Nothing?> {
        return if (_shouldReturnError || restaurantsList == null) {
            Result.Error(java.lang.IllegalArgumentException())
        } else {
            _observableRestaurantList.value = Result.Success(restaurantsList)
            Result.Success(null)
        }
    }

    override suspend fun updateFavourite(restaurant: Restaurant): Result<Nothing?> {
        restaurantsList?.forEach { restaurantEntity ->
            if (restaurant.name == restaurantEntity.name) {
                restaurantEntity.isFavourite = restaurant.isFavourite
            }
        }
        return refreshRestaurantList()
    }
}