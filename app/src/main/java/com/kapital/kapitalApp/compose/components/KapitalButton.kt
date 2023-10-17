package com.kapital.kapitalApp.compose.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.Kapital.KapitalApp.R
import com.kapital.kapitalApp.compose.theme.buttonTextStyle

@Suppress("FunctionNaming")
@Composable
fun KapitalButton(
    modifier: Modifier = Modifier,
    label: AnnotatedString,
    enabled: Boolean = true,
    colorText: Color = Color.White,
    isBoldText: Boolean = false,
    onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = MaterialTheme.shapes.small,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(disabledContentColor = Color.White)
    ) {
        Text(
            label,
            fontWeight = if (isBoldText) FontWeight.Bold else FontWeight.Normal,
            color = colorText,
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.normal_padding)),
            style = MaterialTheme.typography.buttonTextStyle()
        )
    }
}

@Suppress("FunctionNaming")
@Composable
fun KapitalButton(
    label: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colorText: Color = Color.White,
    isBoldText: Boolean = false,
    onClick: () -> Unit = {}
) {
    KapitalButton(
        label = buildAnnotatedString { append(label) },
        modifier = modifier,
        enabled = enabled,
        colorText = colorText,
        isBoldText = isBoldText,
        onClick = onClick
    )
}

@Composable
@Preview
fun viewButton() {
    KapitalButton(
        label = "Hola"
    ) {

    }
}
