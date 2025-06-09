package com.pdevjay.proxect.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.FormatListBulleted
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.FormatListBulleted
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
) {
    object Home : BottomNavItem("home", Icons.Default.Home, "홈")
    object Calendar : BottomNavItem("calendar", Icons.Default.CalendarMonth, "달력")
    object Plus : BottomNavItem("plus", Icons.Default.Add, "추가")
    object Lists : BottomNavItem("lists", Icons. AutoMirrored.Filled.FormatListBulleted, "목록")
    object Settings : BottomNavItem("settings", Icons.Default.Settings, "설정")

    companion object {
        val items = listOf(Home, Calendar, Plus, Lists, Settings)
    }
}
