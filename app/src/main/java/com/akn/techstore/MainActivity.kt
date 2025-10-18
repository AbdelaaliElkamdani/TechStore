package com.akn.techstore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

// Enumération pour gérer les différents états d'écran
enum class AuthScreen {
    WELCOME, LOGIN, SIGNUP
}

// --- Couleurs et Constantes ---
val PrimaryColor = Color(0xFF4CAF50)
val DarkTextColor = Color(0xFF1E1E1E)
val FieldBackgroundColor = Color(0xFF2A2A2A).copy(alpha = 0.7f)
val CustomLightGray = Color(0xFFCCCCCC)
val GrayBackground = Color(0xFFF7F7F7)

// --- Modèles de Données (pour simuler les produits) ---
data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val rating: Double,
    val imageUrl: String,
    val isNew: Boolean = false
)

data class Category(
    val name: String,
    val icon: ImageVector,
    val color: Color
)

// Données fictives pour l'aperçu
val mockProducts = listOf(
    Product(1, "Smartphone Pro", 899.99, 4.5, "https://placehold.co/100x100/1E1E1E/FFFFFF?text=P1", isNew = true),
    Product(2, "Headphones ANC", 249.99, 4.2, "https://placehold.co/100x100/4CAF50/FFFFFF?text=P2"),
    Product(3, "Laptop Ultrabook", 1299.00, 4.8, "https://placehold.co/100x100/1E1E1E/FFFFFF?text=P3"),
    Product(4, "Smartwatch X", 349.99, 4.6, "https://placehold.co/100x100/4CAF50/FFFFFF?text=P4", isNew = true),
)
val mockCategories = listOf(
    Category("All", Icons.Default.Check, Color(0xFF90A4AE)),
    Category("Smartphones", Icons.Default.Phone, Color(0xFF42A5F5)),
    Category("Headphones", Icons.Default.Call, Color(0xFF66BB6A)),
    Category("Laptop", Icons.Default.ShoppingCart, Color(0xFFFF7043)),
    Category("Watch", Icons.Default.Check, Color(0xFF90A4AE)),
)

enum class MainScreen(val title: String, val icon: ImageVector) {
    HOME("Discover", Icons.Default.Home),
    FAVORITES("Favorites", Icons.Default.Favorite),
    CART("My Cart", Icons.Default.ShoppingCart),
    PROFILE("Profile", Icons.Default.Person)
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            // État de navigation global
            var currentAuthScreen by remember { mutableStateOf(AuthScreen.WELCOME) }
            var isLoggedIn by remember { mutableStateOf(false) }

            // Thème de base pour l'application
            MaterialTheme(
                colorScheme = MaterialTheme.colorScheme.copy(
                    primary = colorResource(id = R.color.green),
                    secondary = colorResource(id = R.color.green),
                    surface = colorResource(id = R.color.white),
                    background = colorResource(id = R.color.white)
                )
            ) {
                // Fonction de navigation simple pour l'authentification
                val navigateTo: (AuthScreen) -> Unit = { screen ->
                    currentAuthScreen = screen
                }

                // Si l'utilisateur est connecté, afficher le contenu principal (MainContent)
                if (isLoggedIn) {
                    MainContent()
                } else {
                    // Sinon, afficher les écrans d'authentification
                    when (currentAuthScreen) {
                        AuthScreen.WELCOME -> WelcomeScreen { navigateTo(AuthScreen.LOGIN) }
                        AuthScreen.LOGIN -> LoginScreen(
                            navigateTo = navigateTo,
                            onLoginSuccess = { isLoggedIn = true } // Change l'état de connexion ici
                        )
                        AuthScreen.SIGNUP -> SignUpScreen(navigateTo = navigateTo)
                    }
                }
            }
        }
    }
}


// --- CONTENU PRINCIPAL (après connexion) ---
@Composable
fun MainContent() {
    // Nouvelle variable pour gérer l'état de navigation détaillée (hors barre de nav principale)
    // Permet de naviguer vers DetailScreen ou CartScreen
    var currentMainScreen by remember { mutableStateOf<Any>(MainScreen.HOME) }

    // Conversion de l'état pour la Bottom Bar
    val currentBottomScreen = when (currentMainScreen) {
        is MainScreen -> currentMainScreen as MainScreen
        else -> MainScreen.HOME // Si on est sur un écran détail, on met en surbrillance l'accueil
    }

    // Fonction de navigation pour l'accueil/panier/favoris/profil
    val navigateToMain: (MainScreen) -> Unit = { screen ->
        currentMainScreen = screen
    }

    // Fonction pour naviguer vers un produit spécifique (sera utilisé plus tard)
    val navigateToDetails: (Product) -> Unit = { product ->
        // Pour gérer la navigation vers l'écran de détail, nous mettons l'objet Product dans l'état de navigation
        currentMainScreen = product
        // Note: La Bottom Bar ne s'affichera pas si currentMainScreen n'est pas de type MainScreen
    }

    Scaffold(
        bottomBar = {
            // La bottom bar s'affiche uniquement sur les écrans principaux
            if (currentMainScreen is MainScreen) {
                BottomNavigationBar(
                    currentScreen = currentBottomScreen,
                    onScreenSelected = navigateToMain
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(GrayBackground)
        ) {
            when (currentMainScreen) {
                MainScreen.HOME -> DiscoverScreen(navigateToDetails = navigateToDetails)
                MainScreen.FAVORITES -> FavouritesScreen(navigateToDetails = navigateToDetails)
                MainScreen.CART -> CartScreen()
                MainScreen.PROFILE -> ProfileScreen()
                is Product -> DetailScreen(
                    product = currentMainScreen as Product,
                    onBack = { currentMainScreen = MainScreen.HOME }
                )
                else -> PlaceholderScreen("Unknown Screen")
            }
        }
    }
}

@Composable
fun DetailScreen(product: Product, onBack: () -> Unit) {

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
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = DarkTextColor)
            }

            Text(
                text = "Details",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = DarkTextColor
            )

            IconButton(onClick = {}) {
                Icon(
                    Icons.Default.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = Color.Gray
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.gray))
                .padding(horizontal = 8.dp)
                .weight(1f),
            contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp)
        ) {

            // 1. Image du Produit (Haut de l'écran)
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
                            .fillMaxSize()
                            .background(Color.Gray)
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        // Placeholder d'image agrandi
                        Text("PRODUCT IMAGE", fontSize = 28.sp, color = Color.White)
                    }
                }
            }

            // 2. Infos principales et Couleur
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White)
                        .padding(10.dp)
                ) {
                    // Nom et Rating
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            product.name,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkTextColor,
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
                    // Rating
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Star, contentDescription = "Rating", tint = Color(0xFFFFC107), modifier = Modifier.size(20.dp))
                        Text(product.rating.toString(), fontSize = 16.sp, color = DarkTextColor, fontWeight = FontWeight.Medium)
                        Spacer(Modifier.width(4.dp))
                        Text("(${product.id} reviews)", fontSize = 14.sp, color = Color.Gray)
                    }
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "This is the detailed description for the ${product.name}. It features a high-resolution display, long-lasting battery life, and the latest chipset for unparalleled performance. Ideal for gaming and professional use. Don't miss out on this cutting-edge technology!",
                        fontSize = 14.sp,
                        color = Color.DarkGray,
                        lineHeight = 20.sp,
                        modifier = Modifier.fillMaxWidth().padding(10.dp),
                    )
                    Spacer(Modifier.height(8.dp))
                    // Prix
                    Text(
                        text = "$${product.price}",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkTextColor,
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
            // Bouton Ajouter au panier
            Button(
                onClick = {
                    // Logique pour ajouter au panier
                    println("${product.name} added to cart.")
                },
                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.green)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .height(56.dp)
                    .fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.ShoppingCart, contentDescription = "Cart", modifier = Modifier.size(20.dp).padding(end = 4.dp))
                    Text(text = "Add to Cart", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}


// --- Barre de Navigation Inférieure ---
@Composable
fun BottomNavigationBar(currentScreen: MainScreen, onScreenSelected: (MainScreen) -> Unit) {
    NavigationBar(
        containerColor = Color.White,
        contentColor = PrimaryColor,
    ) {
        MainScreen.entries.forEach { screen ->
            val isSelected = currentScreen == screen
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = screen.title
                    )
                },
                label = { Text(screen.title, fontSize = 10.sp) },
                selected = isSelected,
                onClick = { onScreenSelected(screen) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = PrimaryColor,
                    selectedTextColor = PrimaryColor,
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}


// --- ÉCRAN 4 : DISCOVER (Accueil) ---
@Composable
fun DiscoverScreen(navigateToDetails: (Product) -> Unit) {
    var searchQuery by remember { mutableStateOf("") }

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
                color = DarkTextColor,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .padding(top = 16.dp)
                    .padding(start = 16.dp)
            )
        }

        // 5. Section "Featured Products" (Utilisez cette structure pour l'API)

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.gray))
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp)
        ) {

            item {
                // 2. Champ de recherche
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
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
                        .height(50.dp)
                )
            }

            // 3. Zone "Clearance Sales"

            item {
                ClearanceSaleCard(modifier = Modifier.padding(bottom = 24.dp, top = 16.dp))
            }

            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.padding(bottom = 24.dp)
                ) {
                    items(mockCategories) { category ->
                        CategoryItem(category)
                    }
                }
            }

            // Liste de produits en grille 2xN
            items(mockProducts.chunked(2)) { rowItems ->
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

@Composable
fun ClearanceSaleCard(modifier: Modifier = Modifier) {

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.green)),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text("Clearance Sales", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text("Up to 50% off", color = Color.White, fontSize = 14.sp, modifier = Modifier.padding(vertical = 4.dp))
                Button(
                    onClick = { /* Naviguer vers les soldes */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp)
                ) {
                    Text("Shop now", color = PrimaryColor, fontSize = 14.sp)
                }
            }
            // Image Placeholder - Remplacement de PhoneIphone par Store
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0x80FFFFFF)), // 50% de blanc transparent
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.ShoppingCart, contentDescription = "Sale Icon", tint = colorResource(id = R.color.green), modifier = Modifier.size(50.dp))
            }
        }
    }
}

@Composable
fun CategoryItem(category: Category) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { /* Filtre par catégorie */ }
    ) {
        Box(
            modifier = Modifier
                .height(30.dp)
                .clip(RoundedCornerShape(25.dp))
                .background(colorResource(id = R.color.white))
                .padding(horizontal = 16.dp), // Fond très clair
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = category.name,
                fontSize = 12.sp,
                color = DarkTextColor,
            )
        }
    }
}

@Composable
fun ProductCard(product: Product, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        // Le `clickable` est maintenant dans DiscoverScreen pour gérer la navigation
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Box (
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                // Image Placeholder
                Box(
                    modifier = Modifier
                        .height(140.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFFE0E0E0))

                ) {
                    // Ici, vous utiliserez un composant Image avec Glide/Coil ou l'URL de l'API
                    Text("IMG", modifier = Modifier.align(Alignment.Center), color = Color.Gray)
                }

                // Bouton J'aime (Heart)
                Icon(
                    Icons.Default.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = Color.Gray,
                    modifier = Modifier
                        .size(30.dp)
                        .padding(top = 8.dp, end = 8.dp)
                        .align(Alignment.TopEnd)
                )
            }

            Spacer(Modifier.height(8.dp))
            Text(product.name, fontWeight = FontWeight.SemiBold, color = DarkTextColor, fontSize = 14.sp)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Star, contentDescription = "Rating", tint = Color(0xFFFFC107), modifier = Modifier.size(16.dp))
                Text(product.rating.toString(), fontSize = 12.sp, color = Color.Gray)
                Spacer(Modifier.width(8.dp))
                Text("(${product.id} reviews)", fontSize = 12.sp, color = Color.Gray)
            }
            Spacer(Modifier.height(4.dp))
            Text("$${product.price}", fontWeight = FontWeight.Bold, color = colorResource(id = R.color.green), fontSize = 16.sp)
        }
    }
}

// --- Écran de remplacement temporaire ---
@Composable
fun PlaceholderScreen(name: String) {
    Box(
        modifier = Modifier.fillMaxSize().background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Text("Welcome to the $name", fontSize = 24.sp, color = DarkTextColor)
    }
}


@Composable
fun FavouritesScreen(navigateToDetails: (Product) -> Unit) {

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

        // 5. Section "Featured Products" (Utilisez cette structure pour l'API)

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.gray))
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp)
        ) {

            // Liste de produits en grille 2xN
            items(mockProducts.chunked(2)) { rowItems ->
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

// --- ÉCRAN 1 : WELCOME (Bienvenue) ---

@Composable
fun WelcomeScreen(navigateTo: (AuthScreen) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.green)),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        // En-tête (Logo et nom de la marque)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 48.dp, bottom = 32.dp)
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Placeholder du Logo (Remplacez ceci par votre Image réelle)
            Image(
                painter = painterResource(id = R.drawable.splashscreen_logo), // Assurez-vous que votre logo est dans le dossier 'drawable'
                contentDescription = "Logo TechStore",
                modifier = Modifier
                    .size(395.dp)
            )
        }

        // Partie inférieure blanche
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // Prend le reste de l'espace
                .background(Color.White, RoundedCornerShape(topStart = 60.dp, topEnd = 60.dp))
                .padding(40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Welcome",
                color = colorResource(id = R.color.black),
                fontSize = 60.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 40.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "to our store",
                color = colorResource(id = R.color.black),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 40.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Future Tech. Now in your hands",
                color = Color.Gray,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Bouton "Get Started"
            Button(
                onClick = { navigateTo(AuthScreen.LOGIN) }, // On va à l'écran de Login
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.green)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(text = "Get Started", fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

// --- ÉCRAN 2 : LOGIN (Connexion) ---

@Composable
fun LoginScreen(navigateTo: (AuthScreen) -> Unit, onLoginSuccess: () -> Unit) {
    var email by remember { mutableStateOf("test@example.com") } // Valeurs par défaut pour tester la connexion
    var password by remember { mutableStateOf("password123") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Titre de l'écran
        Text(
            text = "Log In",
            color = colorResource(id = R.color.black),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 80.dp, bottom = 40.dp)
        )

        // Champ Email
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = colorResource(id = R.color.sdGray).copy(alpha = 0.7f),
                unfocusedContainerColor = colorResource(id = R.color.sdGray).copy(alpha = 0.7f)
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(20.dp))

        // Champ Mot de passe
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = colorResource(id = R.color.sdGray).copy(alpha = 0.7f),
                unfocusedContainerColor = colorResource(id = R.color.sdGray).copy(alpha = 0.7f)
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))

        // Lien "Forgot Password ?"
        Text(
            text = "Forgot password ?",
            color = colorResource(id = R.color.green),
            fontSize = 14.sp,
            modifier = Modifier
                .align(Alignment.Start)
                .clickable { /* Logique pour mot de passe oublié */ }
        )
        Spacer(Modifier.height(32.dp))

        // Bouton Login
        Button(
            onClick = {
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    onLoginSuccess()
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.green)),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(text = "Login", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        }
        Spacer(Modifier.weight(1f)) // Pousse le texte en bas

        // Lien "Don't have an account ?"
        Row(
            modifier = Modifier.padding(bottom = 32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Don't have an account ? ", color = Color.Gray, fontSize = 14.sp)
            Text(
                text = "Sign up",
                color = colorResource(id = R.color.green),
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.clickable { navigateTo(AuthScreen.SIGNUP) }
            )
        }
    }
}

// --- ÉCRAN 3 : SIGN UP (Inscription) ---

@Composable
fun SignUpScreen(navigateTo: (AuthScreen) -> Unit) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Titre de l'écran
        Text(
            text = "Sign Up",
            color = colorResource(id = R.color.black),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 80.dp, bottom = 40.dp)
        )

        // Champ Nom
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = colorResource(id = R.color.sdGray).copy(alpha = 0.7f),
                unfocusedContainerColor = colorResource(id = R.color.sdGray).copy(alpha = 0.7f)
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(20.dp))

        // Champ Email
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = colorResource(id = R.color.sdGray).copy(alpha = 0.7f),
                unfocusedContainerColor = colorResource(id = R.color.sdGray).copy(alpha = 0.7f)
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(20.dp))

        // Champ Mot de passe
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = colorResource(id = R.color.sdGray).copy(alpha = 0.7f),
                unfocusedContainerColor = colorResource(id = R.color.sdGray).copy(alpha = 0.7f)
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(20.dp))

        // Champ Confirmer mot de passe
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = colorResource(id = R.color.sdGray).copy(alpha = 0.7f),
                unfocusedContainerColor = colorResource(id = R.color.sdGray).copy(alpha = 0.7f)
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(32.dp))

        // Bouton Sign up
        Button(
            onClick = { /* Logique d'inscription */ println("Inscription de: $name") },
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.green)),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(text = "Sign up", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        }
        Spacer(Modifier.weight(1f)) // Pousse le texte en bas

        // Lien "Already have an account ? Login"
        Row(
            modifier = Modifier.padding(bottom = 32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Already have an account ? ", color = Color.Gray, fontSize = 14.sp)
            Text(
                text = "Login",
                color = colorResource(id = R.color.green),
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.clickable { navigateTo(AuthScreen.LOGIN) }
            )
        }
    }
}

// --- 1. COULEURS & CONSTANTES SIMPLES ---
val PrimaryGreen = Color(0xFF4CAF50)
val DarkText = Color(0xFF1E1E1E)
val LightGrayBackground = Color(0xFFF7F7F7)
val SoftCardBackground = Color(0xFFFFFFFF)
val ErrorRed = Color(0xFFF44336)
val GrayBorder = Color(0xFFE0E0E0)

// --- 2. MODÈLES DE DONNÉES ---

data class SimpleProduct(
    val id: Int,
    val name: String,
    val price: Double,
    val color: String = "",
    val imageUrl: String = "https://placehold.co/80x80/288D2A/FFFFFF?text=IMG"
)

data class CartItem(
    val product: SimpleProduct,
    val quantity: Int
)

// Données fictives pour le panier
val mockCartItems = listOf(
    CartItem(SimpleProduct(1, "Xbox Series X", 570.00), 1),
    CartItem(SimpleProduct(2, "Wireless Controller", 77.00, "Blue"), 1),
    CartItem(SimpleProduct(3, "Razer Kaira Pro", 153.00, "Green"), 1),
)

// --- 3. ÉCRAN DU PANIER (My Cart) ---

@Composable
fun CartScreen() {
    // État mutable du panier pour simuler les interactions (ajout/suppression)
    val cartItemsState = remember { mutableStateListOf<CartItem>().apply { addAll(mockCartItems) } }

    // Fonction pour mettre à jour la quantité d'un article
    val updateQuantity: (CartItem, Int) -> Unit = { item, newQuantity ->
        val index = cartItemsState.indexOfFirst { it.product.id == item.product.id }
        if (index != -1) {
            if (newQuantity <= 0) {
                cartItemsState.removeAt(index)
            } else {
                cartItemsState[index] = item.copy(quantity = newQuantity)
            }
        }
    }

    // Calculs de prix
    val subtotal = cartItemsState.sumOf { it.product.price * it.quantity }
    val deliveryFee = 5.00
    val discountRate = 0.40 // 40%
    val discount = if (subtotal > 0) subtotal * discountRate else 0.0
    val total = subtotal + deliveryFee - discount

    // En-tête de l'écran (sans la barre de navigation complète)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightGrayBackground)
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.white))
        ) {

            Text(
                text = "My Cart",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = DarkTextColor,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .padding(top = 16.dp)
                    .padding(start = 16.dp)
            )
        }

        // Liste des articles du panier
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(cartItemsState, key = { it.product.id }) { item ->
                CartItemRow(
                    item = item,
                    onQuantityChange = { newQty -> updateQuantity(item, newQty) },
                    onRemove = { updateQuantity(item, 0) }
                )
            }

            // Section Récapitulatif
            item {
                PriceSummary(subtotal, deliveryFee, discount, total)
            }
        }

        // Bouton de Checkout (Paiement)
        Button(
            onClick = { /* Action de paiement */ },
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(56.dp)
        ) {
            Text(
                text = "Checkout for $${"%.2f".format(total)}",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun CartItemRow(
    item: CartItem,
    onQuantityChange: (Int) -> Unit,
    onRemove: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = SoftCardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Box{
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Image et Texte
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                // Image Placeholder
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFFE0E0E0)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("IMG", color = Color.Gray)
                }

                Spacer(Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            item.product.name,
                            fontWeight = FontWeight.SemiBold,
                            color = DarkText,
                            fontSize = 16.sp,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    if (item.product.color.isNotEmpty()) {
                        Text("(${item.product.color})", fontSize = 14.sp, color = Color.Gray)
                    }
                    Text(
                        "$${"%.2f".format(item.product.price)}",
                        fontWeight = FontWeight.Bold,
                        color = PrimaryGreen,
                        fontSize = 15.sp
                    )
                }
            }

            // Sélecteur de Quantité
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(start = 12.dp)) {
                QuantityButton(Icons.Default.KeyboardArrowLeft, enabled = item.quantity > 1) {
                    onQuantityChange(item.quantity - 1)
                }
                Text(item.quantity.toString(), fontSize = 16.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(horizontal = 8.dp))
                QuantityButton(Icons.Default.KeyboardArrowRight) {
                    onQuantityChange(item.quantity + 1)
                }

            }
        }
            // Bouton Supprimer (X)
            Icon(
                Icons.Default.Close,
                contentDescription = "Remove",
                tint = Color.Gray,
                modifier = Modifier
                    .clickable(onClick = onRemove)
                    .padding(top = 8.dp, end = 8.dp)
                    .align(Alignment.TopEnd)
                    .size(20.dp)
            )
    }
    }
}

@Composable
fun QuantityButton(icon: ImageVector, enabled: Boolean = true, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(30.dp)
            .clip(CircleShape)
            .background(if (enabled) PrimaryGreen.copy(alpha = 0.1f) else GrayBorder)
            .clickable(enabled = enabled, onClick = onClick)
            .border(1.dp, if (enabled) PrimaryGreen else GrayBorder, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(icon, contentDescription = null, tint = if (enabled) PrimaryGreen else Color.Gray, modifier = Modifier.size(20.dp))
    }
}

/*
@Composable
fun PromoCodeSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(SoftCardBackground)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Face, contentDescription = "Promo", tint = PrimaryGreen)
            Spacer(Modifier.width(8.dp))
            Text("ADJ3AK", fontWeight = FontWeight.Medium, color = DarkText)
        }
        Text(
            "Promocode applied",
            color = Color.White,
            fontSize = 12.sp,
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(PrimaryGreen)
                .padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
    Spacer(Modifier.height(16.dp))
}*/

@Composable
fun PriceSummary(subtotal: Double, deliveryFee: Double, discount: Double, total: Double) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = SoftCardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            SummaryRow("Subtotal", subtotal, Color.Black)
            SummaryRow("Delivery Fee", deliveryFee, Color.Black)
            SummaryRow("Discount (40%)", -discount, ErrorRed)

            Spacer(Modifier.height(12.dp))
            Divider(color = GrayBorder)
            Spacer(Modifier.height(12.dp))

            SummaryRow("Total", total, PrimaryGreen, isTotal = true)
        }
    }
}

@Composable
fun SummaryRow(label: String, amount: Double, color: Color, isTotal: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            label,
            color = DarkText,
            fontSize = if (isTotal) 18.sp else 15.sp,
            fontWeight = if (isTotal) FontWeight.Bold else FontWeight.Normal
        )
        Text(
            (if (amount >= 0) "$" else "-$") + "%.2f".format(kotlin.math.abs(amount)),
            color = color,
            fontSize = if (isTotal) 19.sp else 15.sp,
            fontWeight = if (isTotal) FontWeight.ExtraBold else FontWeight.SemiBold
        )
    }
}

// --- 4. ÉCRAN DU PROFIL (Profile) ---

@Composable
fun ProfileScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightGrayBackground)
    ) {
        // En-tête de l'écran
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.white))
        ) {

            Text(
                text = "Profile",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = DarkTextColor,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .padding(top = 16.dp)
                    .padding(start = 16.dp)
            )
        }

        // Contenu du Profil
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            // Section Avatar et Infos
            item {
                ProfileHeader(
                    name = "Emma Johnson",
                    email = "emma.johnson@email.com",
                    imageUrl = "https://placehold.co/100x100/CCCCCC/FFFFFF?text=EJ" // Placeholder pour l'image
                )
                Spacer(Modifier.height(24.dp))
            }

            // Liste des Options de Profil
            item {
                OptionsList()
            }
        }
    }
}

@Composable
fun ProfileHeader(name: String, email: String, imageUrl: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(contentAlignment = Alignment.BottomEnd) {
            // Image de profil
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE0E0E0)) // Couleur de fond du placeholder
                    .border(1.dp, GrayBorder, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(name.first().toString(), fontSize = 40.sp, color = Color.Gray)
            }
            // Bouton de caméra (Modification)
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(PrimaryGreen)
                    .size(30.dp)
                    .border(2.dp, SoftCardBackground, CircleShape)
                    .clickable { /* Action Modifier Image */ },
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Edit Picture", tint = Color.White, modifier = Modifier.size(16.dp))
            }
        }
        Spacer(Modifier.height(8.dp))
        Text(name, fontWeight = FontWeight.Bold, fontSize = 20.sp, color = DarkText)
        Text(email, fontSize = 14.sp, color = Color.Gray)
    }
}

@Composable
fun OptionsList() {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = SoftCardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            ProfileOption(
                icon = Icons.Default.Info,
                title = "My Informations",
                iconColor = Color(0xFF42A5F5), // Bleu
                onClick = { /* Naviguer vers Infos */ }
            )
            HorizontalDivider(color = GrayBorder, thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))
            ProfileOption(
                icon = Icons.Default.Star,
                title = "Themes",
                iconColor = PrimaryGreen,
                onClick = { /* Naviguer vers Thèmes */ }
            )
            HorizontalDivider(color = GrayBorder, thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))
            ProfileOption(
                icon = Icons.Default.LocationOn,
                title = "Address Book",
                iconColor = Color(0xFF9C27B0), // Violet
                onClick = { /* Naviguer vers Adresses */ }
            )
            HorizontalDivider(color = GrayBorder, thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))
            ProfileOption(
                icon = Icons.Default.Settings,
                title = "Settings",
                iconColor = Color(0xFF607D8B), // Gris ardoise
                onClick = { /* Naviguer vers Paramètres */ }
            )
            HorizontalDivider(color = GrayBorder, thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))
            // Option Logout
            ProfileOption(
                icon = Icons.AutoMirrored.Filled.ExitToApp,
                title = "Logout",
                iconColor = ErrorRed,
                titleColor = ErrorRed,
                onClick = { /* Action de Déconnexion */ }
            )
        }
    }
}

@Composable
fun ProfileOption(
    icon: ImageVector,
    title: String,
    iconColor: Color,
    titleColor: Color = DarkText,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 14.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Icône avec cercle de couleur
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(iconColor.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = title, tint = iconColor, modifier = Modifier.size(20.dp))
            }
            Spacer(Modifier.width(16.dp))
            Text(title, fontSize = 16.sp, color = titleColor, fontWeight = FontWeight.Medium)
        }
        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "Go", tint = GrayBorder)
    }
}


// --- 5. PRÉVISUALISATION ---

@Preview(showBackground = true, name = "Cart Screen Preview")
@Composable
fun CartScreenPreview() {
    MaterialTheme {
        CartScreen()
    }
}

@Preview(showBackground = true, name = "Profile Screen Preview")
@Composable
fun ProfileScreenPreview() {
    MaterialTheme {
        ProfileScreen()
    }
}

@Preview(showBackground = true, name = "Profile Screen Preview")
@Composable
fun NavigationPreview() {
    MaterialTheme {
        BottomNavigationBar(currentScreen = MainScreen.HOME, onScreenSelected = {})
    }
}

// --- Aperçus (Prévisualisations) ---
/*
@Preview(showBackground = true)
@Composable
fun AppPreview() {
    // Affiche l'application dans son état initial (WELCOME)
    MaterialTheme {
        MainContent() // Affiche directement le contenu principal pour la prévisualisation
    }
}


@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen(navigateTo = {})
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    DetailScreen( product = mockProducts[0] , onBack = {} )
}

@Preview(showBackground = true)
@Composable
fun FavouritesScreenPreview() {
    FavouritesScreen( navigateToDetails = {})
}*/