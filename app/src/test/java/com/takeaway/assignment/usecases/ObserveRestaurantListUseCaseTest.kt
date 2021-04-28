package com.takeaway.assignment.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.takeaway.assignment.data.Restaurant
import com.takeaway.assignment.data.RestaurantFilteringSortingCondition
import com.takeaway.assignment.data.Result
import com.takeaway.assignment.data.SortCondition
import com.takeaway.assignment.data.sources.FakeRestaurantsRepository
import com.takeaway.assignment.testutil.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ObserveRestaurantListUseCaseTest {

    private val restaurantsRepository = FakeRestaurantsRepository()
    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    private val observeRestaurantListUseCase =
        ObserveRestaurantListUseCase(restaurantsRepository, testCoroutineDispatcher)

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `Test load restaurant list without restaurants in it`() = runBlockingTest {
        // Given an empty repository
        val filteringSortingCondition = RestaurantFilteringSortingCondition()

        // When calling the use case without name filtering nor custom sorting condition
        val result = observeRestaurantListUseCase(filteringSortingCondition).getOrAwaitValue()

        // Then the result is success with empty data in it
        assert(result is Result.Success)
        assert((result as Result.Success).data.isEmpty())
    }

    @Test
    fun `Test load restaurant list without favourites and default sort options selected`() =
        runBlockingTest {
            // Given a repository with 3 restaurants not marked as favourites
            restaurantsRepository.restaurantsList = generateThreeRestaurantsWithNoFavourites()
            val filteringSortingCondition = RestaurantFilteringSortingCondition()

            // When calling the use case without name filtering nor custom sorting condition
            val result = observeRestaurantListUseCase(filteringSortingCondition).getOrAwaitValue()

            // Then the result is success with three restaurants showing in the original order
            assert(result is Result.Success)
            (result as Result.Success).data.let { restaurantList ->
                assert(restaurantList.isNotEmpty())
                assert(restaurantList[0].name == FIRST_RESTAURANT)
                assert(restaurantList[1].name == SECOND_RESTAURANT)
                assert(restaurantList[2].name == THIRD_RESTAURANT_NOT_FILTERED)
            }
        }

    @Test
    fun `Test load restaurant list filtering by name with suffix and default sort condition selected`() =
        runBlockingTest {
            // Given a repository with 3 restaurants not marked as favourites one which does not include the suffix filtered by
            restaurantsRepository.restaurantsList = generateThreeRestaurantsWithNoFavourites()
            val filteringSortingCondition = RestaurantFilteringSortingCondition(
                searchFilter = RESTAURANT_SUFFIX_FILTER
            )

            // When calling the use case with name suffix filtering and no custom sorting condition
            val result = observeRestaurantListUseCase(filteringSortingCondition).getOrAwaitValue()

            // Then the result is success with the two restaurants with the filtered suffix
            assert(result is Result.Success)
            (result as Result.Success).data.let { restaurantList ->
                assert(restaurantList.size == 2)
                assert(restaurantList[0].name == FIRST_RESTAURANT)
                assert(restaurantList[1].name == SECOND_RESTAURANT)
            }
        }

    @Test
    fun `Test load restaurant list filtering by name with prefix and default sort condition selected`() =
        runBlockingTest {
            // Given a repository with 3 restaurants not marked as favourites one which will match the prefix filter
            restaurantsRepository.restaurantsList = generateThreeRestaurantsWithNoFavourites()
            val filteringSortingCondition = RestaurantFilteringSortingCondition(
                searchFilter = RESTAURANT_PREFIX_FILTER
            )

            // When calling the use case with name suffix filtering and no custom sorting condition
            val result = observeRestaurantListUseCase(filteringSortingCondition).getOrAwaitValue()

            // Then the result is success with the unique restaurant matching the prefix filter
            assert(result is Result.Success)
            (result as Result.Success).data.let { restaurantList ->
                assert(restaurantList.size == 1)
                assert(restaurantList[0].name == FIRST_RESTAURANT)
            }
        }

    @Test
    fun `Test load restaurant list sorted by favourite status`() = runBlockingTest {
        // Given a repository with 3 restaurants, being the last of those the favourite one
        restaurantsRepository.restaurantsList = generateThreeRestaurantsWithLastOneFavourite()
        val filteringSortingCondition = RestaurantFilteringSortingCondition()

        // When calling the use case with no filtering and no custom sorting condition
        val result = observeRestaurantListUseCase(filteringSortingCondition).getOrAwaitValue()

        // Then the result is success with the last restaurant in the original order being the first one
        assert(result is Result.Success)
        (result as Result.Success).data.let { restaurantList ->
            assert(restaurantList.size == 3)
            assert(restaurantList[0].name == THIRD_RESTAURANT_NOT_FILTERED)
            assert(restaurantList[1].name == FIRST_RESTAURANT)
            assert(restaurantList[2].name == SECOND_RESTAURANT)
        }
    }

    @Test
    fun `Test load restaurant list sorted by restaurant status`() = runBlockingTest {
        // Given a repository with 3 restaurants all with wrong initial order regarding their status
        restaurantsRepository.restaurantsList = generateThreeRestaurantsWithUnorderedStatus()
        val filteringSortingCondition = RestaurantFilteringSortingCondition()

        // When calling the use case with no filtering and no custom sorting condition
        val result = observeRestaurantListUseCase(filteringSortingCondition).getOrAwaitValue()

        // Then the result is success with the restaurants ordered as follow: open -> order ahead -> closed
        assert(result is Result.Success)
        (result as Result.Success).data.let { restaurantList ->
            assert(restaurantList.size == 3)
            assert(restaurantList[0].name == THIRD_RESTAURANT_NOT_FILTERED)
            assert(restaurantList[1].name == FIRST_RESTAURANT)
            assert(restaurantList[2].name == SECOND_RESTAURANT)
        }
    }

    @Test
    fun `Test load restaurant list sorted by favourite and restaurant status`() = runBlockingTest {
        // Given a repository with 3 restaurants all with wrong initial order regarding their status,
        // and the only favourite being the closed one
        restaurantsRepository.restaurantsList =
            generateThreeRestaurantsWithUnorderedStatusAndOneFavourite()
        val filteringSortingCondition = RestaurantFilteringSortingCondition()

        // When calling the use case with no filtering and no custom sorting condition
        val result = observeRestaurantListUseCase(filteringSortingCondition).getOrAwaitValue()

        // Then the result is success with the restaurants ordered as follow:
        // closed-favourite -> open -> order ahead
        assert(result is Result.Success)
        (result as Result.Success).data.let { restaurantList ->
            assert(restaurantList.size == 3)
            assert(restaurantList[0].name == SECOND_RESTAURANT)
            assert(restaurantList[1].name == THIRD_RESTAURANT_NOT_FILTERED)
            assert(restaurantList[2].name == FIRST_RESTAURANT)
        }
    }

    @Test
    fun `Test load restaurant list sorted by descending best match value`() = runBlockingTest {
        // Given a repository with 4 restaurants, being the last one the one with best sorting value
        setUpInitialRestaurantsPlusRestaurantWithBestDescendingValues()
        val filteringSortingCondition = RestaurantFilteringSortingCondition(
            sortCondition = SortCondition.BEST_MATCH
        )

        // When calling the use case with no filtering and descending best match value sorting condition
        val result = observeRestaurantListUseCase(filteringSortingCondition).getOrAwaitValue()

        // Then the result is success with the restaurant with the greatest best match value
        // showing first, and the other restaurants in the list in the original order
        assertCorrectOrderWithCustomSortingCondition(result)
    }

    @Test
    fun `Test load restaurant list sorted by descending newest value`() = runBlockingTest {
        // Given a repository with 4 restaurants, being the last one the one with highest newest value
        setUpInitialRestaurantsPlusRestaurantWithBestDescendingValues()
        val filteringSortingCondition = RestaurantFilteringSortingCondition(
            sortCondition = SortCondition.NEWEST
        )

        // When calling the use case with no filtering and descending newest value sorting condition
        val result = observeRestaurantListUseCase(filteringSortingCondition).getOrAwaitValue()

        // Then the result is success with the restaurant with the biggest newest value
        // showing first, and the other restaurants in the list in the original order
        assertCorrectOrderWithCustomSortingCondition(result)
    }

    @Test
    fun `Test load restaurant list sorted by descending rating average`() = runBlockingTest {
        // Given a repository with 4 restaurants, being the last one the one with highest rating average
        setUpInitialRestaurantsPlusRestaurantWithBestDescendingValues()
        val filteringSortingCondition = RestaurantFilteringSortingCondition(
            sortCondition = SortCondition.RATING_AVERAGE
        )

        // When calling the use case with no filtering and descending rating average sorting condition
        val result = observeRestaurantListUseCase(filteringSortingCondition).getOrAwaitValue()

        // Then the result is success with the restaurant with the biggest rating average
        // showing first, and the other restaurants in the list in the original order
        assertCorrectOrderWithCustomSortingCondition(result)
    }

    @Test
    fun `Test load restaurant list sorted by ascending distance`() = runBlockingTest {
        // Given a repository with 4 restaurants, being the last one the one with the lowest distance
        setUpInitialRestaurantsPlusRestaurantWithBestAscendingValues()
        val filteringSortingCondition = RestaurantFilteringSortingCondition(
            sortCondition = SortCondition.DISTANCE
        )

        // When calling the use case with no filtering and ascending distance sorting condition
        val result = observeRestaurantListUseCase(filteringSortingCondition).getOrAwaitValue()

        // Then the result is success with the restaurant with the biggest rating average
        // showing first, and the other restaurants in the list in the original order
        assertCorrectOrderWithCustomSortingCondition(result)
    }

    @Test
    fun `Test load restaurant list sorted by descending popularity`() = runBlockingTest {
        // Given a repository with 4 restaurants, being the last one the one with highest popularity
        setUpInitialRestaurantsPlusRestaurantWithBestDescendingValues()
        val filteringSortingCondition = RestaurantFilteringSortingCondition(
            sortCondition = SortCondition.POPULARITY
        )

        // When calling the use case with no filtering and descending popularity sorting condition
        val result = observeRestaurantListUseCase(filteringSortingCondition).getOrAwaitValue()

        // Then the result is success with the restaurant with the biggest popularity
        // showing first, and the other restaurants in the list in the original order
        assertCorrectOrderWithCustomSortingCondition(result)
    }

    @Test
    fun `Test load restaurant list sorted by descending average price`() = runBlockingTest {
        // Given a repository with 4 restaurants, being the last one the one with highest average price
        setUpInitialRestaurantsPlusRestaurantWithBestDescendingValues()
        val filteringSortingCondition = RestaurantFilteringSortingCondition(
            sortCondition = SortCondition.AVERAGE_PRODUCT_PRICE_HIGHEST_FIRST
        )

        // When calling the use case with no filtering and descending average price sorting condition
        val result = observeRestaurantListUseCase(filteringSortingCondition).getOrAwaitValue()

        // Then the result is success with the restaurant with the highest average price
        // showing first, and the other restaurants in the list in the original order
        assertCorrectOrderWithCustomSortingCondition(result)
    }

    @Test
    fun `Test load restaurant list sorted by ascending average price`() = runBlockingTest {
        // Given a repository with 4 restaurants, being the last one the one with lowest average price
        setUpInitialRestaurantsPlusRestaurantWithBestAscendingValues()
        val filteringSortingCondition = RestaurantFilteringSortingCondition(
            sortCondition = SortCondition.AVERAGE_PRODUCT_PRICE_LOWEST_FIRST
        )

        // When calling the use case with no filtering and ascending average price sorting condition
        val result = observeRestaurantListUseCase(filteringSortingCondition).getOrAwaitValue()

        // Then the result is success with the restaurant with the lowest average price
        // showing first, and the other restaurants in the list in the original order
        assertCorrectOrderWithCustomSortingCondition(result)
    }

    @Test
    fun `Test load restaurant list sorted by ascending delivery cost`() = runBlockingTest {
        // Given a repository with 4 restaurants, being the last one the one with lowest delivery cost
        setUpInitialRestaurantsPlusRestaurantWithBestAscendingValues()
        val filteringSortingCondition = RestaurantFilteringSortingCondition()

        // When calling the use case with no filtering and ascending delivery cost sorting condition
        val result = observeRestaurantListUseCase(filteringSortingCondition).getOrAwaitValue()

        // Then the result is success with the restaurant with the lowest delivery cost
        // showing first, and the other restaurants in the list in the original order
        assertCorrectOrderWithCustomSortingCondition(result)
    }

    @Test
    fun `Test load restaurant list sorted by ascending min cost`() = runBlockingTest {
        // Given a repository with 4 restaurants, being the last one the one with lowest min cost
        setUpInitialRestaurantsPlusRestaurantWithBestAscendingValues()
        val filteringSortingCondition = RestaurantFilteringSortingCondition(
            sortCondition = SortCondition.MIN_COST
        )

        // When calling the use case with no filtering and ascending min cost sorting condition
        val result = observeRestaurantListUseCase(filteringSortingCondition).getOrAwaitValue()

        // Then the result is success with the restaurant with the lowest min cost
        // showing first, and the other restaurants in the list in the original order
        assertCorrectOrderWithCustomSortingCondition(result)
    }

    @Test
    fun `Test load restaurant list filtered by a suffix and sorted by favourite, restaurant status and custom sort condition`() {
        runBlockingTest {
            // Given a repository with 10 restaurants all with different favourite and
            // restaurant status, with different sorting values
            restaurantsRepository.restaurantsList = generateMixedRestaurantList()
            val filteringSortingCondition = RestaurantFilteringSortingCondition(
                searchFilter = RESTAURANT_SUFFIX_FILTER,
                sortCondition = SortCondition.DISTANCE
            )

            // When calling the use case with suffix filtering, and custom sort condition
            val result = observeRestaurantListUseCase(filteringSortingCondition).getOrAwaitValue()

            // Then the result is success with 9 restaurants ordered following the criteria
            // below (from the highest to the lowest priority):
            // 1. Favourites: Favourite restaurants are at the top of the list.
            // 2. Opening state: Restaurant is either open (top), you can order ahead (middle)
            // or a restaurant is currently closed (bottom)
            // 3. Sort options: Restaurant with best value in terms of the sort option and selected
            // order (ascending or descending)
            assert(result is Result.Success)
            (result as Result.Success).data.let { restaurantList ->
                assert(restaurantList.size == 9)
                assert(restaurantList[0].name == RESTAURANT_FAVOURITE_OPEN_LOW_SORTING_VALUE)
                assert(restaurantList[1].name == RESTAURANT_FAVOURITE_ORDER_AHEAD_HIGH_SORTING_VALUE)
                assert(restaurantList[2].name == RESTAURANT_FAVOURITE_ORDER_AHEAD_LOW_SORTING_VALUE)
                assert(restaurantList[3].name == RESTAURANT_FAVOURITE_CLOSED_HIGH_SORTING_VALUE)
                assert(restaurantList[4].name == RESTAURANT_FAVOURITE_CLOSED_LOW_SORTING_VALUE)
                assert(restaurantList[5].name == RESTAURANT_NOT_FAVOURITE_OPEN_HIGH_SORTING_VALUE)
                assert(restaurantList[6].name == RESTAURANT_NOT_FAVOURITE_OPEN_LOW_SORTING_VALUE)
                assert(restaurantList[7].name == RESTAURANT_NOT_FAVOURITE_ORDER_AHEAD_LOW_SORTING_VALUE)
                assert(restaurantList[8].name == RESTAURANT_NOT_FAVOURITE_CLOSED_HIGH_SORTING_VALUE)
            }
        }
    }

    @Test
    fun `Test load restaurant list with error`() = runBlockingTest {
        // Given a repository that returns error during loading
        restaurantsRepository.shouldReturnError = true
        val filteringSortingCondition = RestaurantFilteringSortingCondition()

        // When calling the use case with no filtering
        val result = observeRestaurantListUseCase(filteringSortingCondition).getOrAwaitValue()

        // Then the result is error
        assert(result is Result.Error)
    }

    private fun setUpInitialRestaurantsPlusRestaurantWithBestAscendingValues() {
        restaurantsRepository.restaurantsList =
            generateThreeRestaurantsWithNoFavourites()
        restaurantsRepository.restaurantsList.add(generateRestaurantWithBestAscendingSortingValues())
    }

    private fun setUpInitialRestaurantsPlusRestaurantWithBestDescendingValues() {
        restaurantsRepository.restaurantsList =
            generateThreeRestaurantsWithNoFavourites()
        restaurantsRepository.restaurantsList.add(generateRestaurantWithBestDescendingSortingValues())
    }

    private fun assertCorrectOrderWithCustomSortingCondition(result: Result<List<Restaurant>>) {
        assert(result is Result.Success)
        (result as Result.Success).data.let { restaurantList ->
            assert(restaurantList.size == 4)
            assert(restaurantList[0].name == RESTAURANT_WITH_BEST_SORTING_VALUE)
            assert(restaurantList[1].name == FIRST_RESTAURANT)
            assert(restaurantList[2].name == SECOND_RESTAURANT)
            assert(restaurantList[3].name == THIRD_RESTAURANT_NOT_FILTERED)
        }
    }
}