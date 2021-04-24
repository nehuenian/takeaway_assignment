package com.takeaway.assignment.di

import android.content.Context
import com.takeaway.assignment.data.sources.RestaurantsDataSource
import com.takeaway.assignment.data.sources.remote.RestaurantsRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class RemoteTasksDataSource

    @Singleton
    @RemoteTasksDataSource
    @Provides
    fun provideRestaurantsRemoteDataSource(context: Context): RestaurantsDataSource {
        return RestaurantsRemoteDataSource(context)
    }
}
