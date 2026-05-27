package com.aguilar.chefai.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aguilar.chefai.domain.usecase.GetRecipesByCategoryUseCase
import com.aguilar.chefai.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RecipeListViewModel(
    private val getRecipesUseCase: GetRecipesByCategoryUseCase,
    private val repository: RecipeRepository // Le pasamos el repositorio para usar la búsqueda directamente
) : ViewModel() {

    private val _state = MutableStateFlow(RecipeListState())
    val state: StateFlow<RecipeListState> = _state.asStateFlow()

    init {
        loadRecipes("Beef") // Categoría inicial por defecto
    }

    fun loadRecipes(category: String) {
        viewModelScope.launch {
            _state.value = RecipeListState(isLoading = true)
            try {
                val recipes = getRecipesUseCase(category)
                _state.value = RecipeListState(recipes = recipes)
            } catch (e: Exception) {
                _state.value = RecipeListState(error = e.localizedMessage ?: "Error de conexión")
            }
        }
    }

    // NUEVA FUNCIÓN: Se ejecuta al usar la lupa
    fun searchRecipes(query: String) {
        if (query.isBlank()) {
            loadRecipes("Beef") // Si borra todo, regresa a la categoría base
            return
        }
        viewModelScope.launch {
            _state.value = RecipeListState(isLoading = true)
            try {
                val results = repository.searchRecipes(query)
                _state.value = RecipeListState(recipes = results)
            } catch (e: Exception) {
                _state.value = RecipeListState(error = e.localizedMessage ?: "Error al buscar")
            }
        }
    }
}
