package com.pdevjay.proxect.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.FormatListBulleted
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.pdevjay.proxect.presentation.data.ProjectAddNav
import com.pdevjay.proxect.presentation.data.ProjectCalendarNav
import com.pdevjay.proxect.presentation.data.ProjectDashboardNav
import com.pdevjay.proxect.presentation.data.ProjectSearchNav
import com.pdevjay.proxect.presentation.data.ProjectSettingsNav
import kotlinx.serialization.Serializable

//sealed class BottomNavItem(
//    val route: String,
//    val icon: ImageVector,
//    val label: String
//) {
//    object Dashboard : BottomNavItem("dashboard", Icons.Default.Dashboard, "대시보드")
//
//    object Calendar : BottomNavItem("calendar", Icons.Default.CalendarMonth, "달력")
//    object Plus : BottomNavItem("plus", Icons.Default.Add, "추가")
//    object Lists : BottomNavItem("lists", Icons.AutoMirrored.Filled.FormatListBulleted, "목록")
//    object Settings : BottomNavItem("settings", Icons.Default.Settings, "설정")
//
//    companion object {
//        val items = listOf(Dashboard, Calendar, Plus, Lists, Settings)
//    }
//}

// BottomNavItem을 제네릭으로 선언하고 T는 @Serializable Any로 제한
sealed class BottomNavItem<T : @Serializable Any>(
    val route: T, // T 타입의 @Serializable 객체 인스턴스
    val icon: ImageVector,
    val label: String
) {
    // 각 아이템 선언 시 제네릭 타입 명시
    object Dashboard :
        BottomNavItem<ProjectDashboardNav>(ProjectDashboardNav, Icons.Default.Dashboard, "대시보드")

    object Calendar :
        BottomNavItem<ProjectCalendarNav>(ProjectCalendarNav, Icons.Default.CalendarMonth, "달력")

    object Plus : BottomNavItem<ProjectAddNav>(ProjectAddNav, Icons.Default.Add, "추가")
    object Lists : BottomNavItem<ProjectSearchNav>(
        ProjectSearchNav,
        Icons.AutoMirrored.Filled.FormatListBulleted,
        "목록"
    )

    object Settings :
        BottomNavItem<ProjectSettingsNav>(ProjectSettingsNav, Icons.Default.Settings, "설정")

    companion object {
        val items: List<BottomNavItem<*>> = listOf(Dashboard, Calendar, Plus, Lists, Settings)
    }
}


