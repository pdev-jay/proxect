package com.pdevjay.proxect.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pdevjay.proxect.presentation.screen.project.ProjectViewModel
import com.pdevjay.proxect.presentation.screen.home.HomeScreen
import com.pdevjay.proxect.presentation.screen.lists.ProjectListScreen
import com.pdevjay.proxect.presentation.screen.settings.SettingsScreen
import com.pdevjay.proxect.presentation.screen.calendar.CalendarScreen


@Composable
fun MainNavHost(navController: NavHostController, modifier: Modifier = Modifier, projectViewModel: ProjectViewModel) {
    NavHost(navController, startDestination = BottomNavItem.Home.route, modifier = modifier) {
        composable(BottomNavItem.Home.route) { HomeScreen(projectViewModel) }
        composable(BottomNavItem.Calendar.route) { CalendarScreen(projectViewModel = projectViewModel) }
        composable(BottomNavItem.Lists.route) { ProjectListScreen() }
        composable(BottomNavItem.Settings.route) { SettingsScreen() }
    }
}
