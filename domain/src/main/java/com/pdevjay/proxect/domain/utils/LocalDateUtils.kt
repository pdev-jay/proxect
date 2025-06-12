package com.pdevjay.proxect.domain.utils

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun Long.toUTCLocalDate(): LocalDate =
    Instant.ofEpochMilli(this)
        .atZone(ZoneOffset.UTC)
        .toLocalDate()

fun LocalDate.toEpochMillis(): Long =
    this.atStartOfDay(ZoneOffset.UTC)
        .toInstant()
        .toEpochMilli()

fun Long.toDateFromPicker(): LocalDate =
    Instant.ofEpochMilli(this)
        .atZone(ZoneOffset.UTC)
        .toLocalDate()

/**
 * UTC epoch milli to UTC LocalDate
 */
val formatDate: (Long) -> String = {
    SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }
        .format(Date(it))
}
