package com.aguilar.chefai.data.remote.repository

import com.aguilar.chefai.data.remote.TheMealDbApi
import com.aguilar.chefai.domain.model.Recipe
import com.aguilar.chefai.domain.repository.RecipeRepository

class RecipeRepositoryImpl(
    private val api: TheMealDbApi
) : RecipeRepository {

    override suspend fun getRecipesByCategory(category: String): List<Recipe> {
        val response = api.getRecipesByCategory(category)
        // Convertimos el DTO de internet a nuestra clase Recipe limpia
        return response.meals?.map { meal ->
            Recipe(
                id = meal.idMeal,
                name = meal.strMeal,
                imageUrl = meal.strMealThumb
            )
        } ?: emptyList()
    }
}