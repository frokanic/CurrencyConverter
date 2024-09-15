package com.cc.currencyconverter

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cc.currencyconverter.composables.CurrencyConverterCard
import com.cc.currencyconverter.model.CurrencyUIModel
import com.cc.designsystem.components.CCBackgroundScreen
import com.cc.designsystem.theme.CurrencyConverterTheme
import com.frokanic.cc.designsystem.R

@Composable
internal fun CurrencyConverterScreen(
    viewModel: CurrencyConverterViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CurrencyConverterScreen(
        uiState = uiState,
        onFromCurrencyChange = viewModel::onFromCurrencyChange,
        onToCurrencyChange = viewModel::onToCurrencyChange,
        onSwap = viewModel::swapCurrencies
    )
}

@Composable
internal fun CurrencyConverterScreen(
    uiState: CurrencyConverterUIState,
    onFromCurrencyChange: (CurrencyUIModel) -> Unit,
    onToCurrencyChange: (CurrencyUIModel) -> Unit,
    onSwap: () -> Unit
) {
    CCBackgroundScreen(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding(),
        ) {
            Spacer(
                modifier = Modifier
                    .height(
                        height = 32.dp
                    )
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(
                    id = R.string.currency_converter
                ),
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center
            )

            Spacer(
                modifier = Modifier
                    .height(
                        height = 12.dp
                    )
            )

            Text(
                modifier = Modifier
                    .padding(
                        horizontal = 16.dp
                    ),
                text = stringResource(
                    id = R.string.currency_converter_description
                ),
                textAlign = TextAlign.Center,
                color = Color(
                    color = 0xFF808080
                )
            )

            Spacer(
                modifier = Modifier
                    .height(
                        height = 48.dp
                    )
            )

            CurrencyConverterCard(
                allCurrencies = uiState.allCurrencies,
                fromCurrency = uiState.fromCurrency,
                toCurrency = uiState.toCurrency,
                onFromCurrencyChanged = onFromCurrencyChange,
                onToCurrencyChanged = onToCurrencyChange,
                onSwap = onSwap
            )

            Spacer(
                modifier = Modifier
                    .height(
                        height = 32.dp
                    )
            )

            Text(
                modifier = Modifier
                    .padding(
                        horizontal = 22.dp
                    ),
                text = stringResource(
                    id = R.string.indicative_exchange_rate
                ),
                style = MaterialTheme.typography.labelSmall
            )

            Spacer(
                modifier = Modifier
                    .height(
                        height = 10.dp
                    )
            )

            Text(
                modifier = Modifier
                    .padding(
                        horizontal = 22.dp
                    ),
                text = uiState.indicativeExchangeRate,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color.Black
                )
            )

            if (uiState.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(
                                size = 30.dp
                            )
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CurrencyConverterScreenPreview() {
    CurrencyConverterTheme {
        CurrencyConverterScreen(
            uiState = CurrencyConverterUIState.PreviewData,
            onFromCurrencyChange = {},
            onToCurrencyChange = {},
            onSwap = {}
        )
    }
}