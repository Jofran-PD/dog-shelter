package com.jofranpduran.dogshelter.ui.adoptablelist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jofranpduran.dogshelter.domain.model.Dog
import com.jofranpduran.dogshelter.domain.model.Gender
import com.jofranpduran.dogshelter.ui.common.rememberDogAgeDisplay
import java.time.LocalDate


@Composable
fun DogListItem(
    modifier: Modifier = Modifier, dog: Dog
) {
    val ageString = rememberDogAgeDisplay(dog)

    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 16.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = dog.name,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = dog.breed,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = ageString,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DogListItemPreview() {
    DogListItem(
        dog = Dog(
            id = 1,
            name = "Rex",
            breed = "Nova Scotia Duck Tolling Retriever",
            weight = 30,
            gender = Gender.FEMALE,
            birthDate = LocalDate.now().minusMonths(56),
            notes = "Very friendly"
        )
    )
}
