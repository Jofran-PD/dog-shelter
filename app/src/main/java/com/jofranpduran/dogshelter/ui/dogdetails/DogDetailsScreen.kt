package com.jofranpduran.dogshelter.ui.dogdetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
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
        ) {
            when (val state = uiState) {
                DogDetailsUiState.Loading ->
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(innerPadding)
                            .align(Alignment.Center)
                    )

                is DogDetailsUiState.Error ->
                    Text(
                        modifier = Modifier
                            .padding(innerPadding)
                            .align(Alignment.Center),
                        text = "Error: ${state.message}"
                    )

                is DogDetailsUiState.Success ->
                    DogDetailsScreenContent(
                        dog = state.dog,
                        contentPadding = innerPadding
                    )

                DogDetailsUiState.NotFound ->
                    Text(
                        modifier = modifier
                            .padding(innerPadding)
                            .align(Alignment.Center),
                        text = "Dog not found."
                    )
            }
        }
    }
}

@Composable
fun DogDetailsScreenContent(
    modifier: Modifier = Modifier,
    dog: Dog,
    contentPadding: PaddingValues = PaddingValues()
) {
    val ageString = rememberDogAgeDisplay(dog)
    val configuration = LocalWindowInfo.current
    val screenWidth = configuration.containerDpSize.width
    val imageHeight = screenWidth * 4f / 3f

    // InfoCard aspect ratio is 1.5. In a row of 2 with 16dp padding on sides and 16dp between cards:
    // cardWidth = (screenWidth - 16.dp * 2 - 16.dp) / 2
    // cardHeight = cardWidth / 1.5
    val cardWidth = (screenWidth - 48.dp) / 2
    val cardHeight = cardWidth / 1.5f
    val halfCardHeight = cardHeight / 2

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = contentPadding.calculateBottomPadding())
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            DogImage(
                imageUri = dog.imageUri,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(imageHeight)
            )

            Text(
                text = dog.name,
                style = MaterialTheme.typography.displayLarge,
                fontWeight = FontWeight.W500,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f))
                        )
                    )
                    .padding(horizontal = 16.dp)
                    .padding(bottom = halfCardHeight + 16.dp, top = 32.dp)
            )
        }

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .offset(y = -halfCardHeight)
        ) {
            DogInfoGrid(
                dog = dog,
                ageString = ageString
            )

            if (dog.notes.isNotBlank()) {
                Spacer(modifier = Modifier.height(16.dp))
                NotesCard(notes = dog.notes)
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun DogImage(
    imageUri: String,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUri)
            .crossfade(true)
            .build(),
        contentDescription = null,
        modifier = modifier,
        contentScale = ContentScale.Crop,
        error = rememberVectorPainter(image = Icons.Default.Pets)
    )
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
                name = "Rex the Faithful Guardian",
                breed = "German Shepherd",
                weight = 30,
                gender = Gender.MALE,
                birthDate = LocalDate.now().minusMonths(26),
                notes = "Friendly and energetic. Rex loves to play fetch and is very good with children. He has been vaccinated and is ready for a new home. He is a very loyal companion."
            )
        )
    }
}