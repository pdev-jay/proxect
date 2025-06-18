package com.pdevjay.proxect.presentation

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.pdevjay.proxect.presentation.data.ProjectAdd
import com.pdevjay.proxect.presentation.data.ProjectDetail_Calendar
import com.pdevjay.proxect.presentation.data.ProjectDetail_Dashboard
import com.pdevjay.proxect.presentation.data.ProjectEdit_Calendar
import com.pdevjay.proxect.presentation.data.ProjectEdit_Dashboard
import com.pdevjay.proxect.presentation.navigation.BottomNavItem
import com.pdevjay.proxect.presentation.navigation.MainNavHost


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
    Log.e("currentRoute", "${currentRoute}")
    Log.e("currentRoute", "${ProjectAdd.serializer().descriptor.serialName}")
    var topBarData by remember {
        mutableStateOf(
            TopAppBarData(
                "Proxect",
                false,
            )
        )
    }

//    val hideBottomBar = when (currentRoute) {
//        is DashboardGraph,
//        is CalendarGraph,
//        is SearchGraph,
//        is SettingsGraph -> false
//
//        else -> true
//    }
    val screenToHideBottomBar = listOf(
        ProjectEdit_Dashboard.serializer().descriptor.serialName,
        ProjectDetail_Dashboard.serializer().descriptor.serialName,
        ProjectEdit_Calendar.serializer().descriptor.serialName,
        ProjectDetail_Calendar.serializer().descriptor.serialName,
        ProjectAdd.serializer().descriptor.serialName
    )

    val hideBottomBar = screenToHideBottomBar.contains(currentRoute)
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
                                    if (hideBottomBar) {
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
                            }
                        },
                        actions = {
                            data.actions()
                        }
                    )
                }
            },
            bottomBar = {
                if (!hideBottomBar) {
                    NavigationBar {
                        BottomNavItem.items.forEach { item ->
                            val selected = currentRoute == item.route

                            NavigationBarItem(
                                selected = selected,
                                onClick = {
                                    if (currentRoute != item.route) {
                                        navController.navigate(item.route) {
                                            when (item.route) {
//                                                is MainDestination -> {
//                                                    popUpTo(item.route) {
//                                                        inclusive = true
//                                                        saveState = true
//                                                    }
//                                                    restoreState = true
//                                                    launchSingleTop = true
//                                                }

                                                is ProjectAdd -> {
                                                    launchSingleTop = true
                                                }

                                                else -> {
                                                    popUpTo(item.route) {
                                                        inclusive = true
                                                        saveState = true
                                                    }
                                                    restoreState = true
                                                    launchSingleTop = true
                                                }
                                            }
                                        }
                                    }
                                },
                                icon = {
                                    if (item == BottomNavItem.Plus) {
                                        Box(
                                            modifier = Modifier
                                                .size(32.dp)
                                                .border(2.dp, Color.Gray, RoundedCornerShape(8.dp))
                                                .padding(2.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(item.icon, contentDescription = item.label)
                                        }
                                    } else {
                                        Icon(item.icon, contentDescription = item.label)
                                    }
                                },
                                alwaysShowLabel = false,
                            )
//                            if (item == BottomNavItem.Plus) {
//                                NavigationBarItem(
//                                    selected = false,
//                                    onClick = {
//                                        // 화면 전환 없이 액션만
//                                        navController.navigate(ProjectAddNav)
//                                    },
//                                    icon = {
//                                        Box(
//                                            modifier = Modifier
//                                                .size(32.dp) // 원하는 사이즈로 고정
//                                                .border(2.dp, Color.Gray, RoundedCornerShape(8.dp))
//                                                .padding(2.dp), // 아이콘 여백 조절
//                                            contentAlignment = Alignment.Center
//                                        ) {
//                                            Icon(item.icon, contentDescription = item.label)
//                                        }
//                                    },
//                                    alwaysShowLabel = false
//                                )
//                            } else {
//                                NavigationBarItem(
//                                    selected = currentRoute == item.route::class.qualifiedName,
//                                    onClick = {
//                                        navController.navigate(item.route)
//                                    },
//                                    icon = { Icon(item.icon, contentDescription = item.label) },
//                                    alwaysShowLabel = false
//                                )
//                            }
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