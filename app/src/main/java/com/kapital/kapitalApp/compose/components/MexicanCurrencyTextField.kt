package com.kapital.kapitalApp.compose.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Suppress("FunctionNaming")
fun MexicanCurrencyTextField(
    amount: String,
    onAmountChanged: (String) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface
    ) {
        OutlinedTextField(
            value = amount,
            onValueChange = onAmountChanged,
            visualTransformation = mexicanCurrencyVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            singleLine = true,
            maxLines = 1,
            textStyle = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
    }
}


fun mexicanCurrencyVisualTransformation(): VisualTransformation {

    var current: String = ""

    return object : VisualTransformation {
        override fun filter(text: AnnotatedString): TransformedText {
            var formatted = ""
            if (text.toString() != current) {
                val parsed = cleanStringToDouble(text.text)
                formatted = amountToCurrency(parsed / 100)
                current = amountToCurrency(parsed / 100)
            }
            val formattedText = buildAnnotatedString {
                append(formatted)
            }

            return TransformedText(
                formattedText,
                offsetMapping = object : OffsetMapping {
                    override fun originalToTransformed(offset: Int): Int {
                        return offset
                    }

                    override fun transformedToOriginal(offset: Int): Int {
                        return offset
                    }
                }
            )
        }
    }
}

fun amountToCurrency(amount: Double): String {
    val numberFormat = NumberFormat.getCurrencyInstance(Locale("es", "CO"))
    numberFormat.maximumFractionDigits = 2
    return numberFormat.format(amount)
}


fun cleanStringToDouble(s: String): Double {
    val cleanString = s
        .replace(".", "")
        .replace(" ", "")
        .replace("$", "")
        .replace(",", ".")
    return cleanString.toDouble()
}

fun formattedAmount(s: String): String {
    if (s.isNotEmpty() && s.isDigitsOnly()) {
        val parsed = cleanStringToDouble(s)
        //val formatted = NumberFormat.getCurrencyInstance().format((parsed / 100)) MXN
        //val formatted = amountToCurrency(parsed / 100) CO
        return amountToCurrency(parsed / 100)
    }
    return ""
}
