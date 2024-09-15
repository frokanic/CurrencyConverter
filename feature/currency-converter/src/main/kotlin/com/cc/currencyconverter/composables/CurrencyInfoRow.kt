package com.cc.currencyconverter.composables

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cc.currencyconverter.model.CurrencyUIModel
import com.cc.designsystem.components.CCTextField
import com.cc.designsystem.components.CCTextMenu

@Composable
fun CurrencyInfoRow(
    modifier: Modifier = Modifier,
    label: String,
    selectedCurrency: CurrencyUIModel,
    allCurrencies: List<CurrencyUIModel>,
    onCurrencyChange: (CurrencyUIModel) -> Unit
) {
    val currencyCodes = remember(allCurrencies) {
        allCurrencies.map { it.code }
    }

    Column(
        modifier = modifier
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall
        )

        Spacer(
            modifier = Modifier
                .height(
                    height = 16.dp
                )
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnimatedContent(
                modifier = Modifier
                    .weight(
                        weight = 1f
                    ),
                targetState = selectedCurrency.code,
            ) { currency ->
                CCTextMenu(
                    selectedOption = currency,
                    options = currencyCodes,
                    onOptionSelected = {
                        onCurrencyChange(allCurrencies[it].copy(value = selectedCurrency.value))
                    }
                )
            }

            Spacer(
                modifier = Modifier
                    .height(
                        height = 32.dp
                    )
            )

            CCTextField(
                modifier = Modifier
                    .weight(
                        weight = 1f
                    ),
                value = selectedCurrency.value,
                onValueChange = { onCurrencyChange(selectedCurrency.copy(value = it)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )
        }
    }
}

@Preview
@Composable
fun CurrencyInfoRowPreview() {
    CurrencyInfoRow(
        label = "Amount",
        selectedCurrency = CurrencyUIModel(
            code = "EUR",
            value = "1.94"
        ),
        allCurrencies = listOf(
            CurrencyUIModel(
                code = "EUR",
                value = "1.94"
            ),
            CurrencyUIModel(
                code = "USD",
                value = "12.11"
            )
        ),
        onCurrencyChange = {  }
    )
}