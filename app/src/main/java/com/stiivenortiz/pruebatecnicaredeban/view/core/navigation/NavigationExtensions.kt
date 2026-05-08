package com.stiivenortiz.pruebatecnicaredeban.view.core.navigation

import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.navOptions

fun NavController.navigateSafe(route: Any) {
    val currentRoute = currentBackStackEntry?.destination?.route
    val targetRoute = route::class.qualifiedName

    if (currentRoute != targetRoute)
        this.navigate(route)
}

fun NavController.navigateSafe(
    route: Any,
    builder: (NavOptionsBuilder.() -> Unit)? = null
) {
    val currentRoute = currentBackStackEntry?.destination?.route
    val targetRoute = route::class.qualifiedName

    if (currentRoute != targetRoute) {
        if (builder != null) {
            this.navigate(route, navOptions(builder))
        } else {
            this.navigate(route)
        }
    }
}

inline fun <reified T : Any> NavController.popBackStackSafe() {
    if (currentBackStackEntry?.destination?.hasRoute<T>() == true)
        this.popBackStack()
}