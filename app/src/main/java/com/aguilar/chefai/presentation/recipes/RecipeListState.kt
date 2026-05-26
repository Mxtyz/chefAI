package com.aguilar.chefai.presentation.recipes

import com.aguilar.chefai.domain.model.Recipe

data class RecipeListState(
    val isLoading: Boolean = false,
    val recipes: List<Recipe> = emptyList(),
    val error: String? = null
)