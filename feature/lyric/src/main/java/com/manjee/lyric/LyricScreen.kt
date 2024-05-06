package com.manjee.lyric

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun LyricRoute() {
    LyricScreen()
}

private var selectedIndex by mutableIntStateOf(-1)
private var testList by mutableStateOf(listOf("test1", "test2", "test3", "test4", "test5"))
private var aList by mutableStateOf(listOf<String>())

@Composable
internal fun LyricScreen() {
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn {
            itemsIndexed(aList) { index, item ->
                Text(
                    modifier = Modifier
                        .clickable {

                        },
                    text = item
                )
            }
        }
        LazyColumn {
            itemsIndexed(testList) { index, item ->
                Log.d("@@@@", "$index $item")
                val scale = remember { Animatable(if (index == selectedIndex) 1.2f else 1f) }

                Text(
                    modifier = Modifier
                        .scale(scale.value)
                        .clickable {
                            selectedIndex = index

                            val job = coroutineScope.launch {
                                scale.animateTo(
                                    targetValue = if (scale.isRunning) 1f else 0f,
                                    animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioMediumBouncy,
                                        stiffness = Spring.StiffnessLow
                                    )
                                )
                            }

                            job.invokeOnCompletion {
                                if (index == selectedIndex) {
                                    Log.i("@@@@", "testList: $testList $index")

//                                    testList = testList
//                                        .toMutableList()
//                                        .apply { removeAt(index) }

                                    aList = aList
                                        .toMutableList()
                                        .apply { add(item) }

                                    Log.i("@@@@", "testList: $testList")

                                    selectedIndex = -1
                                }
                            }
                        },
                    text = item,
                    fontSize = 20.sp
                )
            }
        }
    }
}

@Preview
@Composable
internal fun LyricScreenPreview() {
    LyricScreen()
}