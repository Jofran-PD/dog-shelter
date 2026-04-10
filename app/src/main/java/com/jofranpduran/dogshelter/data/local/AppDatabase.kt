package com.jofranpduran.dogshelter.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jofranpduran.dogshelter.data.local.dao.DogDao
import com.jofranpduran.dogshelter.data.local.entity.DogEntity
import com.jofranpduran.dogshelter.data.local.util.DateConverters
import com.jofranpduran.dogshelter.data.local.util.GenderConverters

@Database(entities = [DogEntity::class], version = 1)
@TypeConverters(DateConverters::class, GenderConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dogDao(): DogDao
}