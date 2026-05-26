package com.aguilar.chefai.presentation.recipes

import com.aguilar.chefai.domain.model.RecipeDetail

data class RecipeDetailState(
    val isLoading: Boolean = false,
    val recipe: RecipeDetail? = null,
    val error: String? = null
)
