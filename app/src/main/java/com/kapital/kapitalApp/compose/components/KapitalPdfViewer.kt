package com.kapital.kapitalApp.compose.components

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import android.util.Log
import androidx.annotation.RawRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.Kapital.KapitalApp.R
import com.kapital.kapitalApp.compose.components.models.PdfListDirection
import com.kapital.kapitalApp.compose.theme.FunGreyDisabled
import com.kapital.kapitalApp.compose.theme.White
import kotlinx.coroutines.coroutineScope
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

@Suppress("FunctionNaming")
@ExperimentalFoundationApi
@Composable
fun PdfViewer(
    @RawRes pdfResId: Int,
    modifier: Modifier = Modifier,
    backgroundColor: Color = White,
    pageColor: Color = White,
    listDirection: PdfListDirection = PdfListDirection.VERTICAL,
    arrangement: Arrangement.HorizontalOrVertical = Arrangement.spacedBy(16.dp),
    loadingListener: (
        isLoading: Boolean,
        currentPage: Int?,
        maxPage: Int?,
    ) -> Unit = { _, _, _ -> }
) {
    val context = LocalContext.current
    PdfViewer(
        pdfStream = context.resources.openRawResource(pdfResId),
        modifier = modifier,
        pageColor = pageColor,
        backgroundColor = backgroundColor,
        listDirection = listDirection,
        arrangement = arrangement,
        loadingListener = loadingListener,
    )
}

@Suppress("FunctionNaming")
@ExperimentalFoundationApi
@Composable
fun PdfViewer(
    pdfStream: InputStream,
    modifier: Modifier = Modifier,
    backgroundColor: Color = FunGreyDisabled,
    pageColor: Color = Color.White,
    listDirection: PdfListDirection = PdfListDirection.VERTICAL,
    arrangement: Arrangement.HorizontalOrVertical = Arrangement.spacedBy(16.dp),
    loadingListener: (
        isLoading: Boolean,
        currentPage: Int?,
        maxPage: Int?,
    ) -> Unit = { _, _, _ -> }
) {
    PdfViewer(
        pdfStream = pdfStream,
        modifier = modifier,
        backgroundColor = backgroundColor,
        listDirection = listDirection,
        loadingListener = loadingListener,
        arrangement = arrangement
    ) { lazyState, image ->
        PagePDF(
            image = image,
            lazyState = lazyState,
            backgroundColor = pageColor
        )
    }
}

@Suppress("FunctionNaming")
@ExperimentalFoundationApi
@Composable
fun PdfViewer(
    pdfStream: InputStream,
    modifier: Modifier = Modifier,
    backgroundColor: Color = FunGreyDisabled,
    listDirection: PdfListDirection = PdfListDirection.VERTICAL,
    arrangement: Arrangement.HorizontalOrVertical = Arrangement.spacedBy(16.dp),
    loadingListener: (
        isLoading: Boolean,
        currentPage: Int?,
        maxPage: Int?,
    ) -> Unit = { _, _, _ -> },
    page: @Composable (LazyListState, ImageBitmap) -> Unit
) {
    val context = LocalContext.current
    val pagePaths = remember {
        mutableStateListOf<String>()
    }
    LaunchedEffect(true) {
        if (pagePaths.isEmpty()) {
            val paths = context.loadPdf(pdfStream, loadingListener)
            pagePaths.addAll(paths)
        }
    }
    val lazyState = rememberLazyListState()
    when (listDirection) {
        PdfListDirection.HORIZONTAL ->
            LazyRow(
                modifier = modifier.background(backgroundColor),
                state = lazyState,
                horizontalArrangement = arrangement
            ) {
                items(pagePaths) { path ->
                    var imageBitmap by remember {
                        mutableStateOf<ImageBitmap?>(null)
                    }
                    LaunchedEffect(path) {
                        imageBitmap = BitmapFactory.decodeFile(path).asImageBitmap()
                    }
                    imageBitmap?.let { page(lazyState, it) }
                }
            }

        PdfListDirection.VERTICAL ->
            LazyColumn(
                modifier = modifier.background(backgroundColor),
                state = lazyState,
                verticalArrangement = arrangement
            ) {
                items(pagePaths) { path ->
                    var imageBitmap by remember {
                        mutableStateOf<ImageBitmap?>(null)
                    }
                    LaunchedEffect(path) {
                        imageBitmap = BitmapFactory.decodeFile(path).asImageBitmap()
                    }
                    imageBitmap?.let { page(lazyState, it) }
                }
            }
    }
}

@Suppress("FunctionNaming")
@ExperimentalFoundationApi
@Composable
private fun PagePDF(
    image: ImageBitmap,
    lazyState: LazyListState,
    backgroundColor: Color = Color.White
) {
    Card(
        modifier = Modifier.background(backgroundColor),
        //elevation = 5.dp
    ) {
        ZoomableImage(painter = BitmapPainter(image), scrollState = lazyState)
    }
}

suspend fun Context.loadPdf(
    inputStream: InputStream,
    loadingListener: (
        isLoading: Boolean,
        currentPage: Int?,
        maxPage: Int?
    ) -> Unit = { _, _, _ -> }
): List<String> = coroutineScope {//withContext(Dispatchers.Default) {
    val constantTemp = getString(R.string.constant_temp)
    val constantPdf = getString(R.string.constant_pdf)
    val constantPdfPage = getString(R.string.constant_pdfPage)
    val constantPng = getString(R.string.constant_png)
    loadingListener(true, null, null)
    val outputDir = cacheDir
    val tempFile = File.createTempFile(constantTemp, constantPdf, outputDir)
    tempFile.mkdirs()
    tempFile.deleteOnExit()
    val outputStream = FileOutputStream(tempFile)
    copy(inputStream, outputStream)
    val input = ParcelFileDescriptor.open(tempFile, ParcelFileDescriptor.MODE_READ_ONLY)
    val renderer = PdfRenderer(input)
    (0 until renderer.pageCount).map { pageNumber ->
        loadingListener(true, pageNumber, renderer.pageCount)
        val file = File.createTempFile("$constantPdfPage$pageNumber", constantPng, outputDir)
        file.mkdirs()
        file.deleteOnExit()
        val page = renderer.openPage(pageNumber)
        val bitmap = Bitmap.createBitmap(1240, 1754, Bitmap.Config.ARGB_8888)
        page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
        page.close()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, FileOutputStream(file))
        file.absolutePath.also { Log.d("TESTE", it) }
    }.also {
        loadingListener(false, null, renderer.pageCount)
        renderer.close()
    }
}

@Throws(IOException::class)
private fun copy(source: InputStream, target: OutputStream) {
    val buf = ByteArray(8192)
    var length: Int
    while (source.read(buf).also { length = it } > 0) {
        target.write(buf, 0, length)
    }
}
