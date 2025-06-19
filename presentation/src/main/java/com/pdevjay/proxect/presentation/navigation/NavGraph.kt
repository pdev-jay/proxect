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
import com.pdevjay.proxect.presentation.screen.calendar.CalendarScreen
import com.pdevjay.proxect.presentation.screen.home.DashboardScreen
import com.pdevjay.proxect.presentation.screen.lists.ProjectListViewModel
import com.pdevjay.proxect.presentation.screen.lists.ProjectSearchScreen
import com.pdevjay.proxect.presentation.screen.project.ProjectViewModel
import com.pdevjay.proxect.presentation.screen.project.add.ProjectAddScreen
import com.pdevjay.proxect.presentation.screen.project.detail.ProjectDetailScreen
import com.pdevjay.proxect.presentation.screen.project.edit.ProjectEditScreen
import com.pdevjay.proxect.presentation.screen.project.list.ProjectListScreen
import com.pdevjay.proxect.presentation.screen.settings.SettingsScreen


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
                enterTransition = { slideInFromLeft() },
                exitTransition = { slideOutToLeft() }
            ) { backStackEntry ->
                val parentEntry =
                    remember(backStackEntry) { navController.getBackStackEntry<DashboardGraph>() }
                val navSharedViewModel: NavSharedViewModel = hiltViewModel(parentEntry)
                DashboardScreen(
                    navSharedViewModel = navSharedViewModel,
                    projectViewModel,
                    onNavigateToProjectDetail = {
                        navController.navigate(ProjectDetail_Dashboard)
                    }
                )
            }

            composable<ProjectDetail_Dashboard>(
                enterTransition = {
                    val from = initialState.destination.route.orEmpty()
                    val to = targetState.destination.route.orEmpty()
                    if (from.startsWith(Dashboard.serializer().descriptor.serialName) && to.startsWith(
                            ProjectDetail_Dashboard.serializer().descriptor.serialName
                        )
                    ) {
                        slideInFromRight()
                    } else {
                        slideInFromLeft()
                    }
                },
                exitTransition = {
                    val from = initialState.destination.route.orEmpty()
                    val to = targetState.destination.route.orEmpty()
                    if (from.startsWith(ProjectDetail_Dashboard.serializer().descriptor.serialName) && to.startsWith(
                            Dashboard.serializer().descriptor.serialName
                        )
                    ) {
                        slideOutToRight()
                    } else {
                        slideOutToLeft()
                    }
                }
            ) { backStackEntry -> // 타입으로 라우트 정의
                val parentEntry =
                    remember(backStackEntry) { navController.getBackStackEntry<DashboardGraph>() }
                val navSharedViewModel: NavSharedViewModel = hiltViewModel(parentEntry)
                ProjectDetailScreen(
                    navSharedViewModel = navSharedViewModel,
                    projectViewModel = projectViewModel,
                    onNavigateToEdit = {
                        navController.navigate(ProjectEdit_Dashboard)
                    },
                    onPopBackStack = {
                        navController.popBackStack()
                    }
                )
            }

            composable<ProjectEdit_Dashboard>(
                enterTransition = { slideInFromRight() },
                exitTransition = { slideOutToRight() }

            ) { backStackEntry -> // 타입으로 라우트 정의
                val parentEntry =
                    remember(backStackEntry) { navController.getBackStackEntry<DashboardGraph>() }
                val navSharedViewModel: NavSharedViewModel = hiltViewModel(parentEntry)
                ProjectEditScreen(
                    navSharedViewModel = navSharedViewModel,
                    projectViewModel = projectViewModel,
                    onPopBackStack = {
                        navController.popBackStack()
                    }
                )
            }
        }

        navigation<CalendarGraph>(startDestination = Calendar) {

            composable<Calendar>(
                enterTransition = {
                    val from = initialState.destination.route.orEmpty()
                    val to = targetState.destination.route.orEmpty()
                    if (from.startsWith(Dashboard.serializer().descriptor.serialName) && to.startsWith(
                            Calendar.serializer().descriptor.serialName
                        )
                    ) {
                        slideInFromRight()
                    } else {
                        slideInFromLeft()
                    }
                },
                exitTransition = {
                    val from = initialState.destination.route.orEmpty()
                    val to = targetState.destination.route.orEmpty()
                    if (from.startsWith(Calendar.serializer().descriptor.serialName) && to.startsWith(
                            Dashboard.serializer().descriptor.serialName
                        )
                    ) {
                        slideOutToRight()
                    } else {
                        slideOutToLeft()
                    }
                }) { backStackEntry ->
                val parentEntry =
                    remember(backStackEntry) { navController.getBackStackEntry<CalendarGraph>() }
                val navSharedViewModel: NavSharedViewModel = hiltViewModel(parentEntry)

                CalendarScreen(
                    navSharedViewModel = navSharedViewModel,
                    projectViewModel = projectViewModel,
                    onNavigateToList = {
                        navController.navigate(ProjectList_Calendar)
                    },
                )
            }

            composable<ProjectList_Calendar>(
                enterTransition = {
                    val from = initialState.destination.route.orEmpty()
                    val to = targetState.destination.route.orEmpty()
                    if (from.startsWith(Calendar.serializer().descriptor.serialName) && to.startsWith(
                            ProjectList_Calendar.serializer().descriptor.serialName
                        )
                    ) {
                        slideInFromRight()
                    } else {

                        slideInFromLeft()
                    }
                },
                exitTransition = {
                    val from = initialState.destination.route.orEmpty()
                    val to = targetState.destination.route.orEmpty()
                    if (from.startsWith(ProjectList_Calendar.serializer().descriptor.serialName) && to.startsWith(
                            Calendar.serializer().descriptor.serialName
                        )
                    ) {
                        slideOutToRight()
                    } else {
                        slideOutToLeft()
                    }
                }

            ) { backStackEntry ->
                val parentEntry =
                    remember(backStackEntry) { navController.getBackStackEntry<CalendarGraph>() }
                val navSharedViewModel: NavSharedViewModel = hiltViewModel(parentEntry)

                ProjectListScreen(
                    navSharedViewModel = navSharedViewModel,
                    onNavigateToDetail = {
                        navController.navigate(ProjectDetail_Calendar)
                    }
                )
            }

            composable<ProjectDetail_Calendar>(
                enterTransition = {
                    val from = initialState.destination.route.orEmpty()
                    val to = targetState.destination.route.orEmpty()

                    if (from.startsWith(ProjectEdit_Calendar.serializer().descriptor.serialName) && to.startsWith(
                            ProjectDetail_Calendar.serializer().descriptor.serialName
                        )
                    ) {
                        slideInFromLeft()
                    } else {
                        slideInFromRight()
                    }

                },
                exitTransition = {
                    val from = initialState.destination.route.orEmpty()
                    val to = targetState.destination.route.orEmpty()

                    if (from.startsWith(ProjectDetail_Calendar.serializer().descriptor.serialName) && to.startsWith(
                            ProjectEdit_Calendar.serializer().descriptor.serialName
                        )
                    ) {
                        slideOutToLeft()
                    } else {
                        slideOutToRight()
                    }
                }
            ) { backStackEntry -> // 타입으로 라우트 정의
                val parentEntry =
                    remember(backStackEntry) { navController.getBackStackEntry<CalendarGraph>() }
                val navSharedViewModel: NavSharedViewModel = hiltViewModel(parentEntry)

                ProjectDetailScreen(
                    navSharedViewModel = navSharedViewModel,
                    projectViewModel = projectViewModel,
                    onNavigateToEdit = {
                        navController.navigate(ProjectEdit_Calendar)
                    },
                    onPopBackStack = {
                        navController.popBackStack()
                    }
                )
            }

            composable<ProjectEdit_Calendar>(
                enterTransition = { slideInFromRight() },
                exitTransition = { slideOutToRight() }

            ) { backStackEntry -> // 타입으로 라우트 정의
                val parentEntry =
                    remember(backStackEntry) { navController.getBackStackEntry<CalendarGraph>() }
                val navSharedViewModel: NavSharedViewModel = hiltViewModel(parentEntry)

                ProjectEditScreen(
                    navSharedViewModel = navSharedViewModel,
                    projectViewModel = projectViewModel,
                    onPopBackStack = {
                        navController.popBackStack()
                    }
                )
            }
        }
//
        navigation<SearchGraph>(startDestination = Search) {
            composable<Search>(
                enterTransition = {
                    val from = initialState.destination.route.orEmpty()
                    val to = targetState.destination.route.orEmpty()
                    if (from.startsWith(Settings.serializer().descriptor.serialName) && to.startsWith(
                            Search.serializer().descriptor.serialName
                        )
                    ) {
                        slideInFromLeft()
                    } else {
                        slideInFromRight()
                    }
                },
                exitTransition = {
                    val from = initialState.destination.route.orEmpty()
                    val to = targetState.destination.route.orEmpty()
                    if (from.startsWith(Search.serializer().descriptor.serialName) && to.startsWith(
                            Settings.serializer().descriptor.serialName
                        )
                    ) {
                        slideOutToLeft()
                    } else {
                        slideOutToRight()
                    }
                }) { backStackEntry ->
                ProjectSearchScreen(
                    navController = navController,
                    projectListViewModel = projectListViewModel
                )
            }
        }

        navigation<SettingsGraph>(startDestination = Settings) {


            composable<Settings>(
                enterTransition = { slideInFromRight() },
                exitTransition = { slideOutToRight() }
            ) { backStackEntry ->
                SettingsScreen(navController = navController)
            }

        }

        composable<ProjectAdd>(
            enterTransition = { slideInFromRight() },
            exitTransition = { slideOutToLeft() }

        ) { backStackEntry ->
            ProjectAddScreen(
                projectViewModel = projectViewModel,
                onPopBackStack = {
                    navController.popBackStack()
                }
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
        targetOffsetX = { -it },
        animationSpec = tween(200)
    )
}

val slideOutToRight: (() -> @JvmSuppressWildcards ExitTransition?) = {
    slideOutHorizontally(
        targetOffsetX = { it },
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