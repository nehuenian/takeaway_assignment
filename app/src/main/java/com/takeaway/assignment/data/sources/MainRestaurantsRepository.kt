package com.takeaway.assignment.data.sources

import android.util.Log
import androidx.lifecycle.LiveData
import com.takeaway.assignment.data.Restaurant
import com.takeaway.assignment.data.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainRestaurantsRepository(
    private val restaurantsRemoteDataSource: RestaurantsDataSource,
    private val restaurantsLocalDataSource: RestaurantsDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : RestaurantsRepository {

    private val tag = this.javaClass.canonicalName

    override fun observeRestaurantList(): LiveData<Result<List<Restaurant>>> {
        return restaurantsLocalDataSource.observableRestaurantList
    }

    override suspend fun refreshRestaurantList(): Result<Nothing?> {
        return updateRestaurantListFromRemoteDataSource()
    }

    override suspend fun updateFavourite(restaurant: Restaurant): Result<Nothing?> {
        return withContext(ioDispatcher) {
            restaurantsLocalDataSource.updateFavourite(
                restaurant.name,
                restaurant.isFavourite
            )
        }
    }

    private suspend fun updateRestaurantListFromRemoteDataSource(): Result<Nothing?> {
        return restaurantsRemoteDataSource.getRestaurantList().let { result ->
            when (result) {
                is Result.Success -> {
                    repeat(result.data.size) { restaurantsLocalDataSource::insertOrUpdateRestaurant }
                    Result.Success(null)
                }
                is Result.Error -> {
                    Log.w(tag, result.exception.message, result.exception)
                    result
                }
                Result.Loading -> {
                    val illegalArgumentException =
                        IllegalArgumentException(
                            "Loading is not a valid result for getRestaurantList"
                        ).also {
                            Log.w(tag, it.message, it)
                        }
                    Result.Error(illegalArgumentException)
                }
            }
        }
    }
}
