package com.takeaway.assignment.data.sources.local

// restaurants table
object RestaurantTableStructure {
    const val TABLE_NAME = "restaurants"

    const val COLUMN_ID = "id"
    const val COLUMN_NAME = "name"
    const val COLUMN_STATUS = "status"
    const val COLUMN_FAVOURITE = "favourite"

    // Sorting Values
    const val SORTING_VALUES_PREFIX = "sorting_"
    const val COLUMN_BEST_MATCH = "bestMatch"
    const val COLUMN_NEWEST = "newest"
    const val COLUMN_RATING_AVERAGE = "ratingAverage"
    const val COLUMN_DISTANCE = "distance"
    const val COLUMN_POPULARITY = "popularity"
    const val COLUMN_AVERAGE_PRODUCT_PRICE = "averageProductPrice"
    const val COLUMN_DELIVERY_COSTS = "deliveryCosts"
    const val COLUMN_MIN_COST = "minCost"
}
