package com.pdevjay.proxect.presentation.navigation

import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.pdevjay.proxect.presentation.data.ProjectAddNav
import com.pdevjay.proxect.presentation.data.ProjectCalendarNav
import com.pdevjay.proxect.presentation.data.ProjectDashboardNav
import com.pdevjay.proxect.presentation.data.ProjectDetailNav
import com.pdevjay.proxect.presentation.data.ProjectEditNav
import com.pdevjay.proxect.presentation.data.ProjectForPresentation
import com.pdevjay.proxect.presentation.data.ProjectListNav
import com.pdevjay.proxect.presentation.data.ProjectSearchNav
import com.pdevjay.proxect.presentation.data.ProjectSettingsNav
import com.pdevjay.proxect.presentation.data.toDetailNav
import com.pdevjay.proxect.presentation.data.toEditNav
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
    projectViewModel: ProjectViewModel,
    projectListViewModel: ProjectListViewModel
) {
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = ProjectDashboardNav,
    ) {
        val routeDetail = ProjectDetailNav::class.qualifiedName!!
        val routeEdit = ProjectEditNav::class.qualifiedName!!
        val routeAdd = ProjectAddNav::class.qualifiedName!!

        composable<ProjectDashboardNav>(
            enterTransition = {
                fadeIn(
                    animationSpec = tween(200)
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(200)
                )
            }
        ) { backStackEntry ->
            DashboardScreen(
                navController = navController,
                projectViewModel,
                onNavigateToProjectDetail = { project ->
                    navController.navigate(project.toDetailNav())
                }
            )
        }
        composable<ProjectCalendarNav>(
            enterTransition = {
                fadeIn(
                    animationSpec = tween(200)
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(200)
                )
            }
        ) { backStackEntry ->
            CalendarScreen(
                navController = navController,
                projectViewModel = projectViewModel,
                onNavigateToList = { projectList ->
                    Log.e("listScreen", "1 : ${projectList.size}")


                    navController.navigate(ProjectListNav)
                },
            )
        }
        composable<ProjectSearchNav>(
            enterTransition = {
                fadeIn(
                    animationSpec = tween(200)
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(200)
                )
            }
        ) { backStackEntry ->
            ProjectSearchScreen(
                navController = navController,
                projectListViewModel = projectListViewModel
            )
        }
        composable<ProjectSettingsNav>(
            enterTransition = {
                fadeIn(
                    animationSpec = tween(200)
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(200)
                )
            }
        ) { backStackEntry ->
            SettingsScreen(navController = navController)
        }


        composable<ProjectDetailNav>(
            enterTransition = {
                val from = initialState.destination.route.orEmpty()
                val to = targetState.destination.route.orEmpty()

                if (from.startsWith(routeEdit) && to.startsWith(routeDetail)) {
                    slideInHorizontally(
                        initialOffsetX = { -it },
                        animationSpec = tween(200)
                    )
                } else {
                    slideInHorizontally(
                        initialOffsetX = { it },
                        animationSpec = tween(200)
                    )
                }
            },
            exitTransition = {
                val from = initialState.destination.route.orEmpty()
                val to = targetState.destination.route.orEmpty()

                if (from.startsWith(routeDetail) && to.startsWith(routeEdit)) {
                    slideOutHorizontally(
                        targetOffsetX = { -it },
                        animationSpec = tween(200)
                    )
                } else {
                    slideOutHorizontally(
                        targetOffsetX = { it },
                        animationSpec = tween(200)
                    )
                }
            }
        ) { backStackEntry -> // 타입으로 라우트 정의
            val project: ProjectDetailNav = backStackEntry.toRoute()
            ProjectDetailScreen(
                navController = navController,
                project = project.toPresentation(),
                projectViewModel = projectViewModel,
                onNavigateToEdit = { projectToEdit ->
                    navController.navigate(projectToEdit.toEditNav())
                })
        }
        composable<ProjectEditNav>(
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(200)
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(200)
                )
            }

        ) { backStackEntry -> // 타입으로 라우트 정의
            val project: ProjectEditNav = backStackEntry.toRoute()
            ProjectEditScreen(
                navController = navController,
                project = project.toPresentation(),
                projectViewModel = projectViewModel
            )
        }
        composable<ProjectAddNav>(
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(200)
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(200)
                )
            }

        ) { backStackEntry ->
            ProjectAddScreen(navController = navController, projectViewModel = projectViewModel)
        }
        composable<ProjectListNav>(
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(200)
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(200)
                )
            }

        ) { backStackEntry ->
            val projectList = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<List<ProjectForPresentation>>("projectList")
                ?: emptyList()
            Log.e("listScreen", "2: ${projectList.size}")

            ProjectListScreen(
                navController = navController,
            )
        }
    }
}
