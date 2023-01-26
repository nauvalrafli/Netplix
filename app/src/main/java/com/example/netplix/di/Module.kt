package com.example.netplix.di

import com.example.netplix.data.repo.BaseRepo
import com.example.netplix.data.repo.TMDBRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Singleton
    @Provides
    fun provideBaseRepo() : BaseRepo = BaseRepo()

    @Singleton
    @Provides
    fun provideTMDBRepo(baseRepo: BaseRepo) = TMDBRepo(baseRepo)

}

