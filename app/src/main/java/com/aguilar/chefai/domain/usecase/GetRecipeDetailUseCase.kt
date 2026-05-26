package com.aguilar.chefai.domain.usecase

import com.aguilar.chefai.domain.model.RecipeDetail
import com.aguilar.chefai.domain.repository.RecipeRepository

class GetRecipeDetailUseCase(
    private val repository: RecipeRepository
) {
    suspend operator fun invoke(id: String): RecipeDetail {
        return repository.getRecipeDetails(id)
    }
}
