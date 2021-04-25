package com.takeaway.assignment.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.takeaway.assignment.data.Result
import com.takeaway.assignment.data.sources.FakeRestaurantsRepository
import com.takeaway.assignment.testutil.generateThreeRestaurantsWithLastOneFavourite
import com.takeaway.assignment.testutil.generateThreeRestaurantsWithNoFavourites
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class UpdateRestaurantFavouriteStatusUseCaseTest {
    private val restaurantsRepository = FakeRestaurantsRepository()
    private val markRestaurantAsFavouriteUseCase =
        UpdateRestaurantFavouriteStatusUseCase(restaurantsRepository)

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `Test mark restaurant as favourite successfully`() = runBlockingTest {
        // Given a working repository with three restaurants without favourites
        restaurantsRepository.restaurantsList = generateThreeRestaurantsWithNoFavourites()
        assert(restaurantsRepository.restaurantsList.first().isFavourite.not())

        // When calling the use case to mark the first restaurant as favourite
        val restaurantToMark = restaurantsRepository.restaurantsList.first().copy().apply {
            isFavourite = true
        }
        val result = markRestaurantAsFavouriteUseCase(restaurantToMark)

        // Then the first restaurant in the repository is marked as favourite
        assert(result is Result.Success)
        assert(restaurantsRepository.restaurantsList.first().isFavourite)
    }

    @Test
    fun `Test un-mark restaurant as favourite successfully`() = runBlockingTest {
        // Given a working repository with three restaurants, being the last of those marked as favourite
        restaurantsRepository.restaurantsList = generateThreeRestaurantsWithLastOneFavourite()
        assert(restaurantsRepository.restaurantsList.last().isFavourite)

        // When calling the use case to un-mark the last restaurant as favourite
        val restaurantToMark = restaurantsRepository.restaurantsList.last().copy().apply {
            isFavourite = false
        }
        val result = markRestaurantAsFavouriteUseCase(restaurantToMark)

        // Then the last restaurant in the repository is un-marked as favourite
        assert(result is Result.Success)
        assert(restaurantsRepository.restaurantsList.last().isFavourite.not())
    }

    @Test
    fun `Test mark restaurant as favourite failing`() = runBlockingTest {
        // Given a repository with failures
        restaurantsRepository.restaurantsList = generateThreeRestaurantsWithNoFavourites()
        restaurantsRepository.shouldReturnError = true

        // When calling the use case to mark the first restaurant as favourite
        val restaurantToMark = restaurantsRepository.restaurantsList.first().copy().apply {
            isFavourite = true
        }
        val result = markRestaurantAsFavouriteUseCase(restaurantToMark)

        // Then the first restaurant in the repository is marked as favourite
        assert(result is Result.Error)
        assert(restaurantsRepository.restaurantsList.first().isFavourite.not())
    }
}
