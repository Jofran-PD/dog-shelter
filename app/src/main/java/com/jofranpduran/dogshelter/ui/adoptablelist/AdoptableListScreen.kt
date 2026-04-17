package com.jofranpduran.dogshelter.ui.adoptablelist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jofranpduran.dogshelter.domain.model.Dog
import com.jofranpduran.dogshelter.domain.model.Gender
import java.time.LocalDate

@Composable
fun AdoptableListScreen(
    modifier: Modifier = Modifier,
    viewModel: AdoptableListViewModel = hiltViewModel(),
    onItemClick: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (val state = uiState) {
                is AdoptableListUiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                is AdoptableListUiState.Error -> {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = "Error: ${state.message}"
                    )
                }

                is AdoptableListUiState.Success -> {
                    if (state.dogs.isEmpty()) {
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = "No pets to show."
                        )
                    } else {
                        AdoptableListContent(
                            dogs = state.dogs,
                            onItemClick = onItemClick
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AdoptableListContent(
    modifier: Modifier = Modifier,
    dogs: List<Dog>,
    onItemClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(dogs) { dog ->
            DogListItem(
                dog = dog,
                onItemClick = onItemClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AdoptableListContentPreview() {
    val dogs = listOf(
        Dog(
            id = 1,
            name = "Rex",
            breed = "German Shepherd",
            weight = 30,
            gender = Gender.MALE,
            birthDate = LocalDate.now().minusYears(2),
            notes = "Friendly and energetic"
        ),
        Dog(
            id = 2,
            name = "Bella",
            breed = "Golden Retriever",
            weight = 25,
            gender = Gender.FEMALE,
            birthDate = LocalDate.now().minusYears(3),
            notes = "Loves to play fetch"
        ),
        Dog(
            id = 3,
            name = "Max",
            breed = "Beagle",
            weight = 15,
            gender = Gender.MALE,
            birthDate = LocalDate.now().minusYears(1),
            notes = "Great sense of smell"
        )
    )
    AdoptableListContent(dogs = dogs, onItemClick = {})
}
