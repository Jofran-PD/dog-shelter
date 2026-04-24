package com.jofranpduran.dogshelter.ui.adddog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jofranpduran.dogshelter.domain.model.Gender
import com.jofranpduran.dogshelter.ui.theme.DogShelterTheme
import com.jofranpduran.dogshelter.ui.theme.FemalePink
import com.jofranpduran.dogshelter.ui.theme.MaleBlue
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun AddDogScreen(
    onNavigateUp: () -> Unit,
    onDogAdded: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AddDogViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.isSaved) {
        if (uiState.isSaved) {
            onDogAdded()
        }
    }

    AddDogContent(
        uiState = uiState,
        onNavigateUp = onNavigateUp,
        onNameChange = viewModel::onNameChange,
        onBreedChange = viewModel::onBreedChange,
        onWeightChange = viewModel::onWeightChange,
        onGenderChange = viewModel::onGenderChange,
        onBirthDateChange = viewModel::onBirthDateChange,
        onNotesChange = viewModel::onNotesChange,
        onSaveClick = viewModel::saveDog,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDogContent(
    uiState: AddDogUiState,
    onNavigateUp: () -> Unit,
    onNameChange: (String) -> Unit,
    onBreedChange: (String) -> Unit,
    onWeightChange: (String) -> Unit,
    onGenderChange: (Gender) -> Unit,
    onBirthDateChange: (LocalDate) -> Unit,
    onNotesChange: (String) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Add New Dog") },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    SaveDogButton(
                        onClick = onSaveClick,
                        enabled = uiState.isFormValid && !uiState.isSaving,
                        isSaving = uiState.isSaving,
                        modifier = Modifier.padding(end = 12.dp)
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            AddDogTextField(
                label = "Name",
                value = uiState.name,
                onValueChange = onNameChange
            )

            Spacer(modifier = Modifier.size(16.dp))

            AddDogTextField(
                label = "Breed",
                value = uiState.breed,
                onValueChange = onBreedChange
            )

            Spacer(modifier = Modifier.size(16.dp))

            AddDogTextField(
                label = "Weight (lb)",
                value = uiState.weight,
                onValueChange = onWeightChange,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.size(16.dp))

            GenderSelector(
                selectedGender = uiState.gender,
                onGenderChange = onGenderChange
            )

            Spacer(modifier = Modifier.size(16.dp))

            DogDatePickerField(
                label = "Birth Date",
                selectedDate = uiState.birthDate,
                onDateSelected = onBirthDateChange
            )

            Spacer(modifier = Modifier.size(16.dp))

            AddDogTextField(
                label = "Notes (Optional)",
                value = uiState.notes,
                onValueChange = onNotesChange,
                minLines = 3,
                singleLine = false
            )

            if (uiState.errorMessage != null) {
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = uiState.errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
    }
}

@Composable
fun SaveDogButton(
    onClick: () -> Unit,
    enabled: Boolean,
    isSaving: Boolean,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier,
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.38f),
            disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.38f)
        )
    ) {
        if (isSaving) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                strokeWidth = 2.dp,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        } else {
            Icon(Icons.Default.Check, contentDescription = "Save")
        }
    }
}

@Composable
fun AddDogTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    readOnly: Boolean = false,
    trailingIcon: @Composable (() -> Unit)? = null,
    minLines: Int = 1,
    singleLine: Boolean = true
) {
    Column(modifier = modifier) {
        AddDogLabel(text = label)
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = onValueChange,
            singleLine = singleLine,
            minLines = minLines,
            readOnly = readOnly,
            keyboardOptions = keyboardOptions,
            trailingIcon = trailingIcon,
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
                focusedBorderColor = MaterialTheme.colorScheme.primary
            )
        )
    }
}


@Composable
fun AddDogLabel(
    text: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.size(4.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenderSelector(
    selectedGender: Gender,
    onGenderChange: (Gender) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        AddDogLabel(text = "Gender")
        val genders = Gender.entries.filter { it != Gender.UNKNOWN }
        SingleChoiceSegmentedButtonRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            genders.forEachIndexed { index, gender ->
                val isSelected = selectedGender == gender
                val color = when (gender) {
                    Gender.MALE -> MaleBlue
                    Gender.FEMALE -> FemalePink
                    else -> MaterialTheme.colorScheme.primary
                }
                SegmentedButton(
                    shape = SegmentedButtonDefaults.itemShape(
                        index = index,
                        count = genders.size,
                        baseShape = RoundedCornerShape(16.dp)
                    ),
                    onClick = { onGenderChange(gender) },
                    selected = isSelected,
                    colors = SegmentedButtonDefaults.colors(
                        activeContainerColor = color,
                        activeContentColor = MaterialTheme.colorScheme.onPrimary,
                        inactiveContainerColor = MaterialTheme.colorScheme.surface,
                        inactiveBorderColor = MaterialTheme.colorScheme.outlineVariant,
                        activeBorderColor = color
                    )
                ) {
                    Text(gender.name.lowercase().replaceFirstChar { it.uppercase() })
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DogDatePickerField(
    label: String,
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {
    var showDatePicker by remember { mutableStateOf(false) }

    AddDogTextField(
        label = label,
        value = selectedDate.format(DateTimeFormatter.ofPattern("MMM dd, yyyy")),
        onValueChange = {},
        readOnly = true,
        trailingIcon = {
            IconButton(onClick = { showDatePicker = true }) {
                Icon(Icons.Default.DateRange, contentDescription = "Select Date")
            }
        },
        modifier = modifier
    )

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = selectedDate
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli()
        )

        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val date = Instant.ofEpochMilli(millis)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                        onDateSelected(date)
                    }
                    showDatePicker = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}



@Preview(showBackground = true)
@Composable
fun AddDogScreenPreview() {
    DogShelterTheme {
        AddDogContent(
            uiState = AddDogUiState(
                name = "Rex",
                breed = "German Shepherd",
                weight = "70",
                gender = Gender.MALE,
                birthDate = LocalDate.now().minusYears(2),
                errorMessage = "Unknown error."
            ),
            onNavigateUp = {},
            onNameChange = {},
            onBreedChange = {},
            onWeightChange = {},
            onGenderChange = {},
            onBirthDateChange = {},
            onNotesChange = {},
            onSaveClick = {}
        )
    }
}
