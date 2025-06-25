package com.pdevjay.proxect.presentation.screen.search

import com.pdevjay.proxect.domain.model.ProjectStatus

data class SearchState(
    val isStatusFilterActive: Boolean = false,
    val isDateFilterActive: Boolean = false,

    val selectedStatus: ProjectStatus? = null,
    val startDate: Long = 0L,
    val endDate: Long = 0L,

    val searchQuery: String = "",
    val isSearching: Boolean = false
)
