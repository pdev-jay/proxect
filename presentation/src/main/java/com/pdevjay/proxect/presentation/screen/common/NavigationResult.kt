package com.pdevjay.proxect.presentation.screen.common

import androidx.navigation.NavController

inline fun <reified T> NavController.getNavigationResult(key: String): T? {
    val result = currentBackStackEntry?.savedStateHandle?.get<T>(key)
    currentBackStackEntry?.savedStateHandle?.remove<T>(key)
    return result
}

inline fun <reified T> NavController.getNavigationInput(key: String): T? {
    val result = previousBackStackEntry?.savedStateHandle?.get<T>(key)
    previousBackStackEntry?.savedStateHandle?.remove<T>(key)
    return result
}

inline fun <reified T> NavController.setNavigationResultToPrevious(key: String, value: T) {
    previousBackStackEntry?.savedStateHandle?.set(key, value)
}

inline fun <reified T> NavController.setNavigationResult(key: String, value: T) {
    currentBackStackEntry?.savedStateHandle?.set(key, value)
}
