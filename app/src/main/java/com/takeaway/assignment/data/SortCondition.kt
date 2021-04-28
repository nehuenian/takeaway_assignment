package com.takeaway.assignment.data

enum class SortCondition {
    BEST_MATCH,
    NEWEST,
    RATING_AVERAGE,
    DISTANCE,
    POPULARITY,
    AVERAGE_PRODUCT_PRICE_LOWEST_FIRST,
    AVERAGE_PRODUCT_PRICE_HIGHEST_FIRST,
    DELIVERY_COSTS,
    MIN_COST;

    override fun toString(): String {
        return when (this) {
            BEST_MATCH -> "Best match"
            NEWEST -> "Newest"
            RATING_AVERAGE -> "Rating"
            DISTANCE -> "Distance"
            POPULARITY -> "Popularity"
            AVERAGE_PRODUCT_PRICE_LOWEST_FIRST -> "Average product price: lowest first"
            AVERAGE_PRODUCT_PRICE_HIGHEST_FIRST -> "Average product price: highest first"
            DELIVERY_COSTS -> "Delivery cost"
            MIN_COST -> "Min cost"
        }
    }
}
