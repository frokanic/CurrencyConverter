package com.cc.currencyconverter.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.draw.rotate
import com.frokanic.cc.designsystem.R
import kotlinx.coroutines.launch

@Composable
fun CurrenciesSwapper(
    modifier: Modifier = Modifier,
    onSwap: () -> Unit
) {
    val animatable = remember {
        Animatable(
            initialValue = 0f
        )
    }

    val scope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        HorizontalDivider()

        Box(
            modifier = Modifier
                .size(
                    size = 44.dp
                )
                .clip(
                    shape = CircleShape
                )
                .background(
                    color = MaterialTheme.colorScheme.primary
                )
                .clickable {
                    if (animatable.isRunning)
                        return@clickable
                        
                    scope.launch {
                        onSwap()

                        animatable.animateTo(
                            targetValue = animatable.value + 180f,
                            animationSpec = tween(
                                durationMillis = 300
                            )
                        )
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier
                    .padding(
                        all = 12.dp
                    )
                    .rotate(
                        degrees = animatable.value
                    ),
                painter = painterResource(
                    id = R.drawable.ic_swap
                ),
                contentDescription = null,
                tint = Color.White,

            )
        }
    }
}

@Preview
@Composable
fun CurrenciesSwapperPreview() {
    CurrenciesSwapper(
        onSwap = {  }
    )
}