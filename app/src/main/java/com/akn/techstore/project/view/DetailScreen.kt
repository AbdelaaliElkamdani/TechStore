package com.akn.techstore.project.view


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.akn.techstore.R
import com.akn.techstore.project.model.data.Cart
import com.akn.techstore.project.model.data.Favourite
import com.akn.techstore.project.theme.*
import com.akn.techstore.project.viewModel.CartViewModel
import com.akn.techstore.project.viewModel.FavouriteViewModel
import com.akn.techstore.project.viewModel.ProductViewModel
import com.akn.techstore.project.viewModelProvider.CartViewModelProviderFactory
import com.akn.techstore.project.viewModelProvider.FavouriteViewModelProviderFactory

@Composable
fun DetailScreen(
    productId: Int,
    onBack: () -> Unit,
    viewModel: ProductViewModel = viewModel()
) {
    LaunchedEffect(productId) {
        // Appel a la fonction du ViewModel uniquement si l'ID change
        viewModel.getProductById(productId)
    }

    // L'observation de l'état doit être faite AVANT l'appel de fonction
    val product = viewModel.productIdState.observeAsState().value?.products[0]

    val context = LocalContext.current

    val favViewModel: FavouriteViewModel = viewModel( factory = FavouriteViewModelProviderFactory(context))
    val cartViewModel: CartViewModel = viewModel( factory = CartViewModelProviderFactory(context))

    // Ce LaunchedEffect pour les checks est correct.
    LaunchedEffect(productId) {
        favViewModel.checkIsFavourite(productId)
        cartViewModel.checkInCart(productId)
    }

    // Recuperation des etats observables pour les checks afin de connaitre si le produit est dans la base de donnees
    val isFavourite by favViewModel.isFavourite.collectAsState(initial = false)
    val isInCart by cartViewModel.isInCart.collectAsState(initial = false)


    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.gray))
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(colorResource(id = R.color.white)),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = DarkText)
            }

            Text(
                text = stringResource(R.string.detail_title),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText
            )

            IconButton(onClick = {
                if (product != null) {
                    if (!isFavourite) {
                        favViewModel.addToFavourite(Favourite(product = product))
                    } else {
                        favViewModel.removeFromFavourite(productId)
                    }
                }
            }) {
                Icon(
                     if(isFavourite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = if(isFavourite) Color.Red else Color.Gray
                )
            }
        }

        if (product != null) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorResource(id = R.color.gray))
                    .padding(horizontal = 8.dp)
                    .weight(1f),
                contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp)
            ) {

                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                            .height(300.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.White)
                            .padding(10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            AsyncImage(
                                 model = product.imageUrl,
                                contentDescription = "Product Image",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxSize()
                            )
                        }
                    }
                }

                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.White)
                            .padding(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                product.name,
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold,
                                color = DarkText,
                                modifier = Modifier.weight(1f)
                            )
                            Icon(
                                Icons.Default.ThumbUp,
                                contentDescription = "Favorite",
                                tint = Color.Green,
                                modifier = Modifier.size(32.dp).padding(start = 8.dp)
                            )
                        }
                        Spacer(Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Star,
                                contentDescription = "Rating",
                                tint = Color(0xFFFFC107),
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                product.rating.toString(),
                                fontSize = 16.sp,
                                color = DarkText,
                                fontWeight = FontWeight.Medium
                            )
                            Spacer(Modifier.width(4.dp))
                            Text("(${product.id} reviews)", fontSize = 14.sp, color = Color.Gray)
                        }
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = product.description,
                            fontSize = 14.sp,
                            color = Color.DarkGray,
                            lineHeight = 20.sp,
                            modifier = Modifier.fillMaxWidth().padding(10.dp),
                        )
                        Spacer(Modifier.height(8.dp))

                        Text(
                            text = "${product.price}Dh",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkText,
                            modifier = Modifier.fillMaxWidth().padding(10.dp),
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        if(!isInCart) {
                            cartViewModel.addToCart(Cart(product = product))
                        }
                    },
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.green)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .height(56.dp)
                        .fillMaxWidth()
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.ShoppingCart,
                            contentDescription = "Cart",
                            modifier = Modifier.size(20.dp).padding(end = 4.dp)
                        )
                        Text(
                            text = if (isInCart) "In Cart" else "Add to Cart",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        } else {
            Box(modifier = Modifier .fillMaxSize(), contentAlignment =
                Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }
}