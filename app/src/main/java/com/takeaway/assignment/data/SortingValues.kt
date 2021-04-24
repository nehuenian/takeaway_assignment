package com.takeaway.assignment.data

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName
import com.takeaway.assignment.data.sources.local.RestaurantTableStructure.COLUMN_AVERAGE_PRODUCT_PRICE
import com.takeaway.assignment.data.sources.local.RestaurantTableStructure.COLUMN_BEST_MATCH
import com.takeaway.assignment.data.sources.local.RestaurantTableStructure.COLUMN_DELIVERY_COSTS
import com.takeaway.assignment.data.sources.local.RestaurantTableStructure.COLUMN_DISTANCE
import com.takeaway.assignment.data.sources.local.RestaurantTableStructure.COLUMN_MIN_COST
import com.takeaway.assignment.data.sources.local.RestaurantTableStructure.COLUMN_NEWEST
import com.takeaway.assignment.data.sources.local.RestaurantTableStructure.COLUMN_POPULARITY
import com.takeaway.assignment.data.sources.local.RestaurantTableStructure.COLUMN_RATING_AVERAGE
import com.takeaway.assignment.data.sources.remote.SortingValuesObject.AVERAGE_PRODUCT_PRICE
import com.takeaway.assignment.data.sources.remote.SortingValuesObject.BEST_MATCH
import com.takeaway.assignment.data.sources.remote.SortingValuesObject.DELIVERY_COSTS
import com.takeaway.assignment.data.sources.remote.SortingValuesObject.DISTANCE
import com.takeaway.assignment.data.sources.remote.SortingValuesObject.MIN_COST
import com.takeaway.assignment.data.sources.remote.SortingValuesObject.NEWEST
import com.takeaway.assignment.data.sources.remote.SortingValuesObject.POPULARITY
import com.takeaway.assignment.data.sources.remote.SortingValuesObject.RATING_AVERAGE

data class SortingValues(
    @field:SerializedName(BEST_MATCH)
    @field:ColumnInfo(name = COLUMN_BEST_MATCH)
    val bestMatch: Double,
    @field:SerializedName(NEWEST)
    @field:ColumnInfo(name = COLUMN_NEWEST)
    val newest: Double,
    @field:SerializedName(RATING_AVERAGE)
    @field:ColumnInfo(name = COLUMN_RATING_AVERAGE)
    val ratingAverage: Double,
    @field:SerializedName(DISTANCE)
    @field:ColumnInfo(name = COLUMN_DISTANCE)
    val distance: Long,
    @field:SerializedName(POPULARITY)
    @field:ColumnInfo(name = COLUMN_POPULARITY)
    val popularity: Double,
    @field:SerializedName(AVERAGE_PRODUCT_PRICE)
    @field:ColumnInfo(name = COLUMN_AVERAGE_PRODUCT_PRICE)
    val averageProductPrice: Long,
    @field:SerializedName(DELIVERY_COSTS)
    @field:ColumnInfo(name = COLUMN_DELIVERY_COSTS)
    val deliveryCosts: Long,
    @field:SerializedName(MIN_COST)
    @field:ColumnInfo(name = COLUMN_MIN_COST)
    val minCost: Long
)
