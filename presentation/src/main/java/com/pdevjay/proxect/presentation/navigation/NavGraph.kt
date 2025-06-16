package com.pdevjay.proxect.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.pdevjay.proxect.presentation.data.ProjectAddNav
import com.pdevjay.proxect.presentation.data.ProjectDetailNav
import com.pdevjay.proxect.presentation.data.ProjectEditNav
import com.pdevjay.proxect.presentation.screen.calendar.CalendarScreen
import com.pdevjay.proxect.presentation.screen.home.DashboardScreen
import com.pdevjay.proxect.presentation.screen.lists.ProjectListScreen
import com.pdevjay.proxect.presentation.screen.lists.ProjectListViewModel
import com.pdevjay.proxect.presentation.screen.project.ProjectViewModel
import com.pdevjay.proxect.presentation.screen.project.add.ProjectAddScreen
import com.pdevjay.proxect.presentation.screen.project.detail.ProjectDetailScreen
import com.pdevjay.proxect.presentation.screen.project.edit.ProjectEditScreen
import com.pdevjay.proxect.presentation.screen.settings.SettingsScreen


@Composable
fun MainNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    projectViewModel: ProjectViewModel,
    projectListViewModel: ProjectListViewModel
) {
    NavHost(navController, startDestination = BottomNavItem.Dashboard.route, modifier = modifier) {
        composable(BottomNavItem.Dashboard.route) {
            DashboardScreen(
                navController = navController,
                projectViewModel
            )
        }
        composable(BottomNavItem.Calendar.route) {
            CalendarScreen(
                navController = navController,
                projectViewModel = projectViewModel
            )
        }
        composable(BottomNavItem.Lists.route) {
            ProjectListScreen(
                navController = navController,
                projectListViewModel = projectListViewModel
            )
        }
        composable(BottomNavItem.Settings.route) { SettingsScreen(navController = navController) }


        composable<ProjectDetailNav> { backStackEntry -> // 타입으로 라우트 정의
            val project: ProjectDetailNav = backStackEntry.toRoute()
            ProjectDetailScreen(navController = navController, project = project.toPresentation())
        }
        composable<ProjectEditNav> { backStackEntry -> // 타입으로 라우트 정의
            val project: ProjectEditNav = backStackEntry.toRoute()
            ProjectEditScreen(navController = navController, project = project.toPresentation())
        }
        composable<ProjectAddNav> { backStackEntry ->
            ProjectAddScreen(navController = navController)
        }
    }
}
