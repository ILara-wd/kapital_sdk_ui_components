package com.kapital.kapitalApp.compose.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.Kapital.KapitalApp.R
import com.kapital.kapitalApp.compose.theme.Black
import com.kapital.kapitalApp.compose.theme.White
import com.kapital.kapitalApp.compose.utils.kapitalCommonBottomPadding
import com.kapital.kapitalApp.compose.utils.kapitalCommonHorizontalPadding
import com.kapital.kapitalApp.compose.utils.kapitalCommonTopPadding
import com.kapital.kapitalApp.compose.utils.kapitalDialogHorizontalPadding

@OptIn(ExperimentalComposeUiApi::class)
@Suppress("FunctionNaming")
@Composable
fun KapitalDialog(
    confirmButton: () -> Unit,
    confirmTextButton: String = "",
    dismissButton: () -> Unit,
    dismissTextButton: String = "",
    title: String = ""
) {
    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        BoxDialogAlertKapital(
            confirmButton,
            confirmTextButton,
            dismissButton,
            dismissTextButton,
            title
        )
    }
}

@Suppress("FunctionNaming", "LongMethod")
@Composable
fun BoxDialogAlertKapital(
    confirmButton: () -> Unit,
    confirmTextButton: String = "",
    dismissButton: () -> Unit,
    dismissTextButton: String = "",
    title: String = ""
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
        ) {
            Card(
                modifier = Modifier
                    .kapitalCommonTopPadding()
                    .kapitalDialogHorizontalPadding()
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.End
                ) {
                    IconButton(
                        onClick = { dismissButton() }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_close),
                            contentDescription = stringResource(R.string.logout_description)
                        )
                    }
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Spacer(Modifier.size(60.dp))
                    Text(
                        text = title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .kapitalCommonHorizontalPadding(),
                        textAlign = TextAlign.Center
                    )
                    KapitalButton(
                        label = confirmTextButton,
                        modifier = Modifier
                            .fillMaxWidth()
                            .kapitalCommonHorizontalPadding()
                            .kapitalCommonTopPadding()
                            .kapitalCommonBottomPadding()
                    ) { confirmButton() }
                    KapitalButton(
                        label = dismissTextButton,
                        modifier = Modifier
                            .fillMaxWidth()
                            .kapitalCommonHorizontalPadding()
                            .kapitalCommonTopPadding()
                            .kapitalCommonBottomPadding()
                    ) { dismissButton() }
                }
            }
        }
    }
}

@Suppress("FunctionNaming")
@Composable
fun KapitalConfirmationDialog(
    confirmButton: () -> Unit,
    confirmTextButton: String = "",
    title: String = ""
) {
    AlertDialog(
        onDismissRequest = {},
        confirmButton = {
            KapitalButton(
                label = confirmTextButton,
                modifier = Modifier
                    .padding(
                        start = 8.dp,
                        top = 8.dp,
                        end = 8.dp,
                        bottom = 8.dp
                    )
                    .fillMaxWidth()
            ) {
                confirmButton()
            }
        },
        title = {
            KapitalLabel(
                label = title,
                fontSize = 20.sp,
                color = if (isSystemInDarkTheme()) White else Black,
                textAlign = TextAlign.Center,
                isBoldText = true,
                modifier = Modifier.fillMaxWidth()
            )
        }
    )
}

@Suppress("FunctionNaming")
@Preview
@Composable
fun Preview() {
    KapitalConfirmationDialog(
        confirmTextButton = stringResource(id = R.string.text_alert_accept),
        title = stringResource(id = R.string.text_alert_title_logout),
        confirmButton = {}
    )
}
