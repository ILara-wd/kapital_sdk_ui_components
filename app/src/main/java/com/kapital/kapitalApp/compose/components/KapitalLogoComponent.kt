package com.kapital.kapitalApp.compose.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import com.Kapital.KapitalApp.R
import com.kapital.kapitalApp.compose.theme.getSimpleColorIcons

@Suppress("FunctionNaming")
@Composable
fun KapitalLogoComponent(maxWithFraction: Float = 0.4f, modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.ic_isotipo_white),
        contentDescription = null,
        colorFilter = getSimpleColorIcons(),
        contentScale = ContentScale.FillWidth,
        modifier = modifier
            .fillMaxWidth(maxWithFraction)
            .wrapContentHeight()
            .padding(top = dimensionResource(id = R.dimen.large_padding))
    )
}
