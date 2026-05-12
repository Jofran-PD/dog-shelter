package com.jofranpduran.dogshelter.di

import com.jofranpduran.dogshelter.data.repository.ImageAnalysisRepositoryImpl
import com.jofranpduran.dogshelter.domain.repository.ImageAnalysisRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ImageAnalysisModule {
    @Binds
    @Singleton
    abstract fun bindsImageAnalysisRepository(
        impl: ImageAnalysisRepositoryImpl
    ) : ImageAnalysisRepository
}