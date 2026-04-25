package com.jofranpduran.dogshelter.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.jofranpduran.dogshelter.data.local.dao.DogDao
import com.jofranpduran.dogshelter.data.local.entity.DogEntity
import com.jofranpduran.dogshelter.data.local.util.DateConverters
import com.jofranpduran.dogshelter.data.local.util.GenderConverters

@Database(entities = [DogEntity::class], version = 2)
@TypeConverters(DateConverters::class, GenderConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dogDao(): DogDao

    companion object {
        val MIGRATION_1_2 = object : Migration(1, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE dog ADD COLUMN image_uri TEXT")
            }
        }
    }
}