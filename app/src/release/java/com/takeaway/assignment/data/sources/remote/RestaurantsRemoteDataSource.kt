package com.takeaway.assignment.data.sources.remote

import android.content.Context
import androidx.lifecycle.LiveData
import com.takeaway.assignment.data.Restaurant
import com.takeaway.assignment.data.Result
import com.takeaway.assignment.data.sources.RestaurantsDataSource
import javax.inject.Inject

/**
 * Real implementation of the remote data source that should call BE to retrieve the restaurants
 * Not implemented yet, waiting for APIs from BE team
 */
class RestaurantsRemoteDataSource @Inject constructor(
    @Suppress("UNUSED_PARAMETER") context: Context
) : RestaurantsDataSource {

    override val observableRestaurantList: LiveData<Result<List<Restaurant>>>
        get() = TODO("Missing APIs from BE team")

    override suspend fun getRestaurantList(): Result<List<Restaurant>> {
        TODO("Missing APIs from BE team")
    }

    override suspend fun refreshRestaurantList() {
        TODO("Missing APIs from BE team")
    }
}