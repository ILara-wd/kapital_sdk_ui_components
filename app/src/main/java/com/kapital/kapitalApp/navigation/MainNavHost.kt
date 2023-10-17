package com.kapital.kapitalApp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.kapital.kapitalApp.screen.home.navigation.homeNavigationRoute
import com.kapital.kapitalApp.screen.home.navigation.homeScreen
import com.kapital.kapitalApp.screen.settings.navigation.settingsScreen

/**
 * Created by mertcantoptas on 19.03.2023
 */

@Composable
@Suppress("FunctionNaming")
fun MainNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = homeNavigationRoute
) {

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        homeScreen()
        settingsScreen()
    }
}
