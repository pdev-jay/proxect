package com.pdevjay.proxect.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.FormatListBulleted
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

sealed class BottomNavItem<T : @Serializable Any>(
    val route: T,
    val icon: ImageVector,
    val label: String
) {
    // 각 아이템 선언 시 제네릭 타입 명시
    object Dashboard :
        BottomNavItem<DashboardGraph>(
            DashboardGraph,
            Icons.Default.Dashboard,
            "대시보드"
        )

    object Calendar :
        BottomNavItem<CalendarGraph>(
            CalendarGraph,
            Icons.Default.CalendarMonth,
            "달력"
        )

    object Add : BottomNavItem<ProjectAdd>(ProjectAdd, Icons.Default.Add, "추가")

    object Search : BottomNavItem<SearchGraph>(
        SearchGraph,
        Icons.AutoMirrored.Filled.FormatListBulleted,
        "목록"
    )

    object Settings :
        BottomNavItem<SettingsGraph>(SettingsGraph, Icons.Default.Settings, "설정")

    companion object {
        val items: List<BottomNavItem<*>> = listOf(Dashboard, Calendar, Add, Search, Settings)
    }
}


