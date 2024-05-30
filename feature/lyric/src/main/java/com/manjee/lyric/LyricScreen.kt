@file:OptIn(ExperimentalLayoutApi::class)

package com.manjee.lyric

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.manjee.designsystem.component.LoadingLottie
import com.manjee.designsystem.component.OneButtonDialog
import com.manjee.designsystem.ui.Green60
import com.manjee.designsystem.ui.Grey90
import com.manjee.designsystem.ui.ManduGreen20
import com.manjee.designsystem.ui.ManduGreen50
import com.manjee.designsystem.ui.ManduGreen70
import com.manjee.lyric.util.CustomPlayerUiController
import com.manjee.lyric.component.LyricItem
import com.manjee.model.Lyric
import com.manjee.model.LyricQuiz
import com.manjee.nocaptionyoutubeplayer.PlayerConstants
import com.manjee.nocaptionyoutubeplayer.YouTubePlayer
import com.manjee.nocaptionyoutubeplayer.listeners.AbstractYouTubePlayerListener
import com.manjee.nocaptionyoutubeplayer.options.IFramePlayerOptions
import com.manjee.nocaptionyoutubeplayer.utils.YouTubePlayerTracker
import com.manjee.nocaptionyoutubeplayer.utils.loadOrCueVideo
import com.manjee.nocaptionyoutubeplayer.views.YouTubePlayerView

@Composable
fun LyricRoute(
    viewModel: LyricViewModel = hiltViewModel(),
    onBackPressed: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LyricScreen(
        uiState = uiState,
        checkAnswer = viewModel::checkAnswer,
        onBackPressed = onBackPressed
    )
}

@Composable
internal fun LyricScreen(
    uiState: LyricScreenUiState,
    checkAnswer: (List<Lyric>, String) -> Unit = { _, _ -> },
    onBackPressed: () -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycle = lifecycleOwner.lifecycle

    var currentYouTubePlayer by remember { mutableStateOf<YouTubePlayer?>(null) }

    var submitButtonEnabled by remember { mutableStateOf(false) }
    var selectList by remember { mutableStateOf(listOf<Lyric>()) }
    var quizIndex by remember { mutableIntStateOf(0) }

    when (uiState) {
        is LyricScreenUiState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(ManduGreen50)
            ) {
                // TODO: for preview
//                Box(
//                    modifier = Modifier
//                        .size(200.dp)
//                        .align(Alignment.Center)
//                        .background(Color.Red)
//                )
                LoadingLottie(
                    modifier = Modifier
                        .size(200.dp)
                        .align(Alignment.Center)
                )
            }
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

                currentYouTubePlayer?.let {
                    it.pause()
                    it.loadOrCueVideo(
                        lifecycle,
                        data[quizIndex].videoId,
                        data[quizIndex].stringTime
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(ManduGreen50)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(Green60)
                ) {
                    // TODO Preview 이용시 주석
                    AndroidView(
                        modifier = Modifier
                            .fillMaxWidth(),
                        factory = {
                            YouTubePlayerView(it).apply {
                                enableAutomaticInitialization = false
                                lifecycleOwner.lifecycle.addObserver(this)

                                val tracker = YouTubePlayerTracker()
                                addYouTubePlayerListener(tracker)

                                val youtubeListener =
                                    object : AbstractYouTubePlayerListener() {
                                        override fun onReady(youTubePlayer: YouTubePlayer) {
                                            super.onReady(youTubePlayer)

                                            val customPlayer =
                                                inflateCustomPlayerUi(R.layout.custom_player_ui)
                                            val customPlayerUiController =
                                                CustomPlayerUiController(
                                                    context,
                                                    customPlayer,
                                                    youTubePlayer,
                                                    this@apply
                                                ).apply {
                                                    initViews(customPlayer)
                                                }

                                            youTubePlayer.apply {
                                                addListener(customPlayerUiController)
                                                setPlaybackRate(PlayerConstants.PlaybackRate.RATE_1)
                                                loadOrCueVideo(
                                                    lifecycle,
                                                    data[quizIndex].videoId,
                                                    data[quizIndex].stringTime
                                                )
                                            }

                                            currentYouTubePlayer = youTubePlayer
                                        }

                                        override fun onCurrentSecond(
                                            youTubePlayer: YouTubePlayer,
                                            second: Float
                                        ) {
                                            super.onCurrentSecond(youTubePlayer, second)

                                            if (second >= data[quizIndex].endTime) {
                                                youTubePlayer.pause()
                                            }
                                        }
                                    }

                                val options: IFramePlayerOptions =
                                    IFramePlayerOptions.Builder().controls(0)
                                        .build()
                                initialize(youtubeListener, options)
                            }
                        }
                    )
                }
                Spacer(
                    modifier = Modifier
                        .height(4.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "Your Answer",
                        color = Grey90,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            currentYouTubePlayer?.seekTo(data[quizIndex].stringTime)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Refresh, contentDescription = "rewind",
                            tint = Grey90
                        )
                    }
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "${(uiState.correctCount * 10)}",
                        color = Grey90,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.End
                    )
                }
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

                                data[quizIndex].answerList = data[quizIndex].answerList
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
                        val shuffleData = data[uiState.currentQuizIndex].answerList.shuffled()

                        repeat(shuffleData.size) { index ->
                            val item = shuffleData[index]

                            if (item.isVisible) {
                                LyricItem(
                                    coroutineScope = coroutineScope,
                                    lyric = item,
                                    onComplete = {
                                        selectList = selectList
                                            .toMutableList()
                                            .apply { add(item) }

                                        uiState.data[quizIndex].answerList =
                                            uiState.data[quizIndex].answerList
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
                        containerColor = ManduGreen70,
                        disabledContentColor = Color.Black,
                        disabledContainerColor = ManduGreen20
                    ),
                    onClick = {
                        checkAnswer(
                            selectList,
                            uiState.data[uiState.currentQuizIndex].answer
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

        is LyricScreenUiState.End -> {
            var isDialogVisible by remember { mutableStateOf(true) }
            currentYouTubePlayer?.pause()

            if (isDialogVisible) {
                val artist = uiState.myArtist?.let {
                    uiState.myArtist.name
                } ?: run { "Artist" }
                OneButtonDialog(
                    content = "Your effort has contributed ${uiState.score} points to the score of your $artist.\nThank you.",
                    onPressed = {
                        isDialogVisible = false
                        onBackPressed()
                    }
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
            listOf(
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
                ),
            ),
            quizCount = 4,
            currentQuizIndex = 0
        )
    )
}