package com.jofranpduran.dogshelter.ui.dogdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.jofranpduran.dogshelter.domain.repository.PetsRepository
import com.jofranpduran.dogshelter.domain.model.Dog
import com.jofranpduran.dogshelter.ui.DogDetailsRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

sealed interface DogDetailsUiState {
    data object Loading : DogDetailsUiState
    data class Success(val dog: Dog) : DogDetailsUiState
    data class Error(val message: String) : DogDetailsUiState
    data object NotFound : DogDetailsUiState
}

@HiltViewModel
class DogDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    repository: PetsRepository
) : ViewModel() {
    val dogId = savedStateHandle.toRoute<DogDetailsRoute>().dogId

    val uiState: StateFlow<DogDetailsUiState> = repository.getDogById(dogId)
        .map { dog ->
            if (dog != null) {
                DogDetailsUiState.Success(dog)
            } else {
                DogDetailsUiState.NotFound
            }
        }.catch { e ->
            emit(DogDetailsUiState.Error(e.message ?: "Unknown error."))
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = DogDetailsUiState.Loading
        )
}