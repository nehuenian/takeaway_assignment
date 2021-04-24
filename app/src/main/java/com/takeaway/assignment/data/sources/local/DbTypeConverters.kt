package com.takeaway.assignment.data.sources.local

import androidx.room.TypeConverter
import com.takeaway.assignment.data.Restaurant

object RestaurantStatusConverter {
    @TypeConverter
    fun fromRestaurantStatus(value: Restaurant.Status) = value.ordinal

    @TypeConverter
    fun toRestaurantStatus(value: Int) = enumValues<Restaurant.Status>()[value]
}
