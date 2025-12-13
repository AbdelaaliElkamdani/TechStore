package com.akn.techstore.project.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.akn.techstore.R
import com.akn.techstore.project.components.ProductCard
import com.akn.techstore.project.components.ProductCardSkeleton
import com.akn.techstore.project.navigation.Routes
import com.akn.techstore.project.theme.DarkText
import com.akn.techstore.project.theme.LightGrayBackground
import com.akn.techstore.project.viewModel.FavouriteViewModel
import com.akn.techstore.project.viewModelProvider.FavouriteViewModelProviderFactory
import kotlin.collections.chunked

@Composable
fun FavouritesScreen(
    navController: NavController,
    viewModel: FavouriteViewModel = viewModel( factory = FavouriteViewModelProviderFactory(LocalContext.current))
) {

    // Observation de l'état des favoris
    val favourites by viewModel.allFavourites.observeAsState(emptyList())
    val isLoading by viewModel.loading // Valeur de chargement

    // Affichage de la liste des favoris

    Column( modifier = Modifier.fillMaxSize())
    {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.white))
        ) {
            Text(
                text = stringResource(R.string.favourite_title),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .padding(top = 16.dp)
                    .padding(start = 16.dp)
            )
        }
        // Affichage du chargement
        if (isLoading) LinearProgressIndicator(modifier = Modifier.fillMaxWidth())

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(LightGrayBackground)
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp)
        ) {
            if (isLoading) {
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
            } else if (!favourites.isEmpty()) {

                // Découpage de la liste des favoris en lots de 2
                val favouriteRows = favourites.chunked(2)

                items(favouriteRows) { rowItems ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        rowItems.forEach { favourite ->
                            ProductCard(
                                product = favourite.product,
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable {navController.navigate(route = Routes.Detail.createRoute(favourite.product.id)) }
                            )
                        }
                        if (rowItems.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            } else {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No favourites found",
                            fontSize = 18.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}