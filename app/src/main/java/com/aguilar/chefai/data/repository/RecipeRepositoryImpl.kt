package com.aguilar.chefai.data.repository

import com.aguilar.chefai.data.remote.TheMealDbApi
import com.aguilar.chefai.domain.model.Recipe
import com.aguilar.chefai.domain.repository.RecipeRepository

class RecipeRepositoryImpl(
    private val api: TheMealDbApi
) : RecipeRepository {

    override suspend fun getRecipesByCategory(category: String): List<Recipe> {
        val response = api.getRecipesByCategory(category)
        return response.meals?.map { meal -> meal.toDomain() } ?: emptyList()
    }
}
