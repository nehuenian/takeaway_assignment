package com.takeaway.assignment.data.sources.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.takeaway.assignment.data.PartialRestaurant
import com.takeaway.assignment.data.Restaurant


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
     * @param restaurantId id of the restaurant
     * @param isFavourite status to be updated
     */
    @Query("UPDATE restaurants SET favourite = :isFavourite WHERE id = :restaurantId")
    suspend fun updateFavourite(restaurantId: String, isFavourite: Boolean)

    @Transaction
    suspend fun insertOrUpdate(restaurant: Restaurant) {
        val insertResult = insertRestaurant(restaurant)
        if (insertResult == -1L) {
            updateRestaurant(PartialRestaurant(restaurant))
        }
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRestaurant(restaurant: Restaurant): Long

    @Update(entity = Restaurant::class)
    suspend fun updateRestaurant(partialRestaurant: PartialRestaurant)
}
