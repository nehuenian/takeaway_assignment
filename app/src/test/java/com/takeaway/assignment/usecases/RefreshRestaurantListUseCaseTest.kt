package com.takeaway.assignment.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.takeaway.assignment.data.Result
import com.takeaway.assignment.data.sources.FakeRestaurantsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class RefreshRestaurantListUseCaseTest {
    private val restaurantsRepository = FakeRestaurantsRepository()
    private val refreshRestaurantListUseCase = RefreshRestaurantListUseCase(restaurantsRepository)

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `Test refresh restaurant list with success`() = runBlockingTest {
        // Given a working repository
        restaurantsRepository.shouldReturnError = false

        // When calling the use case without name filtering nor custom sorting condition
        val result = refreshRestaurantListUseCase()

        // Then the result is success with empty data in it
        assert(result is Result.Success)
    }

    @Test
    fun `Test refresh restaurant list with error`() = runBlockingTest {
        // Given a failing repository
        restaurantsRepository.shouldReturnError = true

        // When calling the use case without name filtering nor custom sorting condition
        val result = refreshRestaurantListUseCase()

        // Then the result is success with empty data in it
        assert(result is Result.Error)
    }
}