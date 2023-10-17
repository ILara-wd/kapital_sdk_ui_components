package com.kapital.kapitalApp.compose.utils

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import com.Kapital.KapitalApp.R

@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.kapitalCommonHorizontalPadding() = composed() {
    padding(horizontal = dimensionResource(id = R.dimen.large_padding))
}
@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.kapitalCommonVerticalPadding() = composed() {
    padding(vertical = dimensionResource(id = R.dimen.large_padding))
}
@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.kapitalDialogHorizontalPadding() = composed() {
    padding(horizontal = dimensionResource(id = R.dimen.double_large_padding))
}
@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.kapitalCommonTopPadding(maxHeight: Dp? = null) = composed() {
    padding(
        top = dimensionResource(
            id = maxHeight?.let {
                R.dimen.extra_double_large_padding
            } ?: R.dimen.extra_large_padding
        )
    )
}

@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.kapitalCommonBottomPadding() = composed() {
    padding(bottom = dimensionResource(id = R.dimen.extra_large_padding))
}
