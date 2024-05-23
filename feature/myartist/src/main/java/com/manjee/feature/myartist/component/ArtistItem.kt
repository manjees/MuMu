package com.manjee.feature.myartist.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.manjee.designsystem.ui.Blue90
import com.manjee.designsystem.ui.Brown90
import com.manjee.designsystem.ui.Green90
import com.manjee.designsystem.ui.Orange90
import com.manjee.designsystem.ui.Pink90
import com.manjee.designsystem.ui.Red90
import com.manjee.model.Artist

private val colorList = listOf(
    Red90, Blue90, Green90, Pink90, Orange90, Brown90
)

@Composable
fun ArtistItem(
    artist: Artist,
    onClick: (Artist) -> Unit
) {
    Card(
        modifier = Modifier
            .height(150.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        onClick = { onClick(artist) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .background(color = colorList.random())
            )
            Text(
                modifier = Modifier.padding(8.dp),
                text = artist.name
            )
        }
    }
}

@Composable
@Preview
fun ArtistItemPreview() {
    ArtistItem(Artist(0, "asd", 0)) {}
}