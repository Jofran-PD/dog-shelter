package com.jofranpduran.dogshelter.domain.repository

import com.jofranpduran.dogshelter.domain.model.Dog
import kotlinx.coroutines.flow.Flow

interface PetsRepository {
    fun getAllDogs() : Flow<List<Dog>>
    fun getDogById(id: Int): Flow<Dog?>
    suspend fun insertDog(dog: Dog): Long
}