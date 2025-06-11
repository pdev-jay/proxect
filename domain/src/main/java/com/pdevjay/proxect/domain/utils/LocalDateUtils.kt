package com.pdevjay.proxect.domain.utils

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset

// [1] epochMillis → LocalDate (기기 로컬 기준)
fun Long.toLocalDate(): LocalDate =
    Instant.ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()

// [2] LocalDate → epochMillis (UTC 기준 자정 millis)
fun LocalDate.toEpochMillis(): Long =
    this.atStartOfDay(ZoneOffset.UTC)
        .toInstant()
        .toEpochMilli()
