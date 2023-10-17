package com.kapital.kapitalApp.compose.components

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.Kapital.KapitalApp.R
import com.kapital.kapitalApp.compose.theme.getSimpleStyleText
import com.kapital.kapitalApp.compose.theme.kapitalColorLink

@Suppress("FunctionNaming")
@Composable
fun KapitalLabel(
    label: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    fontSize: TextUnit,
    isBoldText: Boolean = false,
    color: Color
) {
    Text(
        fontFamily = FontFamily(Font(R.font.lato_regular)),
        text = label,
        fontSize = fontSize,
        color = color,
        textAlign = textAlign,
        modifier = modifier,
        fontWeight = if (isBoldText) FontWeight.Bold else FontWeight.Normal
    )
}

@Suppress("FunctionNaming")
@Composable
fun KapitalLabelExtra(
    label: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    fontSize: TextUnit,
    color: Color,
    isBoldText: Boolean = false,
) {
    Text(
        text = label,
        fontSize = fontSize,
        color = color,
        textAlign = textAlign,
        modifier = modifier,
        fontWeight = if (isBoldText) FontWeight.Bold else FontWeight.Normal,
        fontFamily = if (isBoldText) FontFamily(Font(R.font.lato_regular)) else FontFamily(Font(R.font.gotham_bold)),
        lineHeight = 40.sp
    )
}

data class LinkTextData(
    val text: String,
    val tag: String? = null,
    val annotation: String? = null,
    val onClick: ((str: AnnotatedString.Range<String>) -> Unit)? = null,
)

@Suppress("FunctionNaming")
@Composable
fun LinkText(
    linkTextData: List<LinkTextData>,
    modifier: Modifier = Modifier,
) {
    val annotatedString = createAnnotatedString(linkTextData)

    ClickableText(
        text = annotatedString,
        style = MaterialTheme.typography.getSimpleStyleText(),
        onClick = { offset ->
            linkTextData.forEach { annotatedStringData ->
                if (annotatedStringData.tag != null && annotatedStringData.annotation != null) {
                    annotatedString.getStringAnnotations(
                        tag = annotatedStringData.tag,
                        start = offset,
                        end = offset,
                    ).firstOrNull()?.let {
                        annotatedStringData.onClick?.invoke(it)
                    }
                }
            }
        },
        modifier = modifier,
    )
}

@Suppress("FunctionNaming")
@Composable
private fun createAnnotatedString(data: List<LinkTextData>): AnnotatedString {
    return buildAnnotatedString {
        data.forEach { linkTextData ->
            if (linkTextData.tag != null && linkTextData.annotation != null) {
                pushStringAnnotation(
                    tag = linkTextData.tag,
                    annotation = linkTextData.annotation,
                )
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.kapitalColorLink(),
                        fontWeight = FontWeight.SemiBold
                    ),
                ) {
                    append(linkTextData.text)
                }
                pop()
            } else {
                append(linkTextData.text)
            }
        }
    }
}
