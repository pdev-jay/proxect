package com.pdevjay.proxect.presentation

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.pdevjay.proxect.presentation.data.ProjectAddNav
import com.pdevjay.proxect.presentation.data.ProjectForPresentation
import com.pdevjay.proxect.presentation.navigation.BottomNavItem
import com.pdevjay.proxect.presentation.navigation.MainNavHost
import com.pdevjay.proxect.presentation.screen.lists.ProjectListViewModel
import com.pdevjay.proxect.presentation.screen.project.ProjectAddDialog
import com.pdevjay.proxect.presentation.screen.project.ProjectViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    var showAddDialog by remember { mutableStateOf(false) }
    val projectViewModel: ProjectViewModel = hiltViewModel()
    val projectListViewModel: ProjectListViewModel = hiltViewModel()

    var projectToAdd by remember { mutableStateOf<ProjectForPresentation?>(null) }

    if (showAddDialog) {
        ProjectAddDialog(
            projectToAdd = projectToAdd,
            onDismiss = {
                showAddDialog = false
                projectToAdd = null
            },
            onChange = { projectToAdd = it },
            onConfirm = {
                if (projectToAdd != null) {
                    if (projectToAdd!!.name.isNotBlank()) {
                        projectViewModel.addProject(projectToAdd!!)
                        showAddDialog = false
                        projectToAdd = null
                    }
                }
            },
            viewModel = projectViewModel
        )
    }


    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = Color(0xFFF3F3FA)
                ),
                title = { Text("Proxect") },
            )
        },
        bottomBar = {
            //            if (currentRoute != ProjectNavItem.Add.route && currentRoute != ProjectNavItem.Edit.route && currentRoute != ProjectNavItem.Detail.route) {
            NavigationBar {
                BottomNavItem.items.forEach { item ->
                    if (item == BottomNavItem.Plus) {
                        NavigationBarItem(
                            selected = false,
                            onClick = {
                                // 화면 전환 없이 액션만
//                                    showAddDialog = true
                                navController.navigate(ProjectAddNav)
                            },
                            icon = {
                                Box(
                                    modifier = Modifier
                                        .size(32.dp) // 원하는 사이즈로 고정
                                        .border(2.dp, Color.Gray, RoundedCornerShape(8.dp))
                                        .padding(2.dp), // 아이콘 여백 조절
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(item.icon, contentDescription = item.label)
                                }
                            },
                            alwaysShowLabel = false
                        )
                    } else {
                        NavigationBarItem(
                            selected = currentRoute == item.route,
                            onClick = {
                                navController.navigate(item.route)
                            },
                            icon = { Icon(item.icon, contentDescription = item.label) },
                            alwaysShowLabel = false
                        )
                    }
                }
            }
        },
    ) { innerPadding ->
        MainNavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding),
            projectViewModel = projectViewModel,
            projectListViewModel = projectListViewModel
        )
    }
}
