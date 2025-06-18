package com.pdevjay.proxect.presentation.screen.home


//@Composable
//fun HomeScreen(projectViewModel: ProjectViewModel) {
//    val projects by projectViewModel.projectsForHome.collectAsState()
//
//    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
//    var isModalVisible by remember { mutableStateOf(false) }
//    var selectedProject by remember { mutableStateOf<ProjectForPresentation?>(null) }
//
//    val today = remember { LocalDate.now() }
//
//    val todayProjects = remember(projects) {
//        projects.filter { it.startDate.toUTCLocalDate() <= today && it.endDate.toUTCLocalDate() >= today }
//    }
//
//    val otherProjects = remember(projects) {
//        val oneWeekAgo = today.minusDays(7)
//        projects.filter {
//            it.endDate.toUTCLocalDate() in oneWeekAgo..today.minusDays(1)
//        }
//    }
//
//    val futureProjects = remember(projects) {
//        val oneWeekLater = today.plusDays(7)
//        projects.filter {
//            it.startDate.toUTCLocalDate() in today.plusDays(1)..oneWeekLater
//        }
//    }
//
//    if (isModalVisible && selectedDate != null) {
//        ProjectDialog(
//            navController = rememberNavController(),
//            initialContentType = DialogContentType.ProjectDetail,
//            selectedDate = selectedDate!!,
//            initialSelectedProject = selectedProject,
//            onDismiss = {
//                isModalVisible = false
//            },
//            onDelete = {
//                projectViewModel.deleteProject(it)
//            },
//            onUpdate = {
//                projectViewModel.updateProject(it)
//            }
//        )
//
//    }
//
//    LazyColumn(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(horizontal = 16.dp),
//        verticalArrangement = Arrangement.spacedBy(4.dp)
//    ) {
//        if (todayProjects.isNotEmpty()) {
//            item {
//                Text(
//                    text = "진행 중",
//                    style = MaterialTheme.typography.titleLarge,
//                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
//                )
//            }
//            items(todayProjects) { project ->
//                ProjectCard(project = project) {
//                    selectedProject = project
//                    selectedDate = project.startDate.toUTCLocalDate()
//                    isModalVisible = true
//                }
//            }
//        }
//
//        if (futureProjects.isNotEmpty()) {
//            item {
//                Text(
//                    text = "시작 예정",
//                    style = MaterialTheme.typography.titleLarge,
//                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
//                )
//            }
//            items(futureProjects) { project ->
//                ProjectCard(project = project) {
//                    selectedProject = project
//                    selectedDate = project.startDate.toUTCLocalDate()
//                    isModalVisible = true
//                }
//            }
//        }
//
//        if (otherProjects.isNotEmpty()) {
//            item {
//                Text(
//                    text = "종료된 프로젝트",
//                    style = MaterialTheme.typography.titleLarge,
//                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
//                )
//            }
//            items(otherProjects) { project ->
//                ProjectCard(project = project) {
//                    selectedProject = project
//                    selectedDate = project.startDate.toUTCLocalDate()
//                    isModalVisible = true
//                }
//            }
//        }
//    }
//}
//
//
