package com.takeaway.assignment.testutil

import com.takeaway.assignment.data.Restaurant
import com.takeaway.assignment.data.SortingValues

const val FIRST_RESTAURANT = "first_restaurant"
const val SECOND_RESTAURANT = "second_restaurant"
const val THIRD_RESTAURANT_NOT_FILTERED = "third_rest"
const val RESTAURANT_WITH_BEST_SORTING_VALUE = "restaurant_with_best_sorting_value"
const val RESTAURANT_FAVOURITE_CLOSED_LOW_SORTING_VALUE =
    "restaurant_favourite_closed_low_sorting_value"
const val RESTAURANT_FAVOURITE_CLOSED_HIGH_SORTING_VALUE =
    "restaurant_favourite_closed_high_sorting_value"
const val RESTAURANT_FAVOURITE_ORDER_AHEAD_HIGH_SORTING_VALUE =
    "restaurant_favourite_order_ahead_high_sorting_value"
const val RESTAURANT_FAVOURITE_OPEN_LOW_SORTING_VALUE =
    "restaurant_favourite_open_low_sorting_value"
const val RESTAURANT_FAVOURITE_ORDER_AHEAD_LOW_SORTING_VALUE =
    "restaurant_favourite_order_ahead_low_sorting_value"
const val RESTAURANT_NOT_FAVOURITE_OPEN_LOW_SORTING_VALUE =
    "restaurant_not_favourite_open_low_sorting_value"
const val RESTAURANT_NOT_FAVOURITE_CLOSED_HIGH_SORTING_VALUE =
    "restaurant_not_favourite_closed_high_sorting_value"
const val RESTAURANT_NOT_FAVOURITE_OPEN_HIGH_SORTING_VALUE =
    "restaurant_not_favourite_open_high_sorting_value"
const val RESTAURANT_NOT_FAVOURITE_ORDER_AHEAD_LOW_SORTING_VALUE =
    "restaurant_not_favourite_order_ahead_low_sorting_value"
const val RESTAURANT_SUFFIX_FILTER = "restaurant"
const val RESTAURANT_PREFIX_FILTER = "first"

fun generateThreeRestaurantsWithNoFavourites(): ArrayList<Restaurant> {
    return arrayListOf(
        generateDefaultRestaurant(FIRST_RESTAURANT),
        generateDefaultRestaurant(SECOND_RESTAURANT),
        generateDefaultRestaurant(THIRD_RESTAURANT_NOT_FILTERED)
    )
}

fun generateThreeRestaurantsWithLastOneFavourite(): ArrayList<Restaurant> {
    return arrayListOf(
        generateDefaultRestaurant(FIRST_RESTAURANT),
        generateDefaultRestaurant(SECOND_RESTAURANT),
        generateDefaultFavouriteRestaurant(THIRD_RESTAURANT_NOT_FILTERED)
    )
}

fun generateThreeRestaurantsWithUnorderedStatus(): ArrayList<Restaurant> {
    return arrayListOf(
        generateDefaultRestaurant(FIRST_RESTAURANT, Restaurant.Status.ORDER_AHEAD),
        generateDefaultRestaurant(SECOND_RESTAURANT, Restaurant.Status.CLOSED),
        generateDefaultRestaurant(THIRD_RESTAURANT_NOT_FILTERED, Restaurant.Status.OPEN)
    )
}

fun generateThreeRestaurantsWithUnorderedStatusAndOneFavourite(): ArrayList<Restaurant> {
    return arrayListOf(
        generateDefaultRestaurant(FIRST_RESTAURANT, Restaurant.Status.ORDER_AHEAD),
        generateDefaultFavouriteRestaurant(SECOND_RESTAURANT, Restaurant.Status.CLOSED),
        generateDefaultRestaurant(THIRD_RESTAURANT_NOT_FILTERED, Restaurant.Status.OPEN)
    )
}

fun generateMixedRestaurantList(): ArrayList<Restaurant> {
    return arrayListOf(
        generateDefaultRestaurant(THIRD_RESTAURANT_NOT_FILTERED, Restaurant.Status.OPEN),
        generateDefaultFavouriteRestaurant(
            RESTAURANT_FAVOURITE_CLOSED_LOW_SORTING_VALUE,
            Restaurant.Status.CLOSED
        ),
        generateDefaultFavouriteRestaurant(
            RESTAURANT_FAVOURITE_ORDER_AHEAD_LOW_SORTING_VALUE,
            Restaurant.Status.ORDER_AHEAD
        ),
        generateDefaultRestaurant(
            RESTAURANT_NOT_FAVOURITE_OPEN_LOW_SORTING_VALUE
        ),
        generateFavouriteRestaurantHighSortValue(
            RESTAURANT_FAVOURITE_ORDER_AHEAD_HIGH_SORTING_VALUE,
            Restaurant.Status.ORDER_AHEAD
        ),
        generateDefaultRestaurantHighSortValue(
            RESTAURANT_NOT_FAVOURITE_CLOSED_HIGH_SORTING_VALUE,
            Restaurant.Status.CLOSED
        ),
        generateDefaultFavouriteRestaurant(
            RESTAURANT_FAVOURITE_OPEN_LOW_SORTING_VALUE
        ),
        generateDefaultRestaurantHighSortValue(
            RESTAURANT_NOT_FAVOURITE_OPEN_HIGH_SORTING_VALUE,
            Restaurant.Status.OPEN
        ),
        generateFavouriteRestaurantHighSortValue(
            RESTAURANT_FAVOURITE_CLOSED_HIGH_SORTING_VALUE,
            Restaurant.Status.CLOSED
        ),
        generateDefaultRestaurant(
            RESTAURANT_NOT_FAVOURITE_ORDER_AHEAD_LOW_SORTING_VALUE,
            Restaurant.Status.ORDER_AHEAD
        )
    )
}

fun generateRestaurantWithBestAscendingSortingValues(): Restaurant {
    return generateBestAscendingSortingValuesRestaurant()
}

fun generateRestaurantWithBestDescendingSortingValues(): Restaurant {
    return generateBestDescendingSortingValuesRestaurant()
}

private fun generateBestAscendingSortingValuesRestaurant() = Restaurant(
    name = RESTAURANT_WITH_BEST_SORTING_VALUE,
    status = Restaurant.Status.OPEN,
    isFavourite = false,
    sortingValues = generateBestAscendingSortingValues()
)

private fun generateBestDescendingSortingValuesRestaurant() = Restaurant(
    name = RESTAURANT_WITH_BEST_SORTING_VALUE,
    status = Restaurant.Status.OPEN,
    isFavourite = false,
    sortingValues = generateBestDescendingSortingValues()
)

private fun generateBadSortingValues() = SortingValues(
    bestMatch = 0.0,
    newest = 50.0,
    ratingAverage = 0.0,
    distance = 5000,
    popularity = 0.0,
    averageProductPrice = 80,
    deliveryCosts = 10,
    minCost = 30
)

private fun generateBestAscendingSortingValues() = SortingValues(
    bestMatch = 10.0,
    newest = 0.0,
    ratingAverage = 98.0,
    distance = 500,
    popularity = 100.0,
    averageProductPrice = 20,
    deliveryCosts = 0,
    minCost = 10
)

private fun generateBestDescendingSortingValues() = SortingValues(
    bestMatch = 10.0,
    newest = 100.0,
    ratingAverage = 98.0,
    distance = 500,
    popularity = 100.0,
    averageProductPrice = 120,
    deliveryCosts = 0,
    minCost = 10
)

private fun generateDefaultRestaurant(name: String) = Restaurant(
    name = name,
    status = Restaurant.Status.OPEN,
    isFavourite = false,
    sortingValues = generateBadSortingValues()
)

private fun generateDefaultRestaurant(name: String, status: Restaurant.Status) = Restaurant(
    name = name,
    status = status,
    isFavourite = false,
    sortingValues = generateBadSortingValues()
)

private fun generateDefaultRestaurantHighSortValue(name: String, status: Restaurant.Status) =
    Restaurant(
        name = name,
        status = status,
        isFavourite = false,
        sortingValues = generateBestDescendingSortingValues()
    )

private fun generateDefaultFavouriteRestaurant(name: String) = Restaurant(
    name = name,
    status = Restaurant.Status.OPEN,
    isFavourite = true,
    sortingValues = generateBadSortingValues()
)

private fun generateDefaultFavouriteRestaurant(name: String, status: Restaurant.Status) =
    Restaurant(
        name = name,
        status = status,
        isFavourite = true,
        sortingValues = generateBadSortingValues()
    )

private fun generateFavouriteRestaurantHighSortValue(name: String, status: Restaurant.Status) =
    Restaurant(
        name = name,
        status = status,
        isFavourite = true,
        sortingValues = generateBestDescendingSortingValues()
    )

