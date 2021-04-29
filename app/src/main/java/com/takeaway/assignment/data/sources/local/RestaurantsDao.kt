package com.takeaway.assignment.data.sources.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.takeaway.assignment.data.PartialRestaurant
import com.takeaway.assignment.data.Restaurant

/**
 * DAO interface to interact with the DB in relation to the restaurants.
 */
@Dao
interface RestaurantsDao {

    /**
     * Observes the list of restaurants.
     *
     * @return an observable with the list of all the restaurants that will be updated over time.
     */
    @Query("SELECT * FROM restaurants")
    fun observeRestaurantList(): LiveData<List<Restaurant>>

    /**
     * Select all the restaurants from the restaurants table.
     *
     * @return the list of all the restaurants.
     */
    @Query("SELECT * FROM restaurants")
    suspend fun getRestaurantList(): List<Restaurant>

    /**
     * Update the favourite status of a restaurant (used for sorting purposes)
     *
     * @param restaurantName name of the restaurant
     * @param isFavourite status to be updated
     */
    @Query("UPDATE restaurants SET favourite = :isFavourite WHERE name = :restaurantName")
    suspend fun updateFavourite(restaurantName: String, isFavourite: Boolean)

    /**
     * Inserts the restaurant passed in the data base.
     * If the restaurant is already stored it will be updated instead
     *
     * @param restaurant the restaurant to insert/update
     */
    @Transaction
    suspend fun insertOrUpdate(restaurant: Restaurant) {
        val insertResult = insertRestaurant(restaurant)
        if (insertResult == -1L) {
            updateRestaurant(PartialRestaurant(restaurant))
        }
    }

    /**
     * Inserts a restaurant passed in the data base. It will ignore the insert if there is a
     * conflict in the operation
     *
     * @param restaurant the restaurant to insert
     * @return the row inserted, or -1 if the operation was ignored due to conflict
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRestaurant(restaurant: Restaurant): Long

    /**
     * Updates the restaurant passed in the data base.
     * This uses a partial version of the [Restaurant], that is the [PartialRestaurant], to update
     * only the values coming from the remote source
     *
     * @param partialRestaurant the [PartialRestaurant] to update the [Restaurant]
     */
    @Update(entity = Restaurant::class)
    suspend fun updateRestaurant(partialRestaurant: PartialRestaurant)
}
