package com.jofranpduran.dogshelter.domain.repository

import kotlinx.coroutines.flow.Flow

interface ImageAnalysisRepository {
    suspend fun getDogBreedFromImage(imageUri: String): Result<String>
    fun generateDogNotes(imageUri: String, breed: String): Flow<String>
}