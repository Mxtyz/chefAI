package com.aguilar.chefai.domain.repository

import com.aguilar.chefai.domain.model.Recipe
import com.aguilar.chefai.domain.model.RecipeDetail

interface RecipeRepository {
    suspend fun getRecipesByCategory(category: String): List<Recipe>
    suspend fun searchRecipes(query: String): List<Recipe> // Para la lupa
    suspend fun getRecipeDetails(id: String): RecipeDetail // Para los detalles
}