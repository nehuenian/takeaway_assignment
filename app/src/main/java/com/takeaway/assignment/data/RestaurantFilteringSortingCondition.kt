package com.takeaway.assignment.data

data class RestaurantFilteringSortingCondition(
    var searchFilter: String = "",
    var sortCondition: SortCondition = SortCondition.BEST_MATCH,
)
