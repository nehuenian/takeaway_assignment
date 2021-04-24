package com.takeaway.assignment.data

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.TypeConverters
import com.takeaway.assignment.data.sources.local.RestaurantStatusConverter
import com.takeaway.assignment.data.sources.local.RestaurantTableStructure.COLUMN_NAME
import com.takeaway.assignment.data.sources.local.RestaurantTableStructure.COLUMN_STATUS
import com.takeaway.assignment.data.sources.local.RestaurantTableStructure.SORTING_VALUES_PREFIX

@Entity
data class PartialRestaurant(
    @field:ColumnInfo(name = COLUMN_NAME)
    val name: String,

    @field:ColumnInfo(name = COLUMN_STATUS)
    @field:TypeConverters(RestaurantStatusConverter::class)
    val status: Restaurant.Status,

    @field:Embedded(prefix = SORTING_VALUES_PREFIX)
    val sortingValues: SortingValues
) {
    constructor(restaurant: Restaurant) : this(
        name = restaurant.name,
        status = restaurant.status,
        sortingValues = restaurant.sortingValues
    )
}
