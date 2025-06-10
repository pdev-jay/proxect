package com.pdevjay.proxect.domain.model

data class Project(
    val id: String,
    val name: String,
    val description: String,
    val startDate: Long,
    val endDate: Long,
    val color: Long = 0xFF888888
)