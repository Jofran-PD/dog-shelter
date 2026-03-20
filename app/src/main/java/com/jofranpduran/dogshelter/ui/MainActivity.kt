package com.jofranpduran.dogshelter.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import com.jofranpduran.dogshelter.ui.theme.DogShelterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DogShelterTheme {
                DogShelter()
            }
        }
    }
}

@Composable
fun DogShelter() {
    Navigation()
}