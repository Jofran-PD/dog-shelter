package com.jofranpduran.dogshelter.ui.dogdetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.material.icons.filled.MonitorWeight
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jofranpduran.dogshelter.domain.model.Dog
import com.jofranpduran.dogshelter.domain.model.Gender
import com.jofranpduran.dogshelter.ui.common.rememberDogAgeDisplay
import com.jofranpduran.dogshelter.ui.theme.DogShelterTheme
import java.time.LocalDate

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
                    DogDetailsScreenContent(dog = state.dog)

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
    dog: Dog
) {
    val ageString = rememberDogAgeDisplay(dog)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = dog.name,
            style = MaterialTheme.typography.displayLarge,
            fontWeight = FontWeight.W500
        )

        Spacer(modifier = Modifier.height(16.dp))

        DogInfoGrid(dog = dog, ageString = ageString)

        if (dog.notes.isNotBlank()) {
            Spacer(modifier = Modifier.height(16.dp))
            NotesCard(notes = dog.notes)
        }
    }

}

@Composable
fun NotesCard(
    modifier: Modifier = Modifier,
    notes: String
) {
    ElevatedCard(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Description,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = notes,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun DogInfoGrid(
    modifier: Modifier = Modifier,
    dog: Dog,
    ageString: String
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            InfoCard(
                modifier = Modifier.weight(1f),
                icon = Icons.Default.Pets,
                value = dog.breed
            )
            InfoCard(
                modifier = Modifier.weight(1f),
                icon = if (dog.gender == Gender.MALE) Icons.Default.Male else Icons.Default.Female,
                value = dog.gender.name
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            InfoCard(
                modifier = Modifier.weight(1f),
                icon = Icons.Default.Cake,
                value = ageString
            )
            InfoCard(
                modifier = Modifier.weight(1f),
                icon = Icons.Default.MonitorWeight,
                value = "${dog.weight} kg"
            )
        }
    }
}

@Composable
fun InfoCard(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    value: String
) {
    ElevatedCard(
        modifier = modifier.aspectRatio(1.5f)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DogDetailsScreenContentPreview() {
    DogShelterTheme {
        DogDetailsScreenContent(
            dog = Dog(
                id = 1,
                name = "Rex",
                breed = "German Shepherd",
                weight = 30,
                gender = Gender.MALE,
                birthDate = LocalDate.now().minusMonths(26),
                notes = "Friendly and energetic. Rex loves to play fetch and is very good with children. He has been vaccinated and is ready for a new home. He is a very loyal companion."
            )
        )
    }
}