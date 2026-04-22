package com.jofranpduran.dogshelter.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jofranpduran.dogshelter.ui.adddog.AddDogScreen
import com.jofranpduran.dogshelter.ui.adoptablelist.AdoptableListScreen
import com.jofranpduran.dogshelter.ui.dogdetails.DogDetailsScreen
import kotlinx.serialization.Serializable

@Serializable
object AdoptableListRoute

@Serializable
object AddDogRoute

@Serializable
data class DogDetailsRoute(val dogId: Int)

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
        composable<AdoptableListRoute> { backStackEntry ->
            val dogAddedSuccess by backStackEntry.savedStateHandle
                .getStateFlow<Boolean?>("dog_added_success", null)
                .collectAsStateWithLifecycle()

            AdoptableListScreen(
                onItemClick = { dogId ->
                    navController.navigate(DogDetailsRoute(dogId))
                },
                onAddDogClick = {
                    navController.navigate(AddDogRoute)
                },
                dogAddedSuccess = dogAddedSuccess,
                onConsumeDogAddedSuccess = {
                    backStackEntry.savedStateHandle.remove<Boolean>("dog_added_success")
                }
            )
        }

        composable<AddDogRoute> {
            AddDogScreen(
                onNavigateUp = { navController.navigateUp() },
                onDogAdded = {
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("dog_added_success", true)
                    navController.navigateUp()
                }
            )
        }

        composable<DogDetailsRoute> {
            DogDetailsScreen()
        }
    }
}