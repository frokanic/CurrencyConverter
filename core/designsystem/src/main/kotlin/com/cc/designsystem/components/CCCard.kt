
package com.cc.designsystem.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cc.designsystem.theme.CurrencyConverterTheme

@Composable
fun CCCard(
    modifier: Modifier = Modifier,
    color: Color = Color.White,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier
            .padding(
                all = 4.dp
            )
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(
                    size = 20.dp
                ),
                spotColor = Color.Gray
                    .copy(
                        alpha = 0.5f
                    )
            ),
        shape = RoundedCornerShape(
            size = 20.dp
        ),
        colors = CardDefaults.cardColors().copy(
            containerColor = color
        )
    ) {
        Column(
            modifier = Modifier
                .padding(
                    all = 16.dp
                )
        ) {
            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CCCardPreview() {
    CurrencyConverterTheme {
        CCCard(
            modifier = Modifier
                .height(height = 200.dp)
                .width(width = 300.dp)
        ) {

        }
    }
}