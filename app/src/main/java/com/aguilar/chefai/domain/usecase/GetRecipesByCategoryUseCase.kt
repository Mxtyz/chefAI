package com.aguilar.chefai.domain.usecase

import com.aguilar.chefai.domain.model.Recipe
import com.aguilar.chefai.domain.repository.RecipeRepository

/**
 * Caso de Uso siguiendo el Principio de Responsabilidad Única (SRP).
 * Se encarga exclusivamente de orquestar la obtención de recetas por categoría.
 */
class GetRecipesByCategoryUseCase(
    private val repository: RecipeRepository
) {
    suspend operator fun invoke(category: String): List<Recipe> {
        return repository.getRecipesByCategory(category)
    }
}
