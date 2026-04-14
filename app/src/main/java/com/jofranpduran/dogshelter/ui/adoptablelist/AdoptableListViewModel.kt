package com.jofranpduran.dogshelter.ui.adoptablelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jofranpduran.dogshelter.domain.PetsRepository
import com.jofranpduran.dogshelter.domain.model.Dog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

sealed interface AdoptableListUiState {
    data object Loading : AdoptableListUiState
    data class Success(val dogs: List<Dog>) : AdoptableListUiState
    data class Error(val message: String) : AdoptableListUiState
}

@HiltViewModel
class AdoptableListViewModel @Inject constructor(
    repository: PetsRepository
) : ViewModel() {
    val uiState: StateFlow<AdoptableListUiState> = repository.getAllDogs()
        .map<List<Dog>, AdoptableListUiState> { dogs ->
            AdoptableListUiState.Success(dogs)
        }
        .catch { e ->
            emit(AdoptableListUiState.Error(e.message ?: "Unknown error"))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = AdoptableListUiState.Loading
        )
}