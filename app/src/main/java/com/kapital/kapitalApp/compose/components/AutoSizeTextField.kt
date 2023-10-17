package com.kapital.kapitalApp.compose.components

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.ParagraphIntrinsics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.createFontFamilyResolver
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private const val TEXT_SCALE_REDUCTION_INTERVAL = 0.9f

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Suppress("FunctionNaming")
fun AutoSizeTextField(
    modifier: Modifier = Modifier,
    inputValue: String,
    maxLength: Int = 45,
    fontSize: TextUnit = TextUnit.Unspecified,
    lineHeight: TextUnit = 80.sp,
    inputValueChanged: (String) -> Unit
) {
    BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
        var shrunkFontSize = fontSize
        val calculateIntrinsics = @Composable {
            ParagraphIntrinsics(
                text = inputValue,
                style = TextStyle(
                    fontSize = shrunkFontSize,
                    fontWeight = FontWeight.Normal,
                    lineHeight = lineHeight,
                    textAlign = TextAlign.Start
                ),
                density = LocalDensity.current,
                fontFamilyResolver = createFontFamilyResolver(LocalContext.current)
            )
        }

        var intrinsics = calculateIntrinsics()
        with(LocalDensity.current) {
            // TextField and OutlinedText field have default horizontal padding of 16.dp
            val textFieldDefaultHorizontalPadding = 16.dp.toPx()
            val maxInputWidth = maxWidth.toPx() - 2 * textFieldDefaultHorizontalPadding

            while (intrinsics.maxIntrinsicWidth > maxInputWidth) {
                shrunkFontSize *= TEXT_SCALE_REDUCTION_INTERVAL
                intrinsics = calculateIntrinsics()
            }
        }

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = inputValue,
            onValueChange = { newText: String ->
                if (newText.length <= maxLength) {
                    inputValueChanged(newText)
                }
            },
            textStyle = TextStyle(
                fontSize = shrunkFontSize,
                fontWeight = FontWeight.Normal,
                lineHeight = lineHeight,
                textAlign = TextAlign.Start
            ),
            singleLine = true,
        )
    }
}
