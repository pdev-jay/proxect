package com.pdevjay.proxect.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdevjay.proxect.domain.model.Project
import com.pdevjay.proxect.domain.usecase.ProjectUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

//@HiltViewModel
//class MainViewModel @Inject constructor(
//    private val projectUseCases: ProjectUseCases
//) : ViewModel() {
//    val projects = projectUseCases.getProjects()
//        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
//    fun addProject(name: String, description: String) {
//        val newProject = Project(
//            id = UUID.randomUUID().toString(),
//            name = name,
//            description = description,
//            startDate = System.currentTimeMillis(),
//            endDate = System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000 // 1주일
//        )
//        viewModelScope.launch {
//            projectUseCases.addProject(newProject)
//        }
//    }
//
//    fun deleteProject(id: String) {
//        viewModelScope.launch {
//            projectUseCases.deleteProject(id)
//        }
//    }
//}
