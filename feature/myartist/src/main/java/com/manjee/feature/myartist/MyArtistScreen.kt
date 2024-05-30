package com.manjee.feature.myartist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.manjee.designsystem.component.LoadingLottie
import com.manjee.designsystem.ui.Grey90
import com.manjee.designsystem.ui.ManduGreen50
import com.manjee.feature.myartist.component.ArtistConfirmDialog
import com.manjee.feature.myartist.component.ArtistItem
import com.manjee.model.Artist

@Composable
fun MyArtistRoute(
    viewModel: MyArtistViewModel = hiltViewModel(),
    onBackPressed: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MyArtistScreen(
        uiState = uiState,
        search = viewModel::search,
        updateMyArtist = viewModel::updateMyArtist,
        onBackPressed = onBackPressed
    )
}

@Composable
fun MyArtistScreen(
    uiState: MyArtistScreenUiState,
    search: (String) -> Unit = {},
    updateMyArtist: (Artist) -> Unit = {},
    onBackPressed: () -> Unit = {}
) {
    when (uiState) {
        is MyArtistScreenUiState.Loading -> {
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

        is MyArtistScreenUiState.Error -> {
            Text(text = "Error")
        }

        is MyArtistScreenUiState.Complete -> {
            onBackPressed()
        }

        is MyArtistScreenUiState.Success -> {
            var isConfirmDialogVisible by remember { mutableStateOf(false) }
            var selectArtist by remember { mutableStateOf<Artist?>(null) }
            var inputText by remember { mutableStateOf("") }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(ManduGreen50)
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp)
            ) {
                Text(
                    "Welcome to MuMu",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Grey90
                )
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = "Please select your favorite artist",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Grey90
                )
                Spacer(modifier = Modifier.height(18.dp))
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(bottom = 56.dp),
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    items(uiState.data) { artist ->
                        ArtistItem(
                            artist = artist,
                            onClick = {
                                selectArtist = it
                                isConfirmDialogVisible = true
                            }
                        )
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 24.dp)
                    .padding(horizontal = 16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .background(Color.White, RoundedCornerShape(20.dp))
                ) {
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        value = inputText,
                        onValueChange = { newText ->
                            inputText = newText
                            search(newText)
                        },
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = Grey90,
                            unfocusedTextColor = Grey90,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        ),
                        label = { Text(text = "Search your artist") }
                    )
                }
            }

            if (isConfirmDialogVisible && selectArtist != null) {
                ArtistConfirmDialog(
                    artist = selectArtist!!,
                    onConfirm = {
                        isConfirmDialogVisible = false
                        updateMyArtist(it)
                    },
                    onCancel = {
                        isConfirmDialogVisible = false
                    }
                )
            }
        }
    }
}

@Composable
@Preview
fun MyArtistScreenPreview() {
    MyArtistScreen(
        uiState = MyArtistScreenUiState.Success(
            data = listOf(
                Artist(0, "dwdqd", 0),
                Artist(0, "dwdqd", 0),
                Artist(0, "dwdqd", 0),
                Artist(0, "dwdqd", 0),
                Artist(0, "dwdqd", 0),
                Artist(0, "dwdqd", 0),
                Artist(0, "dwdqd", 0),
                Artist(0, "dwdqd", 0),
                Artist(0, "dwdqd", 0),
                Artist(0, "dwdqd", 0),
            )
        )
    )
}