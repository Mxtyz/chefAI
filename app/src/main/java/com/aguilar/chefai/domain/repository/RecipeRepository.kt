package com.aguilar.chefai.domain.repository

import com.aguilar.chefai.domain.model.Recipe

/**
 * Interfaz del repositorio (Abstracción).
 * Define el contrato de qué datos necesitamos, sin importar de dónde vienen.
 */
interface RecipeRepository {
    suspend fun getRecipesByCategory(category: String): List<Recipe>
}