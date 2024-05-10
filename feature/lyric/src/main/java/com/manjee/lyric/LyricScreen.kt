@file:OptIn(ExperimentalLayoutApi::class)

package com.manjee.lyric

import android.util.Log
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.manjee.designsystem.ui.Green60
import com.manjee.designsystem.ui.Grey90
import com.manjee.designsystem.ui.Yellow10
import com.manjee.designsystem.ui.Yellow20
import com.manjee.designsystem.ui.Yellow70
import com.manjee.lyric.component.LyricItem
import com.manjee.model.Lyric
import com.manjee.model.LyricQuiz

@Composable
fun LyricRoute(
    viewModel: LyricViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LyricScreen(
        uiState = uiState,
        checkAnswer = viewModel::checkAnswer
    )
}

@Composable
internal fun LyricScreen(
    uiState: LyricScreenUiState,
    checkAnswer: (List<Lyric>, String) -> Unit = { _, _ -> }
) {
    val coroutineScope = rememberCoroutineScope()

    var submitButtonEnabled by remember { mutableStateOf(false) }
    var selectList by remember { mutableStateOf(listOf<Lyric>()) }
    var quizIndex by remember { mutableIntStateOf(0) }

    when (uiState) {
        is LyricScreenUiState.Loading -> {
            // TODO
        }

        is LyricScreenUiState.Error -> {
            Text(
                text = "Error: ${uiState.message}",
                color = Grey90,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        is LyricScreenUiState.Success -> {
            val data = uiState.data

            if (quizIndex != uiState.currentQuizIndex) {
                quizIndex = uiState.currentQuizIndex
                selectList = listOf()
                submitButtonEnabled = false
            }

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

                                data.answerList = data.answerList
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

                                if (selectList.isEmpty()) {
                                    submitButtonEnabled = true
                                }
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
                        repeat(data.answerList.size) { index ->
                            val item = data.answerList[index]

                            if (item.isVisible) {
                                LyricItem(
                                    coroutineScope = coroutineScope,
                                    lyric = item,
                                    onComplete = {
                                        selectList = selectList
                                            .toMutableList()
                                            .apply { add(item) }

                                        uiState.data.answerList =
                                            uiState.data.answerList
                                                .toMutableList()
                                                .apply { set(index, item.copy(isVisible = false)) }

                                        if (selectList.isNotEmpty()) {
                                            submitButtonEnabled = true
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
                Spacer(
                    modifier = Modifier
                        .height(8.dp)
                )
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .padding(horizontal = 16.dp),
                    colors = ButtonColors(
                        contentColor = Color.Black,
                        containerColor = Yellow70,
                        disabledContentColor = Color.Black,
                        disabledContainerColor = Yellow20
                    ),
                    onClick = {
                        checkAnswer(
                            selectList,
                            uiState.data.answer
                        )
                    },
                    enabled = submitButtonEnabled
                ) {
                    Text(
                        text = "Submit",
                        color = Color.White
                    )
                }
                Spacer(
                    modifier = Modifier
                        .height(8.dp)
                )
            }
        }
    }
}

@Preview
@Composable
internal fun LyricScreenPreview() {
    LyricScreen(
        uiState = LyricScreenUiState.Success(
            LyricQuiz(
                answer = "HELLOMY",
                answerList = listOf(
                    Lyric(1, "HI"),
                    Lyric(2, "HELLO"),
                    Lyric(3, "MY"),
                    Lyric(4, "NAME"),
                    Lyric(5, "IS"),
                    Lyric(6, "MANJEE")
                ),
                stringTime = 0f,
                endTime = 0f,
                videoId = "123"
            )
        )
    )
}