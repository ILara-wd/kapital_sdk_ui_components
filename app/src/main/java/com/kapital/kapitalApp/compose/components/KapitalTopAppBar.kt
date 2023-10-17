package com.kapital.kapitalApp.compose.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.Kapital.KapitalApp.R
import com.kapital.kapitalApp.compose.theme.colorReverseTheme
import com.kapital.kapitalApp.compose.utils.kapitalCommonBottomPadding
import com.kapital.kapitalApp.tools.getIconPassword

@Suppress("FunctionNaming")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KapitalTopAppBar(
    title: String,
    showLeftButton: Boolean = false,
    back: () -> Unit = {},
    showRightButton: Boolean = false,
    changeStateRightIcon: Boolean = false,
    rightAction: () -> Unit = {},
) {
    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        navigationIcon = {
            Box {
                if (showLeftButton) {
                    IconButton(
                        modifier = Modifier.align(Alignment.CenterStart),
                        onClick = { back() }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_arrow_back),
                            tint = colorReverseTheme(),
                            contentDescription = null
                        )
                    }
                }
            }
        },
        actions = {
            Box {
                if (showRightButton) {
                    IconButton(
                        modifier = Modifier.align(Alignment.CenterEnd),
                        onClick = { rightAction() }
                    ) {
                        Icon(
                            painter = painterResource(id = getIconPassword(changeStateRightIcon)),
                            tint = colorReverseTheme(),
                            contentDescription = null
                        )
                    }
                }
            }
        },
        title = {
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = colorReverseTheme(),
                        fontWeight = FontWeight.W700
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun preview() {
    Scaffold(
        topBar = {
            KapitalTopAppBar(
                title = stringResource(id = R.string.app_name),
                showLeftButton = true,
                showRightButton = true,
                back = { },
                changeStateRightIcon = true,
                rightAction = { }
            )
        },
        modifier = Modifier.kapitalCommonBottomPadding(),
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
            ) {}
        })
}
