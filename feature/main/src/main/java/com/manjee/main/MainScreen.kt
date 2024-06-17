@file:OptIn(ExperimentalFoundationApi::class)

package com.manjee.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.manjee.designsystem.component.LoadingLottie
import com.manjee.designsystem.component.NoPaddingText
import com.manjee.designsystem.ui.Blue20
import com.manjee.designsystem.ui.Green20
import com.manjee.designsystem.ui.Grey40
import com.manjee.designsystem.ui.Grey60
import com.manjee.designsystem.ui.Grey90
import com.manjee.designsystem.ui.ManduGreen20
import com.manjee.designsystem.ui.ManduGreen50
import com.manjee.designsystem.ui.Pink20
import com.manjee.designsystem.ui.Red20
import com.manjee.feature.splash.R
import com.manjee.main.component.RankItem
import com.manjee.main.component.RequestArtistDialog
import com.manjee.model.Artist
import com.manjee.model.MainScreenData
import com.manjee.model.Theme
import kotlin.math.absoluteValue
import kotlin.random.Random

@Composable
fun MainRoute(
    viewModel: MainViewModel = hiltViewModel(),
    navigateToLyric: () -> Unit,
    navigateToTitle: (quizId: String) -> Unit,
    navigateToArtist: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.checkShowArtistRequestDialog()
    }

    MainScreen(
        uiState = uiState,
        updateShowDialog = viewModel::updateShowArtistRequestDialog,
        navigateToLyric = navigateToLyric,
        navigateToTitle = navigateToTitle,
        navigateToArtist = navigateToArtist
    )
}

@Composable
internal fun MainScreen(
    uiState: MainScreenUiState,
    updateShowDialog: () -> Unit,
    navigateToLyric: () -> Unit,
    navigateToTitle: (quizId: String) -> Unit,
    navigateToArtist: () -> Unit,
) {
    val configuration = LocalConfiguration.current

    val cardColorList = listOf(Red20, Green20, Blue20, Pink20, ManduGreen20)

    when (uiState) {
        is MainScreenUiState.Loading -> {
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

        is MainScreenUiState.Error -> {
            Text(
                text = "Error: ${uiState.message}",
                color = Grey90,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        is MainScreenUiState.Success -> {
            val mainScreenData = uiState.mainScreenData
            val pagerState = rememberPagerState(0, pageCount = { mainScreenData.themeList.size })

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(ManduGreen50)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .padding(start = 24.dp)
                ) {
                    NoPaddingText(
                        modifier = Modifier
                            .weight(1f),
                        text = stringResource(id = R.string.title),
                        fontWeight = FontWeight.Bold,
                        fontSize = 52.sp,
                        color = Grey90
                    )
                    IconButton(
                        modifier = Modifier
                            .size(48.dp),
                        onClick = {
                            navigateToArtist()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = "Profile",
                            tint = Grey90
                        )
                    }
                }
                NoPaddingText(
                    modifier = Modifier
                        .padding(horizontal = 24.dp, vertical = 8.dp),
                    text = stringResource(id = R.string.subtitle),
                    fontSize = 14.sp,
                    color = Grey90
                )
                HorizontalPager(
                    state = pagerState,
                    contentPadding = PaddingValues(
                        start = (configuration.screenWidthDp.dp / 4) - 8.dp, // 시작 패딩 설정
                        end = (configuration.screenWidthDp.dp / 4) - 8.dp // 끝 패딩 설정
                    ),
                ) {
                    val theme: Theme = mainScreenData.themeList[it]
                    val shadow = if (it == pagerState.currentPage) 15.dp else 0.dp

                    Card(
                        Modifier
                            .size(300.dp)
                            .padding(16.dp)
                            .shadow(shadow, shape = RoundedCornerShape(16.dp))
                            .graphicsLayer {
                                val pageOffset = (
                                        (pagerState.currentPage - it) + pagerState
                                            .currentPageOffsetFraction
                                        ).absoluteValue

                                alpha = lerp(
                                    start = 0.5f,
                                    stop = 1f,
                                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                )
                            }
                            .clickable {
                                if (theme.id == "none")  {
                                    return@clickable
                                }

                                when (theme.isTitle) {
                                    true -> navigateToTitle(theme.id)
                                    false -> navigateToLyric()
                                }
                            }
                    ) {
                        Box(
                            modifier = Modifier
                                .background(cardColorList[Random.nextInt(cardColorList.size - 1)])
                                .fillMaxSize()
                                .padding(16.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize(),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                NoPaddingText(
                                    text = theme.themeName,
                                    color = Grey60,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                NoPaddingText(
                                    text = theme.description,
                                    color = Grey40,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.Transparent)
                ) {
                    items(mainScreenData.rankingList.size) {
                        RankItem(mainScreenData.myArtist, mainScreenData.rankingList[it])
                    }
                }
            }

            if (mainScreenData.isRequestVisible) {
                RequestArtistDialog(
                    onCancel = {
                        updateShowDialog()
                    },
                    onConfirm = {
                        updateShowDialog()
                        navigateToArtist()
                    }
                )
            }
        }
    }
}

@Composable
@Preview
fun MainScreenPreview() {
    MainScreen(
        uiState = MainScreenUiState.Success(
            MainScreenData(
                true, Artist(0L, "sd", 0), listOf(), listOf()
            )
        ),
        {},
        {},
        {},
        {})
}

@Composable
@Preview
fun MainScreenLoadingPreview() {
    MainScreen(uiState = MainScreenUiState.Loading, {}, {}, {}, {})
}