package com.kapital.kapitalApp.compose

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kapital.kapitalApp.compose.components.KapitalLogoComponent
import com.kapital.kapitalApp.compose.theme.colorReverseTheme
import kotlinx.coroutines.delay


@Composable
fun Typewriter(
    baseText: String = "",
    highlightedText: String = "",
    parts: List<String>
) {
    var partIndex by remember { mutableStateOf(0) }
    val highlightStart = baseText.indexOf(highlightedText)


    var partText by remember { mutableStateOf("") }

    val textToDisplay = "$baseText$partText"
    var selectedPartRects by remember { mutableStateOf(listOf<Rect>()) }

    print("Print $textToDisplay")
    Toast.makeText(LocalContext.current, "$textToDisplay", Toast.LENGTH_SHORT).show()


    LaunchedEffect(key1 = parts) {
        while (partIndex <= parts.size) {

            val part = parts[partIndex]

            part.forEachIndexed { charIndex, _ ->
                partText = part.substring(startIndex = 0, endIndex = charIndex + 1)
                delay(100)
            }

            delay(1000)

            part.forEachIndexed { charIndex, _ ->
                partText = part
                    .substring(startIndex = 0, endIndex = part.length - (charIndex + 1))
                delay(30)
            }

            delay(500)

            partIndex = (partIndex + 1) % parts.size
        }
    }

    Row {
        Text(
            text = textToDisplay,
            style = TextStyle(
                fontWeight = FontWeight.SemiBold,
                fontSize = 40.sp,
                letterSpacing = -(1.6).sp,
                lineHeight = 52.sp,
                color = colorReverseTheme()
            ),
            modifier = Modifier.drawBehind {
                val borderSize = 20.sp.toPx()
                selectedPartRects.forEach { rect ->
                    val selectedRect = rect.translate(0f, -borderSize / 1.5f)
                    drawLine(  //85586F
                        color = Color(0xFFA2D63F).copy(1f),
                        start = Offset(selectedRect.left, selectedRect.bottom),
                        end = selectedRect.bottomRight,
                        strokeWidth = borderSize
                    )
                }
            },
            onTextLayout = { layoutResult ->
                val start = baseText.length
                val end = textToDisplay.count()
                selectedPartRects = if (start < end) {
                    layoutResult
                        .getBoundingBoxesForRange(
                            start = start,
                            end = end - 1
                        )
                } else {
                    emptyList()
                }

                if (highlightedText.isNotEmpty()) {
                    if (highlightStart >= 0) {
                        selectedPartRects = selectedPartRects + layoutResult
                            .getBoundingBoxesForRange(
                                start = highlightStart,
                                end = highlightStart + highlightedText.length
                            )
                    }
                }

            }

        )
        KapitalLogoComponent()
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        contentAlignment = Alignment.Center,

        ) {
        Typewriter(parts = remember {
                listOf(
                    "Otro día, Otro Dollar \uD83E\uDD11",
                    "Other day, Other Dollar \uD83E\uDD11"
                )
            }
        )
    }
}

fun TextLayoutResult.getBoundingBoxesForRange(start: Int, end: Int): List<Rect> {
    var prevRect: Rect? = null
    var firstLineCharRect: Rect? = null
    val boundingBoxes = mutableListOf<Rect>()
    for (i in start..end) {
        val rect = getBoundingBox(i)
        val isLastRect = i == end

        // single char case
        if (isLastRect && firstLineCharRect == null) {
            firstLineCharRect = rect
            prevRect = rect
        }

        // `rect.right` is zero for the last space in each line
        // looks like an issue to me, reported: https://issuetracker.google.com/issues/197146630
        if (!isLastRect && rect.right == 0f) continue

        if (firstLineCharRect == null) {
            firstLineCharRect = rect
        } else if (prevRect != null) {
            if (prevRect.bottom != rect.bottom || isLastRect) {
                boundingBoxes.add(
                    firstLineCharRect.copy(right = prevRect.right)
                )
                firstLineCharRect = rect
            }
        }
        prevRect = rect
    }
    return boundingBoxes
}