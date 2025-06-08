package com.pdevjay.proxect.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pdevjay.proxect.presentation.screen.HomeScreen
import com.pdevjay.proxect.presentation.screen.ListScreen
import com.pdevjay.proxect.presentation.screen.SettingsScreen
import com.pdevjay.proxect.presentation.screen.calendar.CalendarScreen


@Composable
fun MainNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController, startDestination = BottomNavItem.Home.route, modifier = modifier) {
        composable(BottomNavItem.Home.route) { HomeScreen() }
        composable(BottomNavItem.Calendar.route) { CalendarScreen() }
        composable(BottomNavItem.Lists.route) { ListScreen() }
        composable(BottomNavItem.Settings.route) { SettingsScreen() }
    }
}
