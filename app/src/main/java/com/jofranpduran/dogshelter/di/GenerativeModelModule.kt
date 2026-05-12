package com.jofranpduran.dogshelter.di

import com.google.firebase.Firebase
import com.google.firebase.ai.GenerativeModel
import com.google.firebase.ai.ai
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GenerativeModelModule {

    @Provides
    @Singleton
    fun provideGenerativeModel(): GenerativeModel {
        return Firebase.ai.generativeModel("gemini-3.1-flash-lite")
    }
}