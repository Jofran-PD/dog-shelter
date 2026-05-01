package com.jofranpduran.dogshelter.ui.adddog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jofranpduran.dogshelter.domain.repository.PetsRepository
import com.jofranpduran.dogshelter.domain.model.Dog
import com.jofranpduran.dogshelter.domain.model.Gender
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

data class AddDogUiState(
    val name: String = "",
    val breed: String = "",
    val weight: String = "",
    val gender: Gender = Gender.MALE,
    val birthDate: LocalDate = LocalDate.now(),
    val notes: String = "",
    val imageUri: String = "",
    val isSaving: Boolean = false,
    val isSaved: Boolean = false,
    val errorMessage: String? = null
) {
    val isFormValid: Boolean
        get() = name.isNotBlank() && breed.isNotBlank() && weight.isNotBlank() && weight.toIntOrNull() != null
}

@HiltViewModel
class AddDogViewModel @Inject constructor(
    private val repository: PetsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddDogUiState())
    val uiState: StateFlow<AddDogUiState> = _uiState.asStateFlow()

    fun onNameChange(name: String) {
        _uiState.update { it.copy(name = name) }
    }

    fun onBreedChange(breed: String) {
        _uiState.update { it.copy(breed = breed) }
    }

    fun onWeightChange(weight: String) {
        _uiState.update { it.copy(weight = weight) }
    }

    fun onGenderChange(gender: Gender) {
        _uiState.update { it.copy(gender = gender) }
    }

    fun onBirthDateChange(date: LocalDate) {
        _uiState.update { it.copy(birthDate = date) }
    }

    fun onNotesChange(notes: String) {
        _uiState.update { it.copy(notes = notes) }
    }

    fun onDogPhotoChange(path: String) {
        _uiState.update { it.copy(imageUri = path) }
    }

    fun saveDog() {
        val currentState = _uiState.value
        if (currentState.isFormValid) {
            viewModelScope.launch {
                _uiState.update { it.copy(isSaving = true, errorMessage = null) }
                try {
                    val dog = Dog(
                        id = 0,
                        name = currentState.name,
                        breed = currentState.breed,
                        weight = currentState.weight.toInt(),
                        gender = currentState.gender,
                        birthDate = currentState.birthDate,
                        notes = currentState.notes,
                        imageUri = currentState.imageUri
                    )
                    repository.insertDog(dog)
                    _uiState.update { it.copy(isSaved = true) }
                } catch (e: Exception) {
                    _uiState.update { it.copy(errorMessage = e.message ?: "Failed to save dog") }
                } finally {
                    _uiState.update { it.copy(isSaving = false) }
                }
            }
        }
    }
}
