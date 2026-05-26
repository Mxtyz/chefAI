package com.aguilar.chefai.presentation.recipes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.aguilar.chefai.domain.model.Recipe

// CAMBIO 1: Agregamos "onRecipeClick" para decirle a la pantalla que vamos a navegar
@Composable
fun RecipeListScreen(
    viewModel: RecipeListViewModel,
    onRecipeClick: (String) -> Unit
) {
    val state by viewModel.state.collectAsState()

    // (Eliminamos la variable 'context' porque ya no usaremos el Toast)

    Box(modifier = Modifier.fillMaxSize()) {
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

        state.error?.let { message ->
            Text(
                text = message,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.align(Alignment.Center).padding(16.dp)
            )
        }

        if (!state.isLoading && state.error == null) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.recipes) { recipe ->
                    RecipeItem(recipe = recipe) {
                        // CAMBIO 2: En lugar del Toast, ejecutamos la navegación
                        onRecipeClick(recipe.name)
                    }
                }
            }
        }
    }
}

// La parte del diseño (RecipeItem) se queda exactamente igual
@Composable
fun RecipeItem(recipe: Recipe, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = recipe.imageUrl,
                contentDescription = recipe.name,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = recipe.name,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}