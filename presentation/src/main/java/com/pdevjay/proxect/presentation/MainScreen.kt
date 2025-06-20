package com.pdevjay.proxect.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.pdevjay.proxect.presentation.navigation.BottomNavItem
import com.pdevjay.proxect.presentation.navigation.MainNavHost
import com.pdevjay.proxect.presentation.navigation.ProjectAdd


val LocalTopBarSetter = compositionLocalOf<(TopAppBarData) -> Unit> {
    error("No TopAppBar setter provided")
}

data class TopAppBarData(
    val title: String,
    val showBack: Boolean,
    val onBack: (() -> Unit)? = null,
    val actions: @Composable () -> Unit = {}
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    val currentParentRoute = currentBackStackEntry?.destination?.parent?.route

    var topBarData by remember {
        mutableStateOf(
            TopAppBarData(
                "Proxect",
                false,
            )
        )
    }

    CompositionLocalProvider(
        LocalTopBarSetter provides { topBarData = it }
    ) {
        Scaffold(
            topBar = {
                AnimatedContent(
                    targetState = topBarData,
                    transitionSpec = {
                        fadeIn(tween(300)) togetherWith fadeOut(tween(300))
                    },

                    ) { data ->

                    TopAppBar(
                        title = {
                            Text(data.title)
                        },
                        navigationIcon = {
                            if (data.showBack) {
                                Row(Modifier.animateContentSize()) {
                                    IconButton(
                                        onClick = {
                                            topBarData.onBack?.invoke()
                                                ?: navController.popBackStack()
                                        }
                                    ) {
                                        Icon(
                                            Icons.AutoMirrored.Filled.ArrowBack,
                                            contentDescription = "back"
                                        )
                                    }
                                }
                            }
                        },
                        actions = {
                            data.actions()
                        }
                    )
                }
            },
            bottomBar = {
                if (currentRoute != ProjectAdd.serializer().descriptor.serialName) {
                    NavigationBar {
                        BottomNavItem.items.forEach { item ->
                            val route = item.route::class.qualifiedName
                            val selected = currentParentRoute == route

                            NavigationBarItem(
                                selected = selected,
                                onClick = {
                                    if (currentParentRoute != item.route) {
                                        navController.navigate(item.route) {
                                            when (item.route) {
                                                is ProjectAdd -> {
                                                    launchSingleTop = true
                                                }

                                                else -> {
                                                    if (!selected) { // 현재 선택된 탭이 아닌 경우 (다른 탭으로 이동)
                                                        navController.navigate(item.route) {
                                                            // 다른 탭으로 이동할 때는 전체 NavHost의 시작점까지 팝업
                                                            popUpTo(navController.graph.findStartDestination().id) {
                                                                inclusive = false // 시작점은 제거하지 않음
                                                                saveState = true // 이전 탭의 상태 저장
                                                            }
                                                            restoreState = true // 새로운 탭의 상태 복원
                                                            launchSingleTop = true // 단일 인스턴스 유지
                                                        }
                                                    } else { // 현재 선택된 탭을 다시 클릭한 경우
                                                        // 현재 탭의 루트(중첩 내비게이션의 시작점)까지만 팝업
                                                        navController.navigate(item.route) {
                                                            popUpTo(item.route) { // item.route는 해당 중첩 그래프의 루트 객체
                                                                inclusive = false // 해당 루트는 제거하지 않음
                                                                saveState = true
                                                            }
                                                            restoreState = true
                                                            launchSingleTop = true // 단일 인스턴스 유지
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                },
                                icon = {
                                    Icon(item.icon, contentDescription = item.label)
                                },
                                alwaysShowLabel = false,
                            )
                        }
                    }
                }
            },
        ) { innerPadding ->
            MainNavHost(
                navController = navController,
                modifier = Modifier.padding(innerPadding),
            )
        }
    }
}