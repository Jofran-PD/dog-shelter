package com.jofranpduran.dogshelter.di

import com.jofranpduran.dogshelter.data.PetsRepositoryImpl
import com.jofranpduran.dogshelter.domain.PetsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindsPetsRepository(
        impl: PetsRepositoryImpl
    ): PetsRepository
}