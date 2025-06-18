package com.pdevjay.proxect.presentation.data

import kotlinx.serialization.Serializable

// 모든 최상위 @Serializable 라우트를 포괄하는 sealed interface 정의
@Serializable
sealed interface BottomNavRoute {
    // NavData.kt의 모든 최상위 라우트 객체들이 이 BottomNavRoute를 구현해야 합니다.
    // 예를 들어, MainDestination.DashboardGraph, ProjectAddNav, ProjectListNav, ProjectDetailNav, ProjectEditNav
}

// BottomNavItem을 제네릭으로 선언하고 T는 BottomNavRoute로 제한
//sealed class BottomNavItem<T : BottomNavRoute>( // @Serializable Any 대신 BottomNavRoute 사용
//    val route: T, // T 타입의 BottomNavRoute 객체 인스턴스
//    val icon: ImageVector,
//    val label: String
//) {
//    // 각 아이템 선언 시 제네릭 타입 명시
//    object Dashboard :
//        BottomNavItem<MainDestination.DashboardGraph>( // MainDestination.DashboardGraph는 BottomNavRoute를 구현합니다.
//            MainDestination.DashboardGraph,
//            Icons.Default.Dashboard,
//            "대시보드"
//        )
//
//    object Calendar :
//        BottomNavItem<MainDestination.CalendarGraph>( // MainDestination.CalendarGraph는 BottomNavRoute를 구현합니다.
//            MainDestination.CalendarGraph,
//            Icons.Default.CalendarMonth,
//            "달력"
//        )
//
//    object Plus : BottomNavItem<ProjectAdd>(ProjectAdd, Icons.Default.Add, "추가")
//
//    object Search :
//        BottomNavItem<MainDestination.SearchGraph>( // ProjectListNav는 BottomNavRoute를 구현합니다.
//            MainDestination.SearchGraph,
//            Icons.AutoMirrored.Filled.FormatListBulleted,
//            "목록"
//        )
//
//    object Settings :
//        BottomNavItem<MainDestination.SettingsGraph>( // MainDestination.SettingsGraph는 BottomNavRoute를 구현합니다.
//            MainDestination.SettingsGraph,
//            Icons.Default.Settings,
//            "설정"
//        )
//
//    companion object {
//        val items: List<BottomNavItem<*>> = listOf(Dashboard, Calendar, Plus, Search, Settings)
//    }
//}

//@Serializable
//sealed interface MainDestination : BottomNavRoute { // MainDestination이라는 부모 인터페이스 정의
//
//    @Serializable
//    data object DashboardGraph : MainDestination // 대시보드 중첩 그래프의 진입점 (인자 없음)
//
//    @Serializable
//    data object CalendarGraph : MainDestination // 캘린더 중첩 그래프의 진입점 (인자 없음)
//
//    @Serializable
//    data object SearchGraph : MainDestination
//
//    @Serializable
//    data object SettingsGraph : MainDestination
//}

//@Serializable
//sealed interface DashboardGraph {
@Serializable
data object DashboardGraph

@Serializable
data object Dashboard

@Serializable
data object ProjectDetail_Dashboard

@Serializable
data object ProjectEdit_Dashboard
//}

//@Serializable
//sealed interface CalendarGraph {
@Serializable
data object CalendarGraph

@Serializable
data object Calendar

@Serializable
data object ProjectList

@Serializable
data object ProjectDetail_Calendar

@Serializable
data object ProjectEdit_Calendar
//}

//@Serializable
//sealed interface SearchGraph {
@Serializable
data object SearchGraph

@Serializable
data object Search
//}

//@Serializable
//sealed interface SettingsGraph {
@Serializable
data object SettingsGraph

@Serializable
data object Settings
//}

@Serializable
data object ProjectAdd