package com.kapital.kapitalApp.navigation

import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.Kapital.KapitalApp.R
import com.kapital.kapitalApp.compose.components.KapitalConfirmationDialog

fun NavGraphBuilder.onBoarding(navController: NavController) {
    navigation(
        route = Navigation.Onboarding.route,
        startDestination = Navigation.Onboarding.tutorial
    ) {
        composable(Navigation.Onboarding.tutorial) {
            KapitalConfirmationDialog(
                confirmTextButton = stringResource(id = R.string.text_alert_accept),
                title = stringResource(id = R.string.text_alert_title_logout),
                confirmButton = {
                    navController.popBackStack()
                }
            )
        }
    }
}
