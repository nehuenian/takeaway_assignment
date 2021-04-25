package com.takeaway.assignment.data.sources

import androidx.lifecycle.LiveData
import com.takeaway.assignment.data.Restaurant
import com.takeaway.assignment.data.Result

/**
 * Main source of truth for accessing restaurants data.
 */
interface RestaurantsDataSource {

    /**
     * Gets the live data in which will be published all the changes on the restaurant list.
     * This is done through a [Result] that wraps the different
     * possible states of the response.
     *
     * @return the observable that will receive the restaurant list updates
     */
    val observableRestaurantList: LiveData<Result<List<Restaurant>>>

    /**
     * Gets the result of the request to get the restaurant list.
     *
     * @return the [Result] related to the request for the restaurant list
     */
    suspend fun getRestaurantList(): Result<List<Restaurant>>

    /**
     * Refresh the restaurant list observable with the last updates from the data source.
     */
    suspend fun refreshRestaurantList()

    /**
     * Update the favourite status of a restaurant.
     *
     * @param restaurantName the name of the restaurant that will be updated
     * @param isFavourite the new favourite status
     * @return the [Result] of the update
     */
    suspend fun updateFavourite(restaurantName: String, isFavourite: Boolean): Result<Nothing?>

    /**
     * Insert or update a restaurant, depending on if the restaurant already exists
     * in the source.
     *
     * @param restaurant the [Restaurant] to be inserted or updated
     */
    suspend fun insertOrUpdateRestaurant(restaurant: Restaurant)
}
