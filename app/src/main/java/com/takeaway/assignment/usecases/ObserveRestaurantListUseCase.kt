package com.takeaway.assignment.usecases

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.takeaway.assignment.data.Restaurant
import com.takeaway.assignment.data.RestaurantFilteringSortingCondition
import com.takeaway.assignment.data.Result
import com.takeaway.assignment.data.SortCondition
import com.takeaway.assignment.data.sources.RestaurantsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ObserveRestaurantListUseCase @Inject constructor(
    private val restaurantsRepository: RestaurantsRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
) {
    suspend operator fun invoke(
        filteringSortingCondition: RestaurantFilteringSortingCondition,
    ): LiveData<Result<List<Restaurant>>> = withContext(dispatcher) {
        restaurantsRepository.observeRestaurantList().map { result ->
            if (result is Result.Success) {
                with(filteringSortingCondition) {
                    val finalRestaurantList = result.data
                        .filter { restaurant ->
                            restaurant.name.contains(searchFilter, true)
                        }
                        .sortByCondition(sortCondition)
                        .sortedByDescending { it.status.weight }
                        .sortedByDescending { it.isFavourite }

                    Result.Success(finalRestaurantList)
                }
            } else {
                result
            }
        }
    }

    private fun List<Restaurant>.sortByCondition(
        sortCondition: SortCondition,
    ) = when (sortCondition) {
        SortCondition.BEST_MATCH -> sortedByDescending { it.sortingValues.bestMatch }
        SortCondition.NEWEST -> sortedByDescending { it.sortingValues.newest }
        SortCondition.RATING_AVERAGE -> sortedByDescending { it.sortingValues.ratingAverage }
        SortCondition.DISTANCE -> sortedBy { it.sortingValues.distance }
        SortCondition.POPULARITY -> sortedByDescending { it.sortingValues.popularity }
        SortCondition.AVERAGE_PRODUCT_PRICE_LOWEST_FIRST -> sortedBy { it.sortingValues.averageProductPrice }
        SortCondition.AVERAGE_PRODUCT_PRICE_HIGHEST_FIRST -> sortedByDescending { it.sortingValues.averageProductPrice }
        SortCondition.DELIVERY_COSTS -> sortedBy { it.sortingValues.deliveryCosts }
        SortCondition.MIN_COST -> sortedBy { it.sortingValues.minCost }
    }
}
