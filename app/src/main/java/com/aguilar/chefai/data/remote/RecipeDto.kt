package com.aguilar.chefai.data.remote

import com.google.gson.annotations.SerializedName

/**
 * Data Transfer Object (DTO) para representar la receta en el JSON de la API.
 */
data class RecipeDto(
    @SerializedName("idMeal")
    val idMeal: String,
    @SerializedName("strMeal")
    val strMeal: String,
    @SerializedName("strMealThumb")
    val strMealThumb: String
)