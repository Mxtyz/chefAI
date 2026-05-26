package com.aguilar.chefai.domain.model

/**
 * Entidad de negocio limpia.
 * Representa lo que la aplicación necesita mostrar, sin dependencias de la API.
 */
data class Recipe(
    val id: String,
    val name: String,
    val imageUrl: String
)