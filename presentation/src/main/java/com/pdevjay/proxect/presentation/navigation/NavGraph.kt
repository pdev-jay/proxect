package com.pdevjay.proxect.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.pdevjay.proxect.presentation.data.Dashboard
import com.pdevjay.proxect.presentation.data.DashboardGraph
import com.pdevjay.proxect.presentation.data.ProjectAdd
import com.pdevjay.proxect.presentation.data.ProjectDetail_Dashboard
import com.pdevjay.proxect.presentation.data.ProjectEdit_Dashboard
import com.pdevjay.proxect.presentation.screen.home.DashboardScreen
import com.pdevjay.proxect.presentation.screen.lists.ProjectListViewModel
import com.pdevjay.proxect.presentation.screen.project.ProjectViewModel
import com.pdevjay.proxect.presentation.screen.project.add.ProjectAddScreen
import com.pdevjay.proxect.presentation.screen.project.detail.ProjectDetailScreen
import com.pdevjay.proxect.presentation.screen.project.edit.ProjectEditScreen


@Composable
fun MainNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val projectViewModel: ProjectViewModel = hiltViewModel()
    val projectListViewModel: ProjectListViewModel = hiltViewModel()
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = DashboardGraph,
    ) {

        navigation<DashboardGraph>(startDestination = Dashboard) {

            composable<Dashboard>(
                enterTransition = { defaultFadeIn() },
                exitTransition = { defaultFadeOut() }
            ) { backStackEntry ->
                val parentEntry =
                    remember(backStackEntry) { navController.getBackStackEntry<DashboardGraph>() }
                val navSharedViewModel: NavSharedViewModel = hiltViewModel(parentEntry)
                DashboardScreen(
                    navController = navController,
                    navSharedViewModel = navSharedViewModel,
                    projectViewModel,
                    onNavigateToProjectDetail = { project ->
                        navSharedViewModel.setProject(project)
                        navController.navigate(ProjectDetail_Dashboard)
                    }
                )
            }

            composable<ProjectDetail_Dashboard> { backStackEntry -> // 타입으로 라우트 정의
                val parentEntry =
                    remember(backStackEntry) { navController.getBackStackEntry<DashboardGraph>() }
                val navSharedViewModel: NavSharedViewModel = hiltViewModel(parentEntry)
                ProjectDetailScreen(
                    navController = navController,
                    navSharedViewModel = navSharedViewModel,
                    projectViewModel = projectViewModel,
                    onNavigateToEdit = {
                        navController.navigate(ProjectEdit_Dashboard)
                    })
            }

            composable<ProjectEdit_Dashboard>(
                enterTransition = { slideInFromRight() },
                exitTransition = { slideOutToLeft() }

            ) { backStackEntry -> // 타입으로 라우트 정의
                val parentEntry =
                    remember(backStackEntry) { navController.getBackStackEntry<DashboardGraph>() }
                val navSharedViewModel: NavSharedViewModel = hiltViewModel(parentEntry)
                ProjectEditScreen(
                    navController = navController,
                    navSharedViewModel = navSharedViewModel,
                    projectViewModel = projectViewModel
                )
            }
        }

//        navigation<MainDestination.CalendarGraph>(startDestination = CalendarGraph.Calendar) {
//
//            composable<CalendarGraph.Calendar>(
//                enterTransition = { defaultFadeIn() },
//                exitTransition = { defaultFadeOut() }
//            ) { backStackEntry ->
//                CalendarScreen(
//                    navController = navController,
//                    navSharedViewModel = navSharedViewModel,
//                    projectViewModel = projectViewModel,
//                    onNavigateToList = { projectList ->
//                        //                    Log.e("listScreen", "1 : ${projectList.size}")
//
//                        navController.navigate(ProjectListNav)
//                    },
//                )
//            }
//
//            composable<CalendarGraph.ProjectList>(
//                enterTransition = { slideInFromRight() },
//                exitTransition = { slideOutToLeft() }
//
//            ) { backStackEntry ->
//                val projectList = remember {
//                    navController.getNavigationInput<List<ProjectForPresentation>>("project_list")
//                } ?: emptyList()
//                ProjectListScreen(
//                    navController = navController,
//                    navSharedViewModel = navSharedViewModel,
//                    projectList = projectList,
//                )
//            }
//
//            composable<CalendarGraph.ProjectDetail>(
//                enterTransition = {
//                    val from = initialState.destination.route.orEmpty()
//                    val to = targetState.destination.route.orEmpty()
//
//                    if (from.startsWith(routeEdit) && to.startsWith(routeDetail)) {
//                        slideInFromLeft()
//                    } else {
//                        slideInFromRight()
//                    }
//
//                },
//                exitTransition = {
//                    val from = initialState.destination.route.orEmpty()
//                    val to = targetState.destination.route.orEmpty()
//
//                    if (from.startsWith(routeDetail) && to.startsWith(routeEdit)) {
//                        slideOutToLeft()
//                    } else {
//                        slideOutToRight()
//                    }
//                }
//            ) { backStackEntry -> // 타입으로 라우트 정의
//                val project: ProjectDetailNav = backStackEntry.toRoute()
//                ProjectDetailScreen(
//                    navController = navController,
//                    navSharedViewModel = navSharedViewModel,
//                    project = project.toPresentation(),
//                    projectViewModel = projectViewModel,
//                    onNavigateToEdit = { project ->
//                        navController.navigate(project.toEditNav())
//                    })
//            }
//
//            composable<CalendarGraph.ProjectEdit>(
//                enterTransition = { slideInFromRight() },
//                exitTransition = { slideOutToLeft() }
//
//            ) { backStackEntry -> // 타입으로 라우트 정의
//                val project: ProjectEditNav = backStackEntry.toRoute()
//                ProjectEditScreen(
//                    navController = navController,
//                    navSharedViewModel = navSharedViewModel,
//                    project = project.toPresentation(),
//                    projectViewModel = projectViewModel
//                )
//            }
//        }
//
//        navigation<MainDestination.SearchGraph>(startDestination = SearchGraph.Search) {
//            composable<SearchGraph.Search>(
//                enterTransition = { defaultFadeIn() },
//                exitTransition = { defaultFadeOut() }
//            ) { backStackEntry ->
//                ProjectSearchScreen(
//                    navController = navController,
//                    projectListViewModel = projectListViewModel
//                )
//            }
//        }
//
//        navigation<MainDestination.SettingsGraph>(startDestination = SettingsGraph.Settings) {
//
//
//            composable<SettingsGraph.Settings>(
//                enterTransition = { defaultFadeIn() },
//                exitTransition = { defaultFadeOut() }
//            ) { backStackEntry ->
//                SettingsScreen(navController = navController)
//            }
//
//        }
//
        composable<ProjectAdd>(
            enterTransition = { slideInFromRight() },
            exitTransition = { slideOutToLeft() }

        ) { backStackEntry ->
            ProjectAddScreen(
                navController = navController,
//                navSharedViewModel = navSharedViewModel,
                projectViewModel = projectViewModel
            )
        }
    }
}

val slideInFromRight: () -> EnterTransition = {
    slideInHorizontally(
        initialOffsetX = { it },
        animationSpec = tween(200)
    )
}

val slideInFromLeft: (() -> @JvmSuppressWildcards EnterTransition?) = {
    slideInHorizontally(
        initialOffsetX = { -it },
        animationSpec = tween(200)
    )
}

val slideOutToLeft: (() -> @JvmSuppressWildcards ExitTransition?) = {
    slideOutHorizontally(
        targetOffsetX = { it },
        animationSpec = tween(200)
    )
}

val slideOutToRight: (() -> @JvmSuppressWildcards ExitTransition?) = {
    slideOutHorizontally(
        targetOffsetX = { -it },
        animationSpec = tween(200)
    )
}

val defaultFadeIn: (() -> EnterTransition?) = {
    fadeIn(
        animationSpec = tween(200)
    )
}

val defaultFadeOut: (() -> ExitTransition?) = {
    fadeOut(
        animationSpec = tween(200)
    )
}