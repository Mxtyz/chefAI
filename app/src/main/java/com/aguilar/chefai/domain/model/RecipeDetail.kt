package com.aguilar.chefai.domain.model

data class RecipeDetail(
    val id: String,
    val name: String,
    val instructions: String,
    val imageUrl: String,
    val ingredients: List<String>
)
