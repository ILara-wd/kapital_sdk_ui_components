package com.kapital.kapitalApp.compose.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.Kapital.KapitalApp.R
import com.kapital.kapitalApp.compose.theme.FunGreyDisabled
import com.kapital.kapitalApp.compose.theme.colorReverseTheme
import com.kapital.kapitalApp.compose.theme.disabledOutlinedButtonColor

@Composable
@Suppress("FunctionNaming")
fun KapitalOutlinedButton(
    label: String,
    modifier: Modifier = Modifier,
    enabled: Boolean,
    onClick: () -> Unit = {}
) {
    BoxWithConstraints {
        val fontSize = if (maxWidth < 400.dp) 14.sp else 16.sp
        OutlinedButton(
            onClick = onClick,
            modifier = modifier,
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = colorReverseTheme()
            ),
            border = BorderStroke(
                1.dp,
                if (enabled) {
                    colorReverseTheme()
                } else {
                    MaterialTheme.colorScheme.disabledOutlinedButtonColor()
                }
            ),
            enabled = enabled
        ) {
            Text(
                label.uppercase(),
                color = if (enabled) colorReverseTheme() else FunGreyDisabled,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.normal_padding)),
                style = MaterialTheme.typography.headlineMedium.copy(fontSize = fontSize)
            )
        }
    }
}
