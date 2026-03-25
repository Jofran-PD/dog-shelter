package com.jofranpduran.dogshelter.ui.adoptablelist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AdoptableListScreen(
    modifier: Modifier = Modifier
) {
    Scaffold { paddingValues ->
        AdoptableListContent(
            modifier = modifier.padding(paddingValues)
        )
    }
}

@Composable
fun AdoptableListContent(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        Text("Dog List")
    }
}