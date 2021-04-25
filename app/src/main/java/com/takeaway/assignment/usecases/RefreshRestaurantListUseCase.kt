package com.takeaway.assignment.usecases

import com.takeaway.assignment.data.Result
import com.takeaway.assignment.data.sources.RestaurantsRepository
import javax.inject.Inject

class RefreshRestaurantListUseCase @Inject constructor(
    private val restaurantsRepository: RestaurantsRepository
) {
    suspend operator fun invoke(): Result<Nothing?> {
        return restaurantsRepository.refreshRestaurantList()
    }
}
