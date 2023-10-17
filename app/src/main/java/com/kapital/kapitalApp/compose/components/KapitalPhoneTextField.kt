package com.kapital.kapitalApp.compose.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.Kapital.KapitalApp.R
import com.kapital.kapitalApp.compose.components.mask.PhoneNumberVisualTransformation

@Suppress("FunctionNaming")
@Composable
fun KapitalPhoneTextField(
    text: String,
    modifier: Modifier,
    onDoneKeyboardAction: (KeyboardActionScope.() -> Unit)? = null,
    isError: Boolean = false,
    messageError: String? = null,
    onChange: (String) -> Unit
) {
    KapitalTextField(
        label = stringResource(id = R.string.number),
        placeholder = "+52 00-0000-0000",
        text = text,
        //leadingIcon = {
        //    Image(
        //        painter = painterResource(id = R.mipmap.ic_flag_mexico),
        //        contentDescription = stringResource(id = R.string.content_description)
        //    )
        //},
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        keyboardActions = onDoneKeyboardAction?.let { KeyboardActions(onDone = it) }
            ?: KeyboardActions.Default,
        modifier = modifier,
        visualTransformation = PhoneNumberVisualTransformation(),
        isError = isError,
        messageError = messageError
    ) { newText: String ->
        onChange.takeIf { newText.length <= 10 }?.invoke(newText)
    }
}

@Suppress("FunctionNaming")
@Preview
@Composable
fun ShowKapitalPhone() {
    Column {
        KapitalPhoneTextField(
            text = "5579232186",
            onChange = {},
            modifier = Modifier
        )
    }
}
