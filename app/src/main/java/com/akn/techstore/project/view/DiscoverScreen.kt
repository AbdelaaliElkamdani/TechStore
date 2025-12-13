package com.akn.techstore.project.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.akn.techstore.R
import com.akn.techstore.project.viewModel.ProductViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.akn.techstore.project.components.CategoryItem
import com.akn.techstore.project.components.ClearanceSaleCard
import com.akn.techstore.project.components.ProductCard
import com.akn.techstore.project.components.ProductCardSkeleton
import com.akn.techstore.project.navigation.Routes
import com.akn.techstore.project.theme.*
import kotlin.collections.chunked

@Composable
fun DiscoverScreen(
    navController: NavController,
    viewModel: ProductViewModel = viewModel()
) {
    val mockCategories = listOf(
        stringResource(R.string.all),
        stringResource(R.string.headphones),
        stringResource(R.string.laptop),
        stringResource(R.string.watch),
        stringResource(R.string.smartphones),
    )

    var searchQuery by remember { mutableStateOf("") }
    var categoryQuery by remember { mutableStateOf("All") }

    // L'observation de l'état pour la liste des produits
    val state by viewModel.productState.observeAsState()

    val error = viewModel.error

    // Cet effet se lance chaque fois que searchQuery ou categoryQuery changent.
    LaunchedEffect(searchQuery, categoryQuery) {

        // La logique pour déterminer quelle fonction appeler est déplacée ici.
        if (searchQuery.isNotEmpty()) {
            // L'utilisateur a tapé une recherche
            viewModel.getSearchProducts(searchQuery)

        } else if (categoryQuery.isNotEmpty() && categoryQuery != "All") {
            // L'utilisateur a sélectionné une catégorie (sauf "All")
            viewModel.getProductsByCategory(categoryQuery)

        } else {
            // Par défaut (searchQuery est vide ET categoryQuery est vide ou "All")
            // On s'assure de recharger la liste complète s'il y a eu une recherche/catégorie précédente.
            // Le loadProducts initial est géré par l'init du ViewModel, mais c'est bon de le rappeler ici si l'utilisateur efface tout.
            viewModel.loadProducts()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.white))
        ) {

            Text(
                text = "Discover",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .padding(top = 16.dp)
                    .padding(start = 16.dp)
            )
        }

        if(state == null) LinearProgressIndicator(modifier = Modifier.fillMaxWidth())

        LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(id = R.color.gray))
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp)
        ) {

                item {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = {
                            searchQuery = it
                            categoryQuery = "" // Réinitialiser la sélection de catégorie
                        },
                        placeholder = { Text("Search products...") },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = colorResource(id = R.color.white),
                            unfocusedContainerColor = colorResource(id = R.color.white),
                            unfocusedBorderColor = Color.Transparent,
                            focusedBorderColor = colorResource(id = R.color.green),
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                    )
                }

                item {
                    ClearanceSaleCard(modifier = Modifier.padding(bottom = 24.dp, top = 16.dp))
                }

                item {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(bottom = 24.dp)
                    ) {
                        items(mockCategories) { category ->
                            CategoryItem(category,category == categoryQuery.ifEmpty { "All" }, onCategoryClick = { it ->
                                categoryQuery = it
                                searchQuery = ""
                            })
                        }
                    }
                }

                if (state == null) {
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
                } else if (error != null) {
                    item {
                        Text("ERREUR: ${error}", color = Color.Red, modifier = Modifier.padding(16.dp))
                    }
                } else {

                    // Découpage de la liste des produits en lots de 2
                    val productRows = state!!.products.chunked(2)

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
                                        .clickable {
                                            navController.navigate(Routes.Detail.createRoute(product.id))
                                        }
                                )
                            }
                            if (rowItems.size == 1) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
        }
    }
}