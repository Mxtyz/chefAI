package com.aguilar.chefai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aguilar.chefai.data.remote.RetrofitClient
import com.aguilar.chefai.data.remote.repository.RecipeRepositoryImpl
import com.aguilar.chefai.domain.usecase.GetRecipeDetailUseCase
import com.aguilar.chefai.domain.usecase.GetRecipesByCategoryUseCase
import com.aguilar.chefai.presentation.recipes.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val api = RetrofitClient.api
        val repository = RecipeRepositoryImpl(api)

        val getRecipesUseCase = GetRecipesByCategoryUseCase(repository)
        val getRecipeDetailUseCase = GetRecipeDetailUseCase(repository)

        setContent {
            val navController = rememberNavController()

            // START DESTINATION AHORA ES SPLASH
            NavHost(navController = navController, startDestination = "splash") {

                // 1. Pantalla Estética de entrada
                composable("splash") {
                    SplashScreen(onTimeout = {
                        // Navegamos eliminando la pantalla de bienvenida del historial
                        navController.navigate("recipe_list") {
                            popUpTo("splash") { inclusive = true }
                        }
                    })
                }

                // 2. Lista de recetas con Lupa
                composable("recipe_list") {
                    val listViewModel = remember {
                        RecipeListViewModel(getRecipesUseCase, repository)
                    }
                    RecipeListScreen(
                        viewModel = listViewModel,
                        onRecipeClick = { recipeId ->
                            navController.navigate("recipe_detail/$recipeId")
                        }
                    )
                }

                // 3. Detalles (Ingredientes y pasos)
                composable("recipe_detail/{recipeId}") { backStackEntry ->
                    val recipeId = backStackEntry.arguments?.getString("recipeId") ?: ""
                    val detailViewModel = remember(recipeId) {
                        RecipeDetailViewModel(getRecipeDetailUseCase, recipeId)
                    }
                    RecipeDetailScreen(viewModel = detailViewModel)
                }
            }
        }
    }
}
