package com.jofranpduran.dogshelter.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import com.jofranpduran.dogshelter.R
import com.jofranpduran.dogshelter.domain.model.Dog


@Composable
fun rememberDogAgeDisplay(dog: Dog): String {
    val (years, months) = remember(dog.id, dog.birthDate) {
        dog.ageYears to dog.ageMonths
    }

    return when {
        years == 0 && months == 0 -> stringResource(R.string.age_newborn)
        years > 0 && months > 0 -> {
            val yearsString = pluralStringResource(R.plurals.age_years, years, years)
            val monthsString = pluralStringResource(R.plurals.age_months, months, months)
            stringResource(R.string.age_combined, yearsString, monthsString)
        }

        years > 0 -> {
            val yearsString = pluralStringResource(R.plurals.age_years, years, years)
            stringResource(R.string.age_single, yearsString)
        }

        else -> {
            val monthsString = pluralStringResource(R.plurals.age_months, months, months)
            stringResource(R.string.age_single, monthsString)
        }
    }
}