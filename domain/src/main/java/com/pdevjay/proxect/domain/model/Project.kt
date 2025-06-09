package com.pdevjay.proxect.domain.model

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

data class Project(
    val id: String,
    val name: String,
    val description: String,
    val startDate: Long,
    val endDate: Long
)

fun Long.toLocalDate(): LocalDate =
    Instant.ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
