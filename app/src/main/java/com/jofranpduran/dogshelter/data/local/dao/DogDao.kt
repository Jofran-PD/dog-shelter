package com.jofranpduran.dogshelter.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jofranpduran.dogshelter.data.local.entity.DogEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDog(dog: DogEntity) : Long

    @Query("SELECT * FROM dog")
    fun getAllDogs(): Flow<List<DogEntity>>

    @Update
    suspend fun updateDog(dog: DogEntity): Int

    @Delete
    suspend fun deleteDog(dog: DogEntity): Int

    @Query("SELECT * FROM dog WHERE id = :id")
    fun getDogByID(id: Int) : Flow<DogEntity?>
}