package com.kapital.kapitalApp.compose.components

import androidx.annotation.RawRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Suppress("FunctionNaming")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun KapitalPdfComponent(@RawRes pdfResId: Int) {
    var isLoading by remember { mutableStateOf(false) }
    Box {
        PdfViewer(
            modifier = Modifier.fillMaxSize(),
            pdfResId = pdfResId,
            loadingListener = { loading, _, _ ->
                isLoading = loading
            }
        )
        if (isLoading) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                KapitalProgressIndicator()
            }
        }
    }
}
