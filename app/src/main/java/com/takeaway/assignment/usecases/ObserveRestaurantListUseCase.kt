package com.takeaway.assignment.usecases

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.takeaway.assignment.data.Restaurant
import com.takeaway.assignment.data.Result
import com.takeaway.assignment.data.SortCondition
import com.takeaway.assignment.data.SortOrder
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
        searchText: String = "",
        sortCondition: SortCondition = SortCondition.DISTANCE,
        sortOrder: SortOrder = SortOrder.ASCENDING,
    ): LiveData<Result<List<Restaurant>>> = withContext(dispatcher) {
        restaurantsRepository.observeRestaurantList().map { result ->
            if (result is Result.Success) {
                var finalRestaurantList = result.data
                    .filter { restaurant ->
                        restaurant.name.contains(searchText, true)
                    }

                finalRestaurantList = when (sortOrder) {
                    SortOrder.ASCENDING -> {
                        finalRestaurantList.sortByAscendingCondition(sortCondition)
                    }
                    SortOrder.DESCENDING -> {
                        finalRestaurantList.sortByDescendingCondition(sortCondition)
                    }
                }

                finalRestaurantList =
                    finalRestaurantList
                        .sortedByDescending { it.status.weight }
                        .sortedByDescending { it.isFavourite }

                Result.Success(finalRestaurantList)
            } else {
                result
            }
        }
    }

    private fun List<Restaurant>.sortByAscendingCondition(
        sortCondition: SortCondition,
    ) = when (sortCondition) {
        SortCondition.BEST_MATCH -> sortedBy { it.sortingValues.bestMatch }
        SortCondition.NEWEST -> sortedBy { it.sortingValues.newest }
        SortCondition.RATING_AVERAGE -> sortedBy { it.sortingValues.ratingAverage }
        SortCondition.DISTANCE -> sortedBy { it.sortingValues.distance }
        SortCondition.POPULARITY -> sortedBy { it.sortingValues.popularity }
        SortCondition.AVERAGE_PRODUCT_PRICE -> sortedBy { it.sortingValues.averageProductPrice }
        SortCondition.DELIVERY_COSTS -> sortedBy { it.sortingValues.deliveryCosts }
        SortCondition.MIN_COST -> sortedBy { it.sortingValues.minCost }
    }

    private fun List<Restaurant>.sortByDescendingCondition(
        sortCondition: SortCondition,
    ) = when (sortCondition) {
        SortCondition.BEST_MATCH -> sortedByDescending { it.sortingValues.bestMatch }
        SortCondition.NEWEST -> sortedByDescending { it.sortingValues.newest }
        SortCondition.RATING_AVERAGE -> sortedByDescending { it.sortingValues.ratingAverage }
        SortCondition.DISTANCE -> sortedByDescending { it.sortingValues.distance }
        SortCondition.POPULARITY -> sortedByDescending { it.sortingValues.popularity }
        SortCondition.AVERAGE_PRODUCT_PRICE -> sortedByDescending { it.sortingValues.averageProductPrice }
        SortCondition.DELIVERY_COSTS -> sortedByDescending { it.sortingValues.deliveryCosts }
        SortCondition.MIN_COST -> sortedByDescending { it.sortingValues.minCost }
    }
}
