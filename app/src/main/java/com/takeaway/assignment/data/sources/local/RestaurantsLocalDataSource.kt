package com.takeaway.assignment.data.sources.local

import android.database.sqlite.SQLiteException
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.takeaway.assignment.data.Restaurant
import com.takeaway.assignment.data.Result
import com.takeaway.assignment.data.sources.RestaurantsDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RestaurantsLocalDataSource internal constructor(
    private val restaurantsDao: RestaurantsDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : RestaurantsDataSource {

    private val tag = this::class.java.canonicalName

    override val observableRestaurantList: LiveData<Result<List<Restaurant>>>
        get() = restaurantsDao.observeRestaurantList().map {
            Result.Success(it)
        }

    override suspend fun getRestaurantList(): Result<List<Restaurant>> {
        return withContext(ioDispatcher) {
            return@withContext try {
                Result.Success(restaurantsDao.getRestaurantList())
            } catch (exception: SQLiteException) {
                Result.Error(exception)
            }
        }
    }

    override suspend fun refreshRestaurantList() {
        // no-op
    }

    override suspend fun updateFavourite(
        restaurantId: String,
        isFavourite: Boolean
    ): Result<Nothing?> {
        return withContext(ioDispatcher) {
            return@withContext try {
                restaurantsDao.updateFavourite(restaurantId, isFavourite)
                Result.Success(null)
            } catch (exception: SQLiteException) {
                Result.Error(exception)
            }
        }
    }

    override suspend fun insertOrUpdateRestaurant(restaurant: Restaurant) {
        withContext(ioDispatcher) {
            try {
                restaurantsDao.insertOrUpdate(restaurant)
            } catch (exception: java.lang.Exception) {
                Log.e(tag, exception.message, exception)
            }
        }
    }
}
