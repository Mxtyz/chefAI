package com.aguilar.chefai.data.mapper

import com.aguilar.chefai.data.remote.dto.RecipeDto
import com.aguilar.chefai.domain.model.Recipe

fun RecipeDto.toDomain(): Recipe {
    return Recipe(
        id = idMeal,
        name = strMeal,
        imageUrl = strMealThumb
    )
}
