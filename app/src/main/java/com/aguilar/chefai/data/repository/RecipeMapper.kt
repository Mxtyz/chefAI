package com.aguilar.chefai.data.repository

import com.aguilar.chefai.data.remote.RecipeDto
import com.aguilar.chefai.domain.model.Recipe

fun RecipeDto.toDomain(): Recipe {
    return Recipe(
        id = idMeal,
        name = strMeal,
        imageUrl = strMealThumb
    )
}