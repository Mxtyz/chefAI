package com.aguilar.chefai.presentation.recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aguilar.chefai.domain.usecase.GetRecipeDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RecipeDetailViewModel(
    private val getRecipeDetailUseCase: GetRecipeDetailUseCase,
    recipeId: String
) : ViewModel() {

    private val _state = MutableStateFlow(RecipeDetailState(isLoading = true))
    val state: StateFlow<RecipeDetailState> = _state.asStateFlow()

    init {
        loadRecipe(recipeId)
    }

    private fun loadRecipe(id: String) {
        viewModelScope.launch {
            _state.value = RecipeDetailState(isLoading = true)
            try {
                val recipe = getRecipeDetailUseCase(id)
                _state.value = RecipeDetailState(recipe = recipe)
            } catch (e: Exception) {
                _state.value = RecipeDetailState(error = e.localizedMessage ?: "Error al cargar detalle")
            }
        }
    }
}
