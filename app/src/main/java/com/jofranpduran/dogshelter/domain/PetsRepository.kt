package com.jofranpduran.dogshelter.domain

import com.jofranpduran.dogshelter.domain.model.Dog
import kotlinx.coroutines.flow.Flow

interface PetsRepository {
    fun getAllDogs() : Flow<List<Dog>>
}