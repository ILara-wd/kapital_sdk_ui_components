package com.kapital.kapitalApp.compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.Kapital.KapitalApp.R
import com.kapital.kapitalApp.compose.theme.activeFocusComponentColor
import com.kapital.kapitalApp.compose.theme.colorReverseTheme
import com.kapital.kapitalApp.compose.theme.labelTextColor
import com.kapital.kapitalApp.compose.theme.subtitles
import com.kapital.kapitalApp.compose.theme.textGrey
import com.kapital.kapitalApp.compose.theme.unfocusedComponentColor

@Suppress("FunctionNaming", "LongParameterList", "LongMethod")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KapitalTextField(
    label: String,
    placeholder: String,
    text: String,
    modifier: Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    textColor: Color = colorReverseTheme(),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    leadingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    isEnabled: Boolean = true,
    messageError: String? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    onChange: (String) -> Unit
) {
    var hasFocus by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            OutlinedTextField(
                value = text,
                onValueChange = onChange,
                maxLines = 1,
                enabled = isEnabled,
                singleLine = true,
                placeholder = {
                    Text(
                        text = placeholder,
                        color = MaterialTheme.colorScheme.textGrey(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                visualTransformation = visualTransformation,
                leadingIcon = leadingIcon,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    //textColor = textColor,
                    //placeholderColor = MaterialTheme.colorScheme.subtitles(),
                    focusedLabelColor = MaterialTheme.colorScheme.activeFocusComponentColor(),
                    unfocusedLabelColor = MaterialTheme.colorScheme.subtitles(),
                    focusedBorderColor = MaterialTheme.colorScheme.activeFocusComponentColor(),
                    unfocusedBorderColor = MaterialTheme.colorScheme.unfocusedComponentColor()
                ),
                isError = isError,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = dimensionResource(id = R.dimen.normal_padding))
                    .onFocusEvent {
                        hasFocus = it.isFocused
                    },
                trailingIcon = trailingIcon
            )
            Column(
                modifier = Modifier
                    .padding(start = dimensionResource(id = R.dimen.large_padding)),
            ) {
                Text(
                    text = label,
                    modifier = Modifier.background(color = Color.White),
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = if (isError) MaterialTheme.colorScheme.error else {
                            if (hasFocus) {
                                MaterialTheme.colorScheme.activeFocusComponentColor()
                            } else {
                                MaterialTheme.colorScheme.labelTextColor()
                            }
                        }
                    ),
                )
            }
        }
        messageError
            ?.takeIf { isError }
            ?.let {
                Text(
                    modifier = Modifier
                        .padding(horizontal = dimensionResource(id = R.dimen.normal_padding)),
                    text = it,
                    style = TextStyle.Default.copy(color = MaterialTheme.colorScheme.error)
                )
            }
    }
}

@Preview
@Composable
fun showKapitalTextField() {
    KapitalTextField(
        label = "Text", placeholder = "Test Holder",
        text = "", modifier = Modifier,
        onChange = {}
    )
}
