package com.jofranpduran.dogshelter.data.local.util

import androidx.room.TypeConverter
import com.jofranpduran.dogshelter.domain.model.Gender

class GenderConverters {

    @TypeConverter
    fun fromGender(gender: Gender): String {
        return gender.name
    }

    @TypeConverter
    fun toGender(value: String): Gender {
        return try {
            Gender.valueOf(value)
        } catch (e: IllegalArgumentException) {
            Gender.UNKNOWN
        }
    }
}