package com.cc.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cc.designsystem.theme.CurrencyConverterTheme

@Composable
fun CCTextField(
    modifier: Modifier = Modifier,
    value: String,
    singleLine: Boolean = false,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    keyboardActions: KeyboardActions = KeyboardActions(),
) {
    BasicTextField(
        modifier = modifier
            .clip(
                shape = RoundedCornerShape(8.dp)
            )
            .background(
                color = Color(
                    color = 0xFFEFEFEF
                )
            )
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 20.dp
            ),
        value = TextFieldValue(
            text = value,
            selection = TextRange(
                index = value.length
            )
        ),
        onValueChange = { onValueChange(it.text) },
        textStyle = MaterialTheme.typography.labelLarge,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine
    )
}

@Preview
@Composable
fun CCTextFieldPreview() {
    CurrencyConverterTheme {
        CCTextField(
            value = "1500",
            onValueChange = {  }
        )
    }
}