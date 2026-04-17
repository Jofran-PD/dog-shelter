package com.jofranpduran.dogshelter.ui.dogdetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun DogDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: DogDetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier
    ) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (val state = uiState) {
                DogDetailsUiState.Loading ->
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )

                is DogDetailsUiState.Error ->
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = "Error: ${state.message}"
                    )

                is DogDetailsUiState.Success ->
                    DogDetailsScreenContent(dogId = state.dogId)

                DogDetailsUiState.NotFound ->
                    Text(
                        modifier = modifier.align(Alignment.Center),
                        text = "Dog not found."
                    )
            }
        }
    }
}

@Composable
fun DogDetailsScreenContent(
    modifier: Modifier = Modifier,
    dogId: Int
) {
    Column(
        modifier = modifier.fillMaxSize().padding(16.dp)
    ) {
        Text(
            text = "$dogId",
            style = MaterialTheme.typography.displayLarge,
            fontWeight = FontWeight.W500
        )
    }

}

@Preview(showBackground = true)
@Composable
fun DogDetailsScreenContentPreview() {
    DogDetailsScreenContent(
        dogId = 56
    )
}