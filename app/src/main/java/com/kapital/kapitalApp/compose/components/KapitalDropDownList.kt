package com.kapital.kapitalApp.compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.Kapital.KapitalApp.R
import com.kapital.kapitalApp.compose.theme.colorReverseTheme
import com.kapital.kapitalApp.compose.theme.subtitles

@Suppress("FunctionNaming", "LongParameterList")
@Composable
fun <T> KapitalDropDownList(
    modifier: Modifier = Modifier,
    label: String,
    list: List<T>,
    mapper: (T) -> String = { it.toString() },
    selectedObject: (T) -> Unit,
    placeHolder: String = "",
    isError: Boolean = false,
    messageError: String? = null,
    value: T?,
    onClick: () -> Unit = {},
    captureText: Boolean = false,
    capturedText: (String) -> Unit = {},
) {
    var text by remember { mutableStateOf("") } // initial value
    var isOpen by remember { mutableStateOf(false) }
    Box(modifier = modifier) {
        Column {
            Box(
                modifier = Modifier.wrapContentSize()
            ) {

                val onChangeText = if (captureText) {
                    { currentText: String ->
                        text = currentText.also {
                            capturedText(text)
                        }
                    }
                } else {
                    {}
                }
                KapitalTextField(
                    textColor = MaterialTheme.colorScheme.subtitles(),
                    text = text,
                    onChange = onChangeText,
                    label = label,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = placeHolder,
                    isError = isError,
                    messageError = messageError
                )
                takeUnless { captureText }
                    ?.let {
                        Column(
                            modifier = Modifier
                                .wrapContentSize()
                                .align(Alignment.CenterEnd)
                                .padding(horizontal = dimensionResource(id = R.dimen.normal_padding)),
                            horizontalAlignment = Alignment.End
                        ) {
                            Icon(
                                tint = colorReverseTheme(),
                                painter = painterResource(id = R.drawable.ic_arrow_drop_down),
                                contentDescription = null
                            )
                        }
                    }
            }
            DropdownMenu(
                modifier = Modifier.fillMaxWidth(),
                expanded = isOpen,
                onDismissRequest = { isOpen = false },
            ) {
                list.forEach {
                    DropdownMenuItem(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            isOpen = false
                            text = mapper(it)
                            selectedObject(it)
                        },
                        text = {
                            Text(
                                mapper(it),
                                modifier = Modifier
                                    .wrapContentWidth()
                            )
                        }
                    )
                }
            }
        }
        takeUnless { captureText }
            ?.let {
                Spacer(
                    modifier = Modifier
                        .matchParentSize()
                        .background(Color.Transparent)
                        .clickable(
                            onClick = {
                                isOpen = true
                                onClick()
                            }
                        )
                )
                value?.let {
                    text = mapper(it)
                }
            }
    }
}

@Preview
@Composable
@Suppress("FunctionNaming")
fun PreviewDropDow() {
    KapitalDropDownList(
        label = "test",
        list = listOf("text 1", "Text 2"),
        selectedObject = {},
        value = ""
    )
}
