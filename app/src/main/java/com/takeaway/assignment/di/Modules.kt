package com.takeaway.assignment.di

import android.content.Context
import androidx.room.Room
import com.takeaway.assignment.data.sources.RestaurantsDataSource
import com.takeaway.assignment.data.sources.local.AssignmentDb
import com.takeaway.assignment.data.sources.local.RestaurantsLocalDataSource
import com.takeaway.assignment.data.sources.remote.RestaurantsRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class RemoteRestaurantsDataSource

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class LocalRestaurantsDataSource

    @Singleton
    @RemoteRestaurantsDataSource
    @Provides
    fun provideRestaurantsRemoteDataSource(context: Context): RestaurantsDataSource {
        return RestaurantsRemoteDataSource(context)
    }

    @Singleton
    @LocalRestaurantsDataSource
    @Provides
    fun provideRestaurantsLocalDataSource(
        database: AssignmentDb,
        ioDispatcher: CoroutineDispatcher
    ): RestaurantsDataSource {
        return RestaurantsLocalDataSource(
            database.restaurantsDao(), ioDispatcher
        )
    }

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): AssignmentDb {
        return Room.databaseBuilder(
            context.applicationContext,
            AssignmentDb::class.java,
            "Assignment.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO
}
