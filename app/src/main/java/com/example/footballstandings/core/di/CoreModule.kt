package com.example.footballstandings.core.di

import com.example.footballstandings.core.network.CompetitionsNetworkDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {
    @Provides
    @Singleton
    fun providesCompetitionsNetworkDataSource() =
        CompetitionsNetworkDataSource()
}