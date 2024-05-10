package com.manjee.lyric.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.manjee.designsystem.ui.Grey90
import com.manjee.model.Lyric
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun LyricItem(
    coroutineScope: CoroutineScope = rememberCoroutineScope(), lyric: Lyric, onComplete: () -> Unit
) {
    val scale = remember { Animatable(1f) }
    var isClicked: Boolean by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .padding(4.dp)
    ) {
        Button(
            modifier = Modifier
                .scale(scale.value)
                .height(40.dp),
            onClick = {
                if (isClicked) return@Button
                isClicked = true

                val job = coroutineScope.launch {
                    scale.animateTo(
                        targetValue = 0f, animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessMedium
                        )
                    )
                }

                job.invokeOnCompletion {
                    onComplete()
                    isClicked = false
                }
            }, colors = ButtonColors(
                contentColor = Color.White,
                containerColor = Grey90,
                disabledContentColor = Color.White,
                disabledContainerColor = Grey90
            ), shape = RoundedCornerShape(8.dp), elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 10.dp
            )
        ) {
            Text(
                modifier = Modifier, text = lyric.str, color = Color.White
            )
        }
    }

    LaunchedEffect(key1 = lyric) {
        scale.snapTo(
            targetValue = 1f
        )
    }
}

@Composable
@Preview
fun LyricItemPreview() {
    LyricItem(
        lyric = Lyric(1, "Hello, world!")
    ) {}
}