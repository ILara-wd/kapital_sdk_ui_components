package com.kapital.kapitalApp.screen.home.navigation

import android.content.Intent
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.navDeepLink
import androidx.navigation.navArgument
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import com.kapital.kapitalApp.screen.home.HomeScreenRoute


const val homeNavigationRoute = "home_route"

fun NavController.navigateHomeScreen(navOptions: NavOptions? = null) {
    this.navigate(homeNavigationRoute, navOptions)
}

fun NavGraphBuilder.homeScreen() {
    composable(
        route = homeNavigationRoute,
        deepLinks = listOf(navDeepLink {
            uriPattern = "https://kapital/dashboard/{name}"
            action = Intent.ACTION_VIEW
        }),
        arguments = listOf(
            navArgument("name") {
                type = NavType.StringType
                defaultValue = ""
            }
        )
    ) { navBackStackEntry ->
        val argument = navBackStackEntry.arguments?.getString("name")
        HomeScreenRoute(deepLinkData = argument)
    }
}
