package com.aguilar.chefai.data.remote.repository

import com.aguilar.chefai.data.remote.TheMealDbApi
import com.aguilar.chefai.data.translation.MlKitRecipeTranslator
import com.aguilar.chefai.data.translation.RecipeTranslator
import com.aguilar.chefai.domain.model.Recipe
import com.aguilar.chefai.domain.model.RecipeDetail
import com.aguilar.chefai.domain.repository.RecipeRepository

class RecipeRepositoryImpl(
    private val api: TheMealDbApi,
    private val translator: RecipeTranslator = MlKitRecipeTranslator()
) : RecipeRepository {

    override suspend fun getRecipesByCategory(category: String): List<Recipe> {
        val response = api.getRecipesByCategory(category)
        return response.meals?.map {
            Recipe(
                id = it.idMeal,
                name = translateToSpanish(it.strMeal),
                imageUrl = it.strMealThumb
            )
        } ?: emptyList()
    }

    override suspend fun searchRecipes(query: String): List<Recipe> {
        val englishQuery = translateToEnglish(query)
        val response = api.searchRecipesByName(englishQuery)
        return response.meals?.map {
            Recipe(
                id = it.idMeal,
                name = translateToSpanish(it.strMeal),
                imageUrl = it.strMealThumb
            )
        } ?: emptyList()
    }

    override suspend fun getRecipeDetails(id: String): RecipeDetail {
        val response = api.getRecipeDetails(id)
        val meal = response.meals?.firstOrNull() ?: throw Exception("No encontrada")

        // Juntamos todos los ingredientes que tengan texto en una sola lista limpia
        val ingredientsList = listOfNotNull(
            meal.strIngredient1, meal.strIngredient2, meal.strIngredient3,
            meal.strIngredient4, meal.strIngredient5, meal.strIngredient6,
            meal.strIngredient7, meal.strIngredient8, meal.strIngredient9,
            meal.strIngredient10, meal.strIngredient11, meal.strIngredient12,
            meal.strIngredient13, meal.strIngredient14, meal.strIngredient15
        ).filter { it.isNotBlank() }

        return RecipeDetail(
            id = meal.idMeal,
            name = translateToSpanish(meal.strMeal),
            instructions = translateToSpanish(meal.strInstructions),
            imageUrl = meal.strMealThumb,
            ingredients = ingredientsList.map { translateToSpanish(it) }
        )
    }

    private suspend fun translateToSpanish(text: String): String {
        return runCatching { translator.toSpanish(text) }.getOrElse { text }
    }

    private suspend fun translateToEnglish(text: String): String {
        return runCatching { translator.toEnglish(text) }.getOrElse { text }
    }
}
