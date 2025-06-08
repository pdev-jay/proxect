package com.pdevjay.proxect.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.pdevjay.proxect.presentation.navigation.BottomNavItem
import com.pdevjay.proxect.presentation.navigation.MainNavHost

//@Composable
//fun MainScreen(navController: NavHostController, viewModel: MainViewModel = hiltViewModel()) {
//    val projectList by viewModel.projects.collectAsState()
//
//    var name by remember { mutableStateOf("") }
//    var description by remember { mutableStateOf("") }
//
//    Column(modifier = Modifier.padding(16.dp)) {
//
//        Text("Add Project", style = MaterialTheme.typography.titleLarge)
//
//        OutlinedTextField(
//            value = name,
//            onValueChange = { name = it },
//            label = { Text("Name") },
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        OutlinedTextField(
//            value = description,
//            onValueChange = { description = it },
//            label = { Text("Description") },
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        Button(
//            onClick = {
//                viewModel.addProject(name, description)
//                name = ""
//                description = ""
//            },
//            modifier = Modifier.padding(top = 8.dp)
//        ) {
//            Text("Add Project")
//        }
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        Text("Projects", style = MaterialTheme.typography.titleLarge)
//
//        LazyColumn {
//            items(projectList) { project ->
//                ProjectItem(project = project, onDelete = { viewModel.deleteProject(project.id) })
//            }
//        }
//    }
//}
//
//@Composable
//fun ProjectItem(project: Project, onDelete: () -> Unit) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 4.dp)
//    ) {
//        Row(
//            modifier = Modifier
//                .padding(12.dp),
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            Column(modifier = Modifier.weight(1f)) {
//                Text(project.name, style = MaterialTheme.typography.titleMedium)
//                Text(project.description, style = MaterialTheme.typography.bodySmall)
//            }
//
//            IconButton(onClick = onDelete) {
//                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
//            }
//        }
//    }
//}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    Scaffold(
        bottomBar = {
            NavigationBar {
                BottomNavItem.items.forEach { item ->
                    NavigationBarItem(
                        selected = item.route == currentRoute,
                        onClick = {
                            if (item.route != currentRoute) {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) }
                    )
                }
            }
        }
    ) { innerPadding ->
        MainNavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}
