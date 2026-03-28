package com.jofranpduran.dogshelter.domain.model

import java.time.LocalDate
import java.time.Period

data class Dog(
    val id: Int,
    val name: String,
    val breed: String,
    val weight: Int,
    val gender: Gender,
    val birthDate: LocalDate,
    val notes: String
) {
    private val period: Period
        get() = Period.between(birthDate, LocalDate.now())

    val ageYears: Int
        get() = period.years

    val ageMonths: Int
        get() = period.months
}
