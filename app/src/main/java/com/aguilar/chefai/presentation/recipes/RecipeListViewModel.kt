package com.aguilar.chefai.presentation.recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aguilar.chefai.domain.usecase.GetRecipesByCategoryUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RecipeListViewModel(
    private val getRecipesByCategoryUseCase: GetRecipesByCategoryUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(RecipeListState())
    val state: StateFlow<RecipeListState> = _state.asStateFlow()

    init {
        loadRecipes("Beef") // Categoría inicial
    }

    fun loadRecipes(category: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                val result = getRecipesByCategoryUseCase(category)
                _state.value = _state.value.copy(
                    isLoading = false,
                    recipes = result,
                    error = null
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.localizedMessage ?: "Ocurrió un error inesperado"
                )
            }
        }
    }
}