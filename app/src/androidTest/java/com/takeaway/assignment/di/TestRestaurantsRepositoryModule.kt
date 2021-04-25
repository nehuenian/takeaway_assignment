package com.takeaway.assignment.di

import android.content.Context
import com.takeaway.assignment.data.sources.RestaurantsRepository
import com.takeaway.assignment.data.sources.TestRestaurantsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RestaurantsRepositoryModule::class]
)
object TestRestaurantsRepositoryModule {
    @Singleton
    @Provides
    fun provideRepository(@ApplicationContext context: Context): RestaurantsRepository {
        return TestRestaurantsRepository(context)
    }
}