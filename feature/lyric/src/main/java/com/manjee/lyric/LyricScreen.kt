@file:OptIn(ExperimentalLayoutApi::class)

package com.manjee.lyric

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.manjee.designsystem.ui.Green60
import com.manjee.designsystem.ui.Grey90
import com.manjee.designsystem.ui.Yellow10
import com.manjee.lyric.component.LyricItem
import com.manjee.model.Lyric

@Composable
fun LyricRoute() {
    LyricScreen()
}

private var quizList by mutableStateOf(
    listOf(
        Lyric(1, "HI"),
        Lyric(2, "HELLO"),
        Lyric(3, "MY"),
        Lyric(4, "NAME"),
        Lyric(5, "IS"),
        Lyric(6, "MANJEE")
    )
)
private var selectList by mutableStateOf(listOf<Lyric>())

@Composable
internal fun LyricScreen() {
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Yellow10)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .background(Green60)
        )
        Spacer(
            modifier = Modifier
                .height(4.dp)
        )
        Text(
            modifier = Modifier.padding(8.dp),
            text = "Your Answer",
            color = Grey90,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        FlowRow(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .padding(horizontal = 4.dp),
        ) {
            repeat(selectList.size) { index ->
                val item = selectList[index]

                LyricItem(
                    lyric = item,
                    onComplete = {
                        quizList = quizList
                            .toMutableList()
                            .map {
                                if (it.index == item.index) {
                                    it.copy(isVisible = true)
                                } else {
                                    it
                                }
                            }

                        selectList = selectList
                            .toMutableList()
                            .apply { remove(item) }
                    }
                )
            }
        }
        Card(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
            colors = CardColors(
                containerColor = Color.White,
                contentColor = Color.White,
                disabledContentColor = Color.White,
                disabledContainerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            )
        ) {
            FlowRow(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp),
                horizontalArrangement = Arrangement.spacedBy(1.dp)
            ) {
                repeat(quizList.size) { index ->
                    val item = quizList[index]

                    if (item.isVisible) {
                        LyricItem(
                            coroutineScope = coroutineScope,
                            lyric = item,
                            onComplete = {
                                selectList = selectList
                                    .toMutableList()
                                    .apply { add(item) }

                                quizList = quizList
                                    .toMutableList()
                                    .apply { set(index, item.copy(isVisible = false)) }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
internal fun LyricScreenPreview() {
    LyricScreen()
}