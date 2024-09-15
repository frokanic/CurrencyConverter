package com.frokanic.currencyconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.cc.designsystem.theme.CurrencyConverterTheme
import com.frokanic.currencyconverter.navigation.CurrencyConverterNavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            CurrencyConverterTheme {
                CurrencyConverterNavGraph()
            }
        }
    }
}