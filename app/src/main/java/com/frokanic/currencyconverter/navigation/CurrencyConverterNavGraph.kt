package com.frokanic.currencyconverter.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.cc.currencyconverter.navigation.CurrencyConverterRoute
import com.cc.currencyconverter.navigation.currencyConverterScreen

@Composable
fun CurrencyConverterNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = CurrencyConverterRoute
    ) {
        currencyConverterScreen()
    }
}