package com.kapital.kapitalApp.compose.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.Kapital.KapitalApp.R

val kapitalFont = FontFamily(
    Font(R.font.lato_regular),
    Font(R.font.gotham_bold, weight = FontWeight.Bold),
    Font(R.font.gotham_light, weight = FontWeight.Light),
    Font(R.font.gotham_medium, weight = FontWeight.Medium),
    Font(R.font.gotham_bold, weight = FontWeight.ExtraBold),
    Font(R.font.gotham_light, weight = FontWeight.ExtraLight),
    Font(R.font.lato_regular, weight = FontWeight.SemiBold)
)

val Typography = Typography(

    //displayLarge = TextStyle(),
    //displayMedium = TextStyle(),
    //displaySmall = TextStyle(),

    // Heading
    headlineLarge = TextStyle(
        fontFamily = kapitalFont,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 37.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = kapitalFont,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = kapitalFont,
        fontWeight = FontWeight.W500,
        fontSize = 20.sp
    ),

    // Title
    titleLarge = TextStyle(
        fontFamily = kapitalFont,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 37.sp
    ),
    titleMedium = TextStyle(
        fontFamily = kapitalFont,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    titleSmall = TextStyle(
        fontFamily = kapitalFont,
        fontWeight = FontWeight.W500,
        fontSize = 20.sp
    ),

    // Subtitle
    labelLarge = TextStyle(
        fontFamily = kapitalFont,
        fontWeight = FontWeight.W700,
        fontSize = 16.sp
    ),
    labelMedium = TextStyle(
        fontFamily = kapitalFont,
        fontWeight = FontWeight.W700,
        fontSize = 14.sp
    ),
    labelSmall = TextStyle(
        fontFamily = kapitalFont,
        fontWeight = FontWeight.W700,
        fontSize = 14.sp
    ),

    bodyLarge = TextStyle(
        fontFamily = kapitalFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = kapitalFont,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    bodySmall = TextStyle(
        fontFamily = kapitalFont,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )

)

@Composable
fun Typography.getStyleSubtitle() =
    MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.subtitles())

@Composable
fun Typography.bottomNavigationItem() =
    MaterialTheme.typography.bodyLarge.copy(fontSize = 12.sp, fontWeight = FontWeight.W700)

@Composable
fun Typography.getSimpleStyleText() =
    MaterialTheme.typography.bodyLarge.copy(color = colorReverseTheme())

@Composable
fun getSimpleColorIcons() = ColorFilter.tint(color = colorReverseTheme())

@Composable
fun colorReverseTheme() = if (isSystemInDarkTheme()) White else Black

@Composable
fun Typography.buttonTextStyle() = MaterialTheme.typography.headlineLarge.copy(
    fontSize = 16.sp, fontFamily = FontFamily(
        Font(
            R.font.lato_regular
        )
    )
)
