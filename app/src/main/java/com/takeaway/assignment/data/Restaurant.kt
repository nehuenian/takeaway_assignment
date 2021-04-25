package com.takeaway.assignment.data

import androidx.room.*
import com.google.gson.annotations.SerializedName
import com.takeaway.assignment.data.sources.local.RestaurantStatusConverter
import com.takeaway.assignment.data.sources.local.RestaurantTableStructure.COLUMN_FAVOURITE
import com.takeaway.assignment.data.sources.local.RestaurantTableStructure.COLUMN_NAME
import com.takeaway.assignment.data.sources.local.RestaurantTableStructure.COLUMN_STATUS
import com.takeaway.assignment.data.sources.local.RestaurantTableStructure.SORTING_VALUES_PREFIX
import com.takeaway.assignment.data.sources.local.RestaurantTableStructure.TABLE_NAME
import com.takeaway.assignment.data.sources.remote.RestaurantObject.NAME
import com.takeaway.assignment.data.sources.remote.RestaurantObject.SORTING_VALUES
import com.takeaway.assignment.data.sources.remote.RestaurantObject.STATUS
import com.takeaway.assignment.data.sources.remote.RestaurantObject.StatusValues

@Entity(
    tableName = TABLE_NAME,
    indices = [Index(COLUMN_NAME)]
)
data class Restaurant @JvmOverloads constructor(
    @field:SerializedName(NAME)
    @field:PrimaryKey
    @field:ColumnInfo(name = COLUMN_NAME)
    val name: String,

    @field:SerializedName(STATUS)
    @field:ColumnInfo(name = COLUMN_STATUS)
    @field:TypeConverters(RestaurantStatusConverter::class)
    val status: Status,

    @field:ColumnInfo(name = COLUMN_FAVOURITE)
    var isFavourite: Boolean = false,

    @field:SerializedName(SORTING_VALUES)
    @field:Embedded(prefix = SORTING_VALUES_PREFIX)
    val sortingValues: SortingValues,
) {
    enum class Status(val weight: Int) {
        @SerializedName(StatusValues.OPEN)
        OPEN(2),

        @SerializedName(StatusValues.CLOSED)
        CLOSED(0),

        @SerializedName(StatusValues.ORDER_AHEAD)
        ORDER_AHEAD(1)
    }
}
