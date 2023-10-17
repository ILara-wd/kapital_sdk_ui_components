package com.kapital.kapitalApp.screen.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag

@Composable
@Suppress("FunctionNaming")
internal fun HomeScreenRoute(
    modifier: Modifier = Modifier,
    deepLinkData: String? = null
) {
    HomeScreen(modifier, deepLinkData)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Suppress("FunctionNaming")
fun HomeScreen(
    modifier: Modifier = Modifier,
    deepLinkData: String? = null
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = {
                    Text(text = "Home Screen")
                },
            )
        },
        content = { paddingValues ->
            Content(modifier = Modifier.padding(paddingValues), deepLinkData)
        }
    )
}

@Composable
@Suppress("FunctionNaming")
fun Content(modifier: Modifier = Modifier, deepLinkData: String? = null) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "This is Home Screen $deepLinkData",modifier = Modifier.testTag("dashboardDeeplinkArgument"), color = Color.Blue)
    }
}
