package com.aguilar.chefai.data.repository

import com.aguilar.chefai.data.remote.api.TheMealDbApi
import com.aguilar.chefai.data.remote.dto.MealDto
import com.aguilar.chefai.domain.model.Recipe
import com.aguilar.chefai.domain.model.RecipeDetail
import com.aguilar.chefai.domain.repository.RecipeRepository
import com.aguilar.chefai.utils.MlKitRecipeTranslator
import com.aguilar.chefai.utils.RecipeTranslator
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class RecipeRepositoryImpl(
    private val api: TheMealDbApi,
    private val translator: RecipeTranslator = MlKitRecipeTranslator()
) : RecipeRepository {

    override suspend fun getRecipesByCategory(category: String): List<Recipe> {
        val response = api.getRecipesByCategory(category)
        return response.meals?.mapRecipesToSpanish() ?: emptyList()
    }

    override suspend fun searchRecipes(query: String): List<Recipe> {
        val englishQuery = translateToEnglish(query)
        val response = api.searchRecipesByName(englishQuery)
        return response.meals?.mapRecipesToSpanish() ?: emptyList()
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
            ingredients = ingredientsList.map { translateIngredientToSpanish(it) }
        )
    }

    private suspend fun translateToSpanish(text: String): String {
        return runCatching { translator.toSpanish(text) }.getOrElse { text }
    }

    private suspend fun translateToEnglish(text: String): String {
        return runCatching { translator.toEnglish(text) }.getOrElse { text }
    }

    private suspend fun translateIngredientToSpanish(ingredient: String): String {
        val normalizedIngredient = ingredient.trim().lowercase()
        val manualTranslation = ingredientTranslations[normalizedIngredient]
        return manualTranslation ?: translateToSpanish(ingredient)
    }

    private suspend fun List<MealDto>.mapRecipesToSpanish(): List<Recipe> {
        return coroutineScope {
            map { meal ->
                async {
                    Recipe(
                        id = meal.idMeal,
                        name = translateToSpanish(meal.strMeal),
                        imageUrl = meal.strMealThumb
                    )
                }
            }.awaitAll()
        }
    }

    private companion object {
        val ingredientTranslations = mapOf(
            "beef" to "Carne de res",
            "chicken" to "Pollo",
            "pork" to "Cerdo",
            "fish" to "Pescado",
            "salmon" to "Salmón",
            "egg" to "Huevo",
            "eggs" to "Huevos",
            "milk" to "Leche",
            "butter" to "Mantequilla",
            "flour" to "Harina",
            "sugar" to "Azúcar",
            "salt" to "Sal",
            "pepper" to "Pimienta",
            "black pepper" to "Pimienta negra",
            "olive oil" to "Aceite de oliva",
            "vegetable oil" to "Aceite vegetal",
            "water" to "Agua",
            "garlic" to "Ajo",
            "onion" to "Cebolla",
            "tomato" to "Tomate",
            "tomatoes" to "Tomates",
            "plum tomatoes" to "Tomates pera",
            "parsley" to "Perejil",
            "cilantro" to "Cilantro",
            "cumin" to "Comino",
            "paprika" to "Pimentón",
            "rice" to "Arroz",
            "potatoes" to "Papas",
            "potato" to "Papa",
            "carrot" to "Zanahoria",
            "carrots" to "Zanahorias",
            "broccoli" to "Brócoli",
            "cheese" to "Queso",
            "bread" to "Pan",
            "lemon" to "Limón",
            "lime" to "Lima",
            "mushrooms" to "Champiñones",
            "mushroom" to "Champiñón"
        )
    }
}
