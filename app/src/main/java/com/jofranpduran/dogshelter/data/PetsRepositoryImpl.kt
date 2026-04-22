package com.jofranpduran.dogshelter.data

import com.jofranpduran.dogshelter.data.local.dao.DogDao
import com.jofranpduran.dogshelter.data.mapper.asDomainModel
import com.jofranpduran.dogshelter.data.mapper.toEntity
import com.jofranpduran.dogshelter.domain.PetsRepository
import com.jofranpduran.dogshelter.domain.model.Dog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PetsRepositoryImpl @Inject constructor(
    private val dogDao: DogDao
) : PetsRepository {
    override fun getAllDogs(): Flow<List<Dog>> {
        return dogDao.getAllDogs().map { list ->
            list.map { it.asDomainModel() }
        }
    }

    override fun getDogById(id: Int): Flow<Dog?> {
        return dogDao.getDogByID(id).map { it?.asDomainModel() }
    }

    override suspend fun insertDog(dog: Dog): Long {
        return dogDao.insertDog(dog.toEntity())
    }
}