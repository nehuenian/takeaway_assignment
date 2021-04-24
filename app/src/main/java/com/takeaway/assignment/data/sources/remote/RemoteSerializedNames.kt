package com.takeaway.assignment.data.sources.remote

object RestaurantObject {
    const val NAME = "name"
    const val STATUS = "status"
    const val SORTING_VALUES = "sortingValues"

    object StatusValues {
        const val OPEN = "open"
        const val CLOSED = "closed"
        const val ORDER_AHEAD = "order ahead"
    }
}

object SortingValuesObject {
    const val BEST_MATCH = "bestMatch"
    const val NEWEST = "newest"
    const val RATING_AVERAGE = "ratingAverage"
    const val DISTANCE = "distance"
    const val POPULARITY = "popularity"
    const val AVERAGE_PRODUCT_PRICE = "averageProductPrice"
    const val DELIVERY_COSTS = "deliveryCosts"
    const val MIN_COST = "minCost"
}
