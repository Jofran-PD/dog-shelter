package com.jofranpduran.dogshelter.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jofranpduran.dogshelter.ui.theme.DogShelterTheme

@Composable
fun PrimaryButton(
    text: String,
    icon: Painter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled && !loading,
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.38f),
            disabledContentColor = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.38f)
        )
    ) {
        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier.size(18.dp),
                strokeWidth = 2.dp,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        } else {
            Icon(
                painter = icon,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
        }
        Spacer(modifier = Modifier.size(8.dp))
        Text(text = text)
    }
}

@Preview(showBackground = true)
@Composable
fun PrimaryButtonPreview() {
    DogShelterTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            PrimaryButton(
                text = "Primary Button",
                icon = rememberVectorPainter(Icons.Filled.Pets),
                onClick = {}
            )
            Spacer(modifier = Modifier.size(16.dp))
            PrimaryButton(
                text = "Disabled Button",
                icon = rememberVectorPainter(Icons.Filled.Pets),
                onClick = {},
                enabled = false
            )
            Spacer(modifier = Modifier.size(16.dp))
            PrimaryButton(
                text = "Loading Button",
                icon = rememberVectorPainter(Icons.Filled.Pets),
                onClick = {},
                loading = true
            )
        }
    }
}
