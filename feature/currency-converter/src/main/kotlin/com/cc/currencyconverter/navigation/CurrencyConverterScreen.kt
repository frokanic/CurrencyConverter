package com.cc.currencyconverter.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.cc.currencyconverter.CurrencyConverterScreen


const val CurrencyConverterRoute = "CurrencyConverterScreen"

fun NavGraphBuilder.currencyConverterScreen() {
    composable(
        route = CurrencyConverterRoute
    ) {
        CurrencyConverterScreen()
    }
}