package com.jofranpduran.dogshelter.data.local.util

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DateConverters {
    private val formatter = DateTimeFormatter.ISO_DATE_TIME

    @TypeConverter
    fun fromDate(date: LocalDate): String {
        return date.format(formatter)
    }

    @TypeConverter
    fun toDate(value: String): LocalDate {
        return LocalDate.parse(value, formatter)
    }
}