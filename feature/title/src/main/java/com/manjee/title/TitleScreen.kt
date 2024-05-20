package com.manjee.title

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.manjee.designsystem.ui.Grey90
import com.manjee.designsystem.ui.ManduGreen50
import com.manjee.designsystem.ui.ManduGreen90
import com.manjee.designsystem.ui.Red70
import com.manjee.designsystem.ui.Yellow30
import com.manjee.designsystem.ui.Yellow70
import com.manjee.model.Title
import com.manjee.model.TitleQuiz
import com.manjee.title.component.QuizCountProgress

@Composable
fun TitleRoute(
    viewModel: TitleViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    TitleScreen(
        uiState = uiState,
        correctQuiz = viewModel::correctAnswer,
        incorrectQuiz = viewModel::incorrectAnswer
    )
}

@SuppressLint("DefaultLocale")
@Composable
fun TitleScreen(
    uiState: TitleScreenUiState,
    correctQuiz: () -> Unit = {},
    incorrectQuiz: () -> Unit = {}
) {
    val configuration = LocalConfiguration.current
    var remainTime by remember { mutableIntStateOf(30) }

    when (uiState) {
        is TitleScreenUiState.Loading -> {
            // TODO
        }

        is TitleScreenUiState.Error -> {
            Text(
                text = "Error: ${uiState.message}",
                color = Grey90,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        is TitleScreenUiState.Success -> {
            val currentQuiz = uiState.data.quiz[uiState.currentQuizIndex]

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(ManduGreen50)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height((configuration.screenHeightDp * 0.25).dp),
                    colors = CardColors(
                        containerColor = Yellow30,
                        contentColor = Color.Black,
                        disabledContentColor = Color.Gray,
                        disabledContainerColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 0.dp,
                        bottomEnd = 40.dp,
                        bottomStart = 40.dp
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 10.dp
                    ),
                ) {}
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = (configuration.screenHeightDp * 0.15).dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Card(
                        modifier = Modifier
                            .width((configuration.screenWidthDp * 0.8).dp)
                            .aspectRatio(3f / 2f),
                        colors = CardColors(
                            containerColor = Color.White,
                            contentColor = Color.Black,
                            disabledContentColor = Color.Gray,
                            disabledContainerColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(20.dp),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 10.dp
                        ),
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    text = String.format("%02d", uiState.correctCount),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = ManduGreen90
                                )
                                QuizCountProgress(
                                    modifier = Modifier
                                        .height(10.dp)
                                        .weight(1f)
                                        .padding(horizontal = 4.dp),
                                    quizCount = uiState.data.choiceList.size,
                                    count = uiState.correctCount,
                                    color = ManduGreen90,
                                    trackColor = Color.White
                                )
                                QuizCountProgress(
                                    modifier = Modifier
                                        .height(10.dp)
                                        .weight(1f)
                                        .padding(horizontal = 4.dp),
                                    quizCount = uiState.data.choiceList.size,
                                    count = uiState.incorrectCount,
                                    color = Color.White,
                                    trackColor = Red70,
                                    isReverse = true
                                )
                                Text(
                                    text = String.format("%02d", uiState.incorrectCount),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Red70
                                )
                            }
                            Spacer(
                                modifier = Modifier
                                    .height(12.dp)
                            )
                            Text(
                                text = stringResource(
                                    id = R.string.quiz_count,
                                    uiState.currentQuizIndex + 1,
                                    uiState.data.choiceList.size
                                ),
                                fontSize = 16.sp,
                                color = Yellow70,
                                fontWeight = FontWeight.Bold
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.Blue)
                            )
                        }
                    }

                    if (!uiState.isEnd) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(
                                16.dp,
                                Alignment.CenterVertically
                            ),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            val choiceList = uiState.data.choiceList.shuffled().take(4)

                            repeat(choiceList.size) {
                                Button(
                                    modifier = Modifier
                                        .width((configuration.screenWidthDp * 0.8).dp)
                                        .height(60.dp),
                                    shape = RoundedCornerShape(14.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.White,
                                        contentColor = Color.Black,
                                    ),
                                    border = BorderStroke(2.dp, Yellow70),
                                    onClick = {
                                        if (choiceList[it] == currentQuiz.title) {
                                            Log.i("@@@@", "정답")
                                            correctQuiz()
                                        } else {
                                            Log.i("@@@@", "오답")
                                            incorrectQuiz()
                                        }
                                    }
                                ) {
                                    Text(
                                        text = choiceList[it],
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = ((configuration.screenHeightDp * 0.15) - 40).dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .align(Alignment.TopCenter)
                            .padding(4.dp)
                            .background(Color.White, CircleShape)
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(2.dp),
                            color = Yellow70,
                            trackColor = Color.White
                        )
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.Center),
                            text = remainTime.toString(),
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center,
                            color = if (remainTime < 10) Red70 else Color.Black
                        )
                    }
                }

                IconButton(
                    modifier = Modifier
                        .align(Alignment.TopStart),
                    onClick = {

                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "backbutton",
                        tint = Color.Black
                    )
                }

                Row(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Show Video",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(
                        modifier = Modifier
                            .width(8.dp)
                    )
                    Checkbox(
                        modifier = Modifier.size(20.dp),
                        checked = true,
                        colors = CheckboxDefaults.colors(
                            checkedColor = Color.White,
                            uncheckedColor = Color.Black,
                            checkmarkColor = Color.Black
                        ),
                        onCheckedChange = {}
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun TitleScreenPreview() {
    TitleScreen(
        uiState = TitleScreenUiState.Success(
            data = TitleQuiz(
                quiz = listOf(
                    Title(title = "title", artist = "", videoId = "asd")
                ),
                choiceList = listOf(
                    "Answer 1",
                    "Answer 2",
                    "Answer 3",
                    "Answer 4"
                )
            ),
            correctCount = 1,
            incorrectCount = 2
        )
    )
}
