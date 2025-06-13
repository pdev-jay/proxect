package com.pdevjay.proxect.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pdevjay.proxect.presentation.screen.calendar.CalendarScreen
import com.pdevjay.proxect.presentation.screen.home.DashboardScreen
import com.pdevjay.proxect.presentation.screen.lists.ProjectListScreen
import com.pdevjay.proxect.presentation.screen.lists.ProjectListViewModel
import com.pdevjay.proxect.presentation.screen.project.ProjectViewModel
import com.pdevjay.proxect.presentation.screen.settings.SettingsScreen


@Composable
fun MainNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    projectViewModel: ProjectViewModel,
    projectListViewModel: ProjectListViewModel
) {
    NavHost(navController, startDestination = BottomNavItem.Dashboard.route, modifier = modifier) {
        composable(BottomNavItem.Dashboard.route) { DashboardScreen(projectViewModel) }
//        composable(BottomNavItem.Home.route) { HomeScreen(projectViewModel) }
        composable(BottomNavItem.Calendar.route) { CalendarScreen(projectViewModel = projectViewModel) }
        composable(BottomNavItem.Lists.route) { ProjectListScreen(projectListViewModel = projectListViewModel) }
        composable(BottomNavItem.Settings.route) { SettingsScreen() }
    }
}
