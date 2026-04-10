package com.jofranpduran.dogshelter.data.mapper

import com.jofranpduran.dogshelter.data.local.entity.DogEntity
import com.jofranpduran.dogshelter.domain.model.Dog

fun DogEntity.asDomainModel(): Dog {
    return Dog(
        id = id,
        name = name,
        breed = breed,
        weight = weight,
        gender = gender,
        birthDate = birthDate,
        notes = notes
    )
}

fun Dog.toEntity(): DogEntity {
    return DogEntity(
        id = id,
        name = name,
        breed = breed,
        weight = weight,
        gender = gender,
        birthDate = birthDate,
        notes = notes
    )
}