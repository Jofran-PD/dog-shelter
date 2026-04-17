package com.jofranpduran.dogshelter.ui.dogdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.jofranpduran.dogshelter.ui.DogDetailsRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

sealed interface DogDetailsUiState {
    data object Loading : DogDetailsUiState
    data class Success(val dogId: Int) : DogDetailsUiState
    data class Error(val message: String) : DogDetailsUiState
    data object NotFound : DogDetailsUiState
}

@HiltViewModel
class DogDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val dogId = savedStateHandle.toRoute<DogDetailsRoute>().dogId

    val uiState: StateFlow<DogDetailsUiState> =
        MutableStateFlow(DogDetailsUiState.Success(dogId))
}