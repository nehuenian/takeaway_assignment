package com.takeaway.assignment.data.sources

import androidx.lifecycle.LiveData
import com.takeaway.assignment.data.Restaurant
import com.takeaway.assignment.data.Result

interface RestaurantsRepository {

    /**
     * Gets the live data in which will be published all the changes on the restaurant list.
     * This is done through a [Result] that wraps the different
     * possible states of the response.
     *
     * @return the observable that will receive the restaurant list updates
     */
    fun observeRestaurantList(): LiveData<Result<List<Restaurant>>>

    /**
     * Refresh the restaurant list observable with the last updates from the data source.
     *
     * @return the [Result] of the refresh operation
     */
    suspend fun refreshRestaurantList(): Result<Nothing?>

    /**
     * Update the favourite status of a restaurant.
     *
     * @param restaurant the restaurant that will be updated
     * @return the [Result] of the update operation
     */
    suspend fun updateFavourite(restaurant: Restaurant): Result<Nothing?>

}
