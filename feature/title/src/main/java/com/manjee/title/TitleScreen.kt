package com.manjee.title

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.manjee.designsystem.component.OneButtonDialog
import com.manjee.designsystem.ui.Green70
import com.manjee.designsystem.ui.Grey90
import com.manjee.designsystem.ui.ManduGreen50
import com.manjee.designsystem.ui.ManduGreen90
import com.manjee.designsystem.ui.Red70
import com.manjee.designsystem.ui.Yellow30
import com.manjee.designsystem.ui.Yellow70
import com.manjee.model.Title
import com.manjee.model.TitleQuiz
import com.manjee.nocaptionyoutubeplayer.PlayerConstants
import com.manjee.nocaptionyoutubeplayer.YouTubePlayer
import com.manjee.nocaptionyoutubeplayer.listeners.AbstractYouTubePlayerListener
import com.manjee.nocaptionyoutubeplayer.options.IFramePlayerOptions
import com.manjee.nocaptionyoutubeplayer.utils.YouTubePlayerTracker
import com.manjee.nocaptionyoutubeplayer.utils.loadOrCueVideo
import com.manjee.nocaptionyoutubeplayer.views.YouTubePlayerView
import com.manjee.title.component.QuizCountProgress
import com.manjee.title.util.CustomPlayerUiController

private const val SETTING_TIMER = 30
private const val INIT_TIMER = 90
private const val START_VIDEO_TIME = 60f

@Composable
fun TitleRoute(
    viewModel: TitleViewModel = hiltViewModel(),
    onBackPressed: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    TitleScreen(
        uiState = uiState,
        correctQuiz = viewModel::correctAnswer,
        incorrectQuiz = viewModel::incorrectAnswer,
        onBackPressed = onBackPressed
    )
}

@SuppressLint("DefaultLocale")
@Composable
fun TitleScreen(
    uiState: TitleScreenUiState,
    correctQuiz: () -> Unit = {},
    incorrectQuiz: () -> Unit = {},
    onBackPressed: () -> Unit = {}
) {
    val configuration = LocalConfiguration.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycle = lifecycleOwner.lifecycle

    var remainTime by remember { mutableIntStateOf(30) }
    var currentYouTubePlayer by remember { mutableStateOf<YouTubePlayer?>(null) }

    val infiniteTransition = rememberInfiniteTransition(label = "")
    val boxColor by infiniteTransition.animateColor(
        initialValue = Yellow70,
        targetValue = Green70,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

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
            var choiceList by remember {
                mutableStateOf(
                    uiState.data.choiceList.shuffled().take(4)
                )
            }

            val boxHeight by animateDpAsState(
                targetValue = if (remainTime > 27) 250.dp else 0.dp, label = "",
            )

            var isShowVideoChecked by remember { mutableStateOf(true) }

            LaunchedEffect(uiState.currentQuizIndex) {
                remainTime = 30

                currentYouTubePlayer?.let {
                    it.pause()
                    it.loadOrCueVideo(lifecycle, currentQuiz.videoId, START_VIDEO_TIME)
                }

                val filteredChoices = uiState.data.choiceList.filter { it != currentQuiz.title }
                choiceList = (filteredChoices.shuffled().take(3) + currentQuiz.title).shuffled()
            }

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
                            .height(280.dp),
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
                                    .background(Color.Transparent, RoundedCornerShape(20.dp))
                            ) {
                                Card(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Color.Transparent),
                                    shape = RoundedCornerShape(20.dp),
                                    colors = CardColors(
                                        containerColor = Color.Black,
                                        contentColor = Color.Black,
                                        disabledContentColor = Color.Gray,
                                        disabledContainerColor = Color.Transparent
                                    ),
                                    onClick = { }
                                ) {
                                    // TODO: preview 이용시 주석
                                    AndroidView(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(250.dp)
                                            .background(
                                                Color.Transparent,
                                                RoundedCornerShape(20.dp)
                                            ),
                                        factory = {
                                            YouTubePlayerView(it).apply {
                                                enableAutomaticInitialization = false
                                                lifecycleOwner.lifecycle.addObserver(this)

                                                val tracker = YouTubePlayerTracker()
                                                addYouTubePlayerListener(tracker)

                                                val youtubeListener =
                                                    object : AbstractYouTubePlayerListener() {
                                                        override fun onCurrentSecond(
                                                            youTubePlayer: YouTubePlayer,
                                                            second: Float
                                                        ) {
                                                            super.onCurrentSecond(
                                                                youTubePlayer,
                                                                second
                                                            )

                                                            remainTime =
                                                                (INIT_TIMER - second.toInt())

                                                            if (remainTime <= 0) {
                                                                remainTime = 30

                                                                incorrectQuiz()
                                                            }
                                                        }

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
                                                                    currentQuiz.videoId,
                                                                    START_VIDEO_TIME
                                                                )
                                                            }

                                                            currentYouTubePlayer = youTubePlayer
                                                        }

                                                        override fun onStateChange(
                                                            youTubePlayer: YouTubePlayer,
                                                            state: PlayerConstants.PlayerState
                                                        ) {
                                                            super.onStateChange(
                                                                youTubePlayer,
                                                                state
                                                            )
                                                        }

                                                        override fun onVideoDuration(
                                                            youTubePlayer: YouTubePlayer,
                                                            duration: Float
                                                        ) {
                                                            super.onVideoDuration(
                                                                youTubePlayer,
                                                                duration
                                                            )
                                                        }

                                                        override fun onVideoId(
                                                            youTubePlayer: YouTubePlayer,
                                                            videoId: String
                                                        ) {
                                                            super.onVideoId(youTubePlayer, videoId)
                                                        }

                                                        override fun onVideoLoadedFraction(
                                                            youTubePlayer: YouTubePlayer,
                                                            loadedFraction: Float
                                                        ) {
                                                            super.onVideoLoadedFraction(
                                                                youTubePlayer,
                                                                loadedFraction
                                                            )
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
                                if (remainTime == 30) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(250.dp)
                                            .align(Alignment.TopCenter)
                                            .background(boxColor, RoundedCornerShape(20.dp))
                                    )
                                } else {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(if (isShowVideoChecked) boxHeight else 250.dp)
                                            .align(Alignment.TopCenter)
                                            .background(boxColor, RoundedCornerShape(20.dp))
                                    )
                                }
                            }
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
                                        remainTime = 30
                                        
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
                    } else {
                        currentYouTubePlayer?.pause()

                        OneButtonDialog(
                            content = "Your effort has improved the score of your artist.\nThank you.",
                            onPressed = {
                                onBackPressed()
                            }
                        )
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
                        checked = isShowVideoChecked,
                        colors = CheckboxDefaults.colors(
                            checkedColor = Color.White,
                            uncheckedColor = Color.Black,
                            checkmarkColor = Color.Black
                        ),
                        onCheckedChange = {
                            isShowVideoChecked = it
                        }
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
        ),
    )
}
