@file:OptIn(ExperimentalComposeUiApi::class)

package com.kapital.kapitalApp.screen.main

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.navigation.compose.rememberNavController
import com.kapital.kapitalApp.navigation.MainNavHost
import com.kapital.kapitalApp.screen.MainAppScaffold

@Composable
@Suppress("FunctionNaming")
fun MainApp(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()

    MainAppScaffold(
        modifier = modifier.semantics {
            testTagsAsResourceId = true
        },
        backgroundColor = MaterialTheme.colorScheme.background,
    ) {
        MainNavHost(
            navController = navController
        )
    }
}
