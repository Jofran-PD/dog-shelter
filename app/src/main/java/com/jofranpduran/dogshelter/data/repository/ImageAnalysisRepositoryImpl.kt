package com.jofranpduran.dogshelter.data.repository

import com.jofranpduran.dogshelter.domain.repository.ImageAnalysisRepository
import javax.inject.Inject

class ImageAnalysisRepositoryImpl @Inject constructor() : ImageAnalysisRepository {
    override suspend fun getDogBreedFromImage(imageUri: String): Result<String> {
        return Result.success("German Shepperd")
    }
}