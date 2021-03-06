package com.takeaway.assignment.testutil

import com.takeaway.assignment.data.Restaurant

const val FIRST_POSITION = 0
const val SECOND_POSITION = 1
const val THIRD_POSITION = 2
const val FOURTH_POSITION = 3

const val SEARCH_FILTER = "sushi"
const val NO_MATCHING_FILTER = "no matching filter"

val expectedFilteredRestaurantList = listOf(
    ExpectedRestaurant(FIRST_POSITION,
        "Sushi One",
        "Best match: 3.0",
        false,
        Restaurant.Status.OPEN.toString()),
    ExpectedRestaurant(SECOND_POSITION,
        "Tanoshii Sushi",
        "Best match: 0.0",
        false,
        Restaurant.Status.OPEN.toString()),
    ExpectedRestaurant(THIRD_POSITION,
        "Zenzai Sushi",
        "Best match: 13.0",
        false,
        Restaurant.Status.CLOSED.toString()),
    ExpectedRestaurant(FOURTH_POSITION,
        "Daily Sushi",
        "Best match: 9.0",
        false,
        Restaurant.Status.CLOSED.toString()),
)

val expectedRestaurantListSortedByBestMatch = listOf(
    ExpectedRestaurant(FIRST_POSITION,
        "Lunchpakketdienst",
        "Best match: 306.0",
        false,
        Restaurant.Status.OPEN.toString()),
    ExpectedRestaurant(SECOND_POSITION,
        "De Amsterdamsche Tram",
        "Best match: 304.0",
        false,
        Restaurant.Status.OPEN.toString()),
    ExpectedRestaurant(THIRD_POSITION,
        "CIRO 1939",
        "Best match: 12.0",
        false,
        Restaurant.Status.OPEN.toString()),
    ExpectedRestaurant(FOURTH_POSITION,
        "Indian Kitchen",
        "Best match: 11.0",
        false,
        Restaurant.Status.OPEN.toString()),
)

val expectedRestaurantListSortedByNewest = listOf(
    ExpectedRestaurant(FIRST_POSITION,
        "Indian Kitchen",
        "Newest: 272.0",
        false,
        Restaurant.Status.OPEN.toString()),
    ExpectedRestaurant(SECOND_POSITION,
        "Lunchpakketdienst",
        "Newest: 259.0",
        false,
        Restaurant.Status.OPEN.toString()),
    ExpectedRestaurant(THIRD_POSITION,
        "Roti Shop",
        "Newest: 247.0",
        false,
        Restaurant.Status.OPEN.toString()),
    ExpectedRestaurant(FOURTH_POSITION,
        "Sushi One",
        "Newest: 238.0",
        false,
        Restaurant.Status.OPEN.toString()),
)

val expectedRestaurantListSortedByRating = listOf(
    ExpectedRestaurant(FIRST_POSITION,
        "Tanoshii Sushi",
        "Rating: 4.5",
        false,
        Restaurant.Status.OPEN.toString()),
    ExpectedRestaurant(SECOND_POSITION,
        "Roti Shop",
        "Rating: 4.5",
        false,
        Restaurant.Status.OPEN.toString()),
    ExpectedRestaurant(THIRD_POSITION,
        "Aarti 2",
        "Rating: 4.5",
        false,
        Restaurant.Status.OPEN.toString()),
    ExpectedRestaurant(FOURTH_POSITION,
        "Indian Kitchen",
        "Rating: 4.5",
        false,
        Restaurant.Status.OPEN.toString()),
)

val expectedRestaurantListSortedByDistance = listOf(
    ExpectedRestaurant(FIRST_POSITION,
        "Tanoshii Sushi",
        "Distance: 1190 meters",
        false,
        Restaurant.Status.OPEN.toString()),
    ExpectedRestaurant(SECOND_POSITION,
        "Aarti 2",
        "Distance: 1605 meters",
        false,
        Restaurant.Status.OPEN.toString()),
    ExpectedRestaurant(THIRD_POSITION,
        "Sushi One",
        "Distance: 1618 meters",
        false,
        Restaurant.Status.OPEN.toString()),
    ExpectedRestaurant(FOURTH_POSITION,
        "Roti Shop",
        "Distance: 2308 meters",
        false,
        Restaurant.Status.OPEN.toString()),
)

val expectedRestaurantListSortedByPopularity = listOf(
    ExpectedRestaurant(FIRST_POSITION,
        "Roti Shop",
        "Popularity: 81.0",
        false,
        Restaurant.Status.OPEN.toString()),
    ExpectedRestaurant(SECOND_POSITION,
        "CIRO 1939",
        "Popularity: 79.0",
        false,
        Restaurant.Status.OPEN.toString()),
    ExpectedRestaurant(THIRD_POSITION,
        "Aarti 2",
        "Popularity: 44.0",
        false,
        Restaurant.Status.OPEN.toString()),
    ExpectedRestaurant(FOURTH_POSITION,
        "Sushi One",
        "Popularity: 23.0",
        false,
        Restaurant.Status.OPEN.toString()),
)

val expectedRestaurantListSortedByLowestPrice = listOf(
    ExpectedRestaurant(FIRST_POSITION,
        "De Amsterdamsche Tram",
        "Average product price: 892 USD",
        false,
        Restaurant.Status.OPEN.toString()),
    ExpectedRestaurant(SECOND_POSITION,
        "Roti Shop",
        "Average product price: 915 USD",
        false,
        Restaurant.Status.OPEN.toString()),
    ExpectedRestaurant(THIRD_POSITION,
        "Aarti 2",
        "Average product price: 922 USD",
        false,
        Restaurant.Status.OPEN.toString()),
    ExpectedRestaurant(FOURTH_POSITION,
        "Indian Kitchen",
        "Average product price: 1189 USD",
        false,
        Restaurant.Status.OPEN.toString()),
)

val expectedRestaurantListSortedByHighestPrice = listOf(
    ExpectedRestaurant(FIRST_POSITION,
        "Lunchpakketdienst",
        "Average product price: 4465 USD",
        false,
        Restaurant.Status.OPEN.toString()),
    ExpectedRestaurant(SECOND_POSITION,
        "CIRO 1939",
        "Average product price: 1762 USD",
        false,
        Restaurant.Status.OPEN.toString()),
    ExpectedRestaurant(THIRD_POSITION,
        "Tanoshii Sushi",
        "Average product price: 1536 USD",
        false,
        Restaurant.Status.OPEN.toString()),
    ExpectedRestaurant(FOURTH_POSITION,
        "Sushi One",
        "Average product price: 1285 USD",
        false,
        Restaurant.Status.OPEN.toString()),
)

val expectedRestaurantListSortedByDeliveryCost = listOf(
    ExpectedRestaurant(FIRST_POSITION,
        "Sushi One",
        "Delivery cost: 0 USD",
        false,
        Restaurant.Status.OPEN.toString()),
    ExpectedRestaurant(SECOND_POSITION,
        "Roti Shop",
        "Delivery cost: 0 USD",
        false,
        Restaurant.Status.OPEN.toString()),
    ExpectedRestaurant(THIRD_POSITION,
        "De Amsterdamsche Tram",
        "Delivery cost: 0 USD",
        false,
        Restaurant.Status.OPEN.toString()),
    ExpectedRestaurant(FOURTH_POSITION,
        "CIRO 1939",
        "Delivery cost: 99 USD",
        false,
        Restaurant.Status.OPEN.toString()),
)

val expectedRestaurantListSortedByMinCost = listOf(
    ExpectedRestaurant(THIRD_POSITION,
        "De Amsterdamsche Tram",
        "Min cost: 0 USD",
        false,
        Restaurant.Status.OPEN.toString()),
    ExpectedRestaurant(THIRD_POSITION,
        "Aarti 2",
        "Min cost: 500 USD",
        false,
        Restaurant.Status.OPEN.toString()),
    ExpectedRestaurant(THIRD_POSITION,
        "Tanoshii Sushi",
        "Min cost: 1000 USD",
        false,
        Restaurant.Status.OPEN.toString()),
    ExpectedRestaurant(FOURTH_POSITION,
        "Sushi One",
        "Min cost: 1200 USD",
        false,
        Restaurant.Status.OPEN.toString()),
)
