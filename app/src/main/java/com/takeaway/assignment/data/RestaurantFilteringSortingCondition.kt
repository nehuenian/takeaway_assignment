package com.takeaway.assignment.data

data class RestaurantFilteringSortingCondition(
    var searchFilter: String = "",
    var sortCondition: SortCondition = SortCondition.DISTANCE,
    var sortOrder: SortOrder = SortOrder.ASCENDING,
)
