package com.akn.techstore.project.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.akn.techstore.project.navigation.Routes
import com.akn.techstore.project.theme.*
import kotlin.Unit

data class Option(
    val icon: ImageVector,
    val title: String,
    val iconColor: Color,
    val titleColor: Color = DarkText,
    val onClick: () -> Unit
)

@Composable
fun OptionsList(navController: NavController, onLogout: () -> Unit) {
    val options = listOf(
        Option(
            icon = Icons.Default.Menu,
            title = "My Orders",
            iconColor = Color.Green,
            onClick = { navController.navigate(Routes.Order.route) }
        ),
        Option(
            icon = Icons.Default.Edit,
            title = "Edit Profile",
            iconColor = Color(0xFF42A5F5),
            onClick = { navController.navigate(Routes.EditProfile.route) }
        ),
        Option(
            icon = Icons.Default.Lock,
            title = "Change Password",
            iconColor = Color(0xFFF44336),
            onClick = { navController.navigate(Routes.Password.route) }
        ),
        Option(
            icon = Icons.Default.LocationOn,
            title = "Address Book",
            iconColor = Color(0xFF9C27B0),
            onClick = { navController.navigate(Routes.Address.route) }
        ),
        Option(
            icon = Icons.Default.Settings,
            title = "Settings",
            iconColor = Color(0xFF607D8B),
            onClick = { navController.navigate(Routes.Setting.route) }
        ),
        Option(
            icon = Icons.AutoMirrored.Filled.ExitToApp,
            title = "Logout",
            iconColor = ErrorRed,
            titleColor = ErrorRed,
            onClick = { onLogout() }
        )
    )

    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.background(Color.White)) {
            options.forEachIndexed { index, option ->
                ProfileOption(option)
                if (index < options.lastIndex) {
                    HorizontalDivider(color = GrayBorder, thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))
                }
            }
        }
    }
}

@Composable
fun ProfileOption(option: Option) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = option.onClick)
            .padding(vertical = 14.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(option.iconColor.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(option.icon, contentDescription = option.title, tint = option.iconColor, modifier = Modifier.size(20.dp))
            }
            Spacer(Modifier.width(16.dp))
            Text(option.title, fontSize = 16.sp, color = option.titleColor, fontWeight = FontWeight.Medium)
        }
        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "Go", tint = GrayBorder)
    }
}