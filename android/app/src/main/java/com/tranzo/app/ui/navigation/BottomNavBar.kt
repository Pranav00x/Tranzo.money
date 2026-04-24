package com.tranzo.app.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route: String,
)

val bottomNavItems = listOf(
    BottomNavItem("Home", Icons.Outlined.Home, Screen.Home.route),
    BottomNavItem("Card", Icons.Outlined.CreditCard, Screen.Card.route),
    BottomNavItem("Dripper", Icons.Outlined.WaterDrop, Screen.DripperDashboard.route),
    BottomNavItem("Activity", Icons.Outlined.Receipt, Screen.TransactionHistory.route),
    BottomNavItem("Profile", Icons.Outlined.Person, Screen.Settings.route),
)

@Composable
fun TranzoBottomBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    // Only show bottom bar on main screens
    val showBottomBar = bottomNavItems.any {
        currentDestination?.hierarchy?.any { dest ->
            dest.route == it.route
        } == true
    }

    if (showBottomBar) {
        NavigationBar(
            containerColor = Color.White,
            tonalElevation = 0.dp,
        ) {
            bottomNavItems.forEach { item ->
                val selected = currentDestination?.hierarchy?.any {
                    it.route == item.route
                } == true

                NavigationBarItem(
                    selected = selected,
                    alwaysShowLabel = true,
                    onClick = {
                        navController.navigate(item.route) {
                            popUpTo(Screen.Home.route) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label,
                        )
                    },
                    label = {
                        Text(
                            text = item.label,
                            style = MaterialTheme.typography.labelSmall,
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF1A1A1A),
                        selectedTextColor = Color(0xFF1A1A1A),
                        unselectedIconColor = Color(0xFFAAAAAA),
                        unselectedTextColor = Color(0xFFAAAAAA),
                        indicatorColor = Color(0xFFF0F0F0),
                    ),
                )
            }
        }
    }
}
