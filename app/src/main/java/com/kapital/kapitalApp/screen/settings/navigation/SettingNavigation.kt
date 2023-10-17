package com.kapital.kapitalApp.screen.settings.navigation

import android.content.Intent
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.navDeepLink
import androidx.navigation.navArgument
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import com.kapital.kapitalApp.screen.settings.SettingsScreenRoute

const val settingsNavigationRoute = "settings_route"

fun NavController.navigateSettingsScreen(navOptions: NavOptions? = null) {
    this.navigate(settingsNavigationRoute, navOptions)
}

fun NavGraphBuilder.settingsScreen() {
    composable(
        route = settingsNavigationRoute,
        deepLinks = listOf(navDeepLink {
            uriPattern = "https://kapital/settings/{id}"
            action = Intent.ACTION_VIEW
        }),
        arguments = listOf(
            navArgument("id") {
                type = NavType.StringType
                defaultValue = ""
            }
        )
    ) { navBackStackEntry ->
        val argument = navBackStackEntry.arguments?.getString("id")
        SettingsScreenRoute(deepLinkData = argument)
    }
}
