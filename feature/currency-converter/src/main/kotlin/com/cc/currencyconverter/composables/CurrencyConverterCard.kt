package com.cc.currencyconverter.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cc.currencyconverter.model.CurrencyUIModel
import com.cc.designsystem.components.CCCard
import com.frokanic.cc.designsystem.R

@Composable
fun CurrencyConverterCard(
    modifier: Modifier = Modifier,
    allCurrencies: List<CurrencyUIModel>,
    fromCurrency: CurrencyUIModel,
    toCurrency: CurrencyUIModel,
    onFromCurrencyChanged: (CurrencyUIModel) -> Unit,
    onToCurrencyChanged: (CurrencyUIModel) -> Unit,
    onSwap: () -> Unit
) {
    CCCard(
        modifier = modifier
            .padding(
                horizontal = 16.dp
            )
            .fillMaxWidth(),
    ) {
        CurrencyInfoRow(
            label = stringResource(
                id = R.string.amount
            ),
            selectedCurrency = fromCurrency,
            allCurrencies = allCurrencies,
            onCurrencyChange = onFromCurrencyChanged
        )

        Spacer(
            modifier = Modifier
                .height(
                    height = 20.dp
                )
        )

        CurrenciesSwapper(
            onSwap = onSwap
        )

        Spacer(
            modifier = Modifier
                .height(
                    height = 10.dp
                )
        )

        CurrencyInfoRow(
            label = stringResource(
                id = R.string.converted_amount
            ),
            selectedCurrency = toCurrency,
            allCurrencies = allCurrencies,
            onCurrencyChange = onToCurrencyChanged
        )
    }
}

@Preview
@Composable
fun CurrencyConverterCardPreview() {
    CurrencyConverterCard(
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
        fromCurrency = CurrencyUIModel(
            code = "EUR",
            value = "1.94"
        ),
        toCurrency = CurrencyUIModel(
            code = "USD",
            value = "12.11"
        ),
        onFromCurrencyChanged = {  },
        onToCurrencyChanged = {  },
        onSwap = {  },
    )
}