package com.aguilar.chefai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aguilar.chefai.data.remote.RetrofitClient
import com.aguilar.chefai.data.repository.RecipeRepositoryImpl
import com.aguilar.chefai.domain.usecase.GetRecipesByCategoryUseCase
import com.aguilar.chefai.presentation.recipes.RecipeDetailScreen
import com.aguilar.chefai.presentation.recipes.RecipeListScreen
import com.aguilar.chefai.presentation.recipes.RecipeListViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val api = RetrofitClient.api
        val repository = RecipeRepositoryImpl(api)
        val useCase = GetRecipesByCategoryUseCase(repository)
        val viewModel = RecipeListViewModel(useCase)

        setContent {
            // 1. Creamos el controlador de navegación
            val navController = rememberNavController()

            // 2. Definimos las rutas (el mapa)
            NavHost(navController = navController, startDestination = "recipe_list") {

                // Pantalla 1: La lista
                composable("recipe_list") {
                    // Le pasamos una función que le dice qué hacer al hacer clic
                    RecipeListScreen(
                        viewModel = viewModel,
                        onRecipeClick = { recipeName ->
                            // Navegamos a la pantalla 2 pasando el nombre
                            navController.navigate("recipe_detail/$recipeName")
                        }
                    )
                }

                // Pantalla 2: Los detalles
                composable("recipe_detail/{recipeName}") { backStackEntry ->
                    // Recuperamos el nombre que mandamos por la ruta
                    val recipeName = backStackEntry.arguments?.getString("recipeName") ?: "Receta Desconocida"
                    RecipeDetailScreen(recipeName = recipeName)
                }
            }
        }
    }
}
