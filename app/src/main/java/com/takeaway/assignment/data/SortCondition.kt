package com.takeaway.assignment.data

enum class SortCondition {
    BEST_MATCH,
    NEWEST,
    RATING_AVERAGE,
    DISTANCE,
    POPULARITY,
    AVERAGE_PRODUCT_PRICE,
    DELIVERY_COSTS,
    MIN_COST;

    override fun toString(): String {
        return when (this) {
            BEST_MATCH -> "Best match"
            NEWEST -> "Newest"
            RATING_AVERAGE -> "Rating"
            DISTANCE -> "Distance"
            POPULARITY -> "Popularity"
            AVERAGE_PRODUCT_PRICE -> "Average product price"
            DELIVERY_COSTS -> "Delivery cost"
            MIN_COST -> "Min cost"
        }
    }
}

enum class SortOrder {
    ASCENDING, DESCENDING
}
