package com.takeaway.assignment.usecases

import com.takeaway.assignment.data.Restaurant
import com.takeaway.assignment.data.Result
import com.takeaway.assignment.data.sources.RestaurantsRepository
import javax.inject.Inject

class UpdateRestaurantFavouriteStatusUseCase @Inject constructor(
    private val restaurantsRepository: RestaurantsRepository,
) {
    suspend operator fun invoke(restaurant: Restaurant): Result<Nothing?> {
        return restaurantsRepository.updateFavourite(restaurant)
    }
}
