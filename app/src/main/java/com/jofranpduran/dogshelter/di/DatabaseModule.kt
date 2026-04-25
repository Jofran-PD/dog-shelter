package com.jofranpduran.dogshelter.di

import android.content.Context
import androidx.room.Room
import com.jofranpduran.dogshelter.data.local.AppDatabase
import com.jofranpduran.dogshelter.data.local.dao.DogDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ) : AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "dog_shelter_db"
        )
            .addMigrations(AppDatabase.MIGRATION_1_2)
            .build()
    }

    @Provides
    fun provideDogDao(database: AppDatabase): DogDao {
        return database.dogDao()
    }
}
