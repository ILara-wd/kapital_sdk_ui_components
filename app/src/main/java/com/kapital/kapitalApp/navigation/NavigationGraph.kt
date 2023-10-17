package com.kapital.kapitalApp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
@Suppress("FunctionNaming")
fun SetupNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Navigation.Onboarding.route,
        route = Navigation.root
    ) {
        onBoarding(navController = navController)
    }
}
