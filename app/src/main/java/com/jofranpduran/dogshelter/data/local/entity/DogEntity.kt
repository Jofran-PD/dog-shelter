package com.jofranpduran.dogshelter.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jofranpduran.dogshelter.domain.model.Gender
import java.time.LocalDate

@Entity(tableName = "dog")
data class DogEntity(
    @PrimaryKey(autoGenerate = true) 
    val id: Int = 0,
    val name: String,
    val breed: String,
    val weight: Int,
    val gender: Gender,
    @ColumnInfo(name = "birth_date")
    val birthDate: LocalDate,
    val notes: String,
    @ColumnInfo(name = "image_uri")
    val imageUri: String = ""
)
