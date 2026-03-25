package com.jofranpduran.dogshelter.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jofranpduran.dogshelter.ui.adoptablelist.AdoptableListScreen
import kotlinx.serialization.Serializable

@Serializable
object AdoptableListRoute

@Composable
fun Navigation(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        modifier = modifier.fillMaxSize(),
        navController = navController,
        startDestination = AdoptableListRoute
    ) {
        composable<AdoptableListRoute> {
            AdoptableListScreen()
        }
    }
}