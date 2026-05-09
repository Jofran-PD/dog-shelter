package com.jofranpduran.dogshelter.domain.repository

interface ImageAnalysisRepository {
    suspend fun getDogBreedFromImage(imageUri: String): Result<String>
}