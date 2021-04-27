package com.takeaway.assignment.data.sources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.takeaway.assignment.data.Restaurant

@Database(entities = [Restaurant::class], version = 1, exportSchema = false)
abstract class AssignmentDb : RoomDatabase() {
    abstract fun restaurantsDao(): RestaurantsDao
}
