package com.akn.techstore.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.akn.techstore.DarkTextColor
import com.akn.techstore.R
import com.akn.techstore.ui.components.ProductCard
import com.akn.techstore.ui.components.ProductCardSkeleton
import com.akn.techstore.ui.model.data.Product
import com.akn.techstore.ui.model.repository.ProductRepository
import com.akn.techstore.ui.viewModel.ProductListViewModel
import kotlin.collections.chunked

@Composable
fun FavouritesScreen(
    navigateToDetails: (Product) -> Unit,
    viewModel: ProductListViewModel = viewModel(factory = object :
        androidx.lifecycle.ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ProductListViewModel(ProductRepository()) as T
        }
    })
) {

    // 1. OBSERVER : Récupérer l'état exposé par le ViewModel.
    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.white))
        ) {

            Text(
                text = "Favourites",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = DarkTextColor,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .padding(top = 16.dp)
                    .padding(start = 16.dp)
            )
        }
        if (state.isLoading) LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.gray))
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp)
        ) {

            if (state.isLoading) {
                val skeletonPlaceholders = List(4) { Unit }
                items(skeletonPlaceholders.chunked(2)) { rowItems ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        rowItems.forEach { i ->
                            ProductCardSkeleton(modifier = Modifier.weight(1f))
                        }
                    }
                }
            } else if (state.error != null) {
                // Affichage de l'erreur
                item {
                    Text(
                        "ERREUR: ${state.error}",
                        color = Color.Red,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            } else {

                val productRows = state.products.chunked(2)

                // Liste de produits en grille 2xN
                items(productRows) { rowItems ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        rowItems.forEach { product ->
                            ProductCard(
                                product = product,
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { navigateToDetails(product) } // Ajout de la navigation
                            )
                        }
                        // Si la ligne a un seul élément, ajoutez un espace vide pour l'alignement
                        if (rowItems.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }

            }
        }
    }
}