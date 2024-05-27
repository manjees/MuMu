package com.manjee.main.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.manjee.designsystem.ui.Blue90
import com.manjee.designsystem.ui.Brown90
import com.manjee.designsystem.ui.Green90
import com.manjee.designsystem.ui.Grey90
import com.manjee.designsystem.ui.Orange90
import com.manjee.designsystem.ui.Pink90
import com.manjee.designsystem.ui.Red90
import com.manjee.model.Artist

private val colorList = listOf(
    Red90, Blue90, Green90, Pink90, Orange90, Brown90
)

@Composable
fun RankItem(myArtist: Artist?, artist: Artist) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(45.dp)
            .padding(horizontal = 16.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(35.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(25.dp)
                )
        ) {
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .background(
                        color = colorList.random(),
                        shape = RoundedCornerShape(25.dp)
                    )
                    .align(Alignment.Center)
            )
        }
        Text(
            modifier = Modifier
                .padding(horizontal = 6.dp)
                .weight(1f),
            text = artist.name,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            color = Grey90
        )
        myArtist?.let {
            if (myArtist.id == artist.id) {
                Box(
                    modifier = Modifier
                        .background(
                            color = Color.Black,
                            shape = RoundedCornerShape(16.dp)
                        )
                ) {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        text = "Me",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = Color.White
                    )
                }
            }
        }
        Spacer(modifier = Modifier
            .width(4.dp)
        )
        Box(
            modifier = Modifier
                .background(
                    color = colorList.random(),
                    shape = RoundedCornerShape(25.dp)
                )
                .size(30.dp)
                .align(Alignment.CenterVertically),
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.Center),
                text = artist.score.toString(),
                fontWeight = FontWeight.SemiBold,
                fontSize = 10.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
fun RankItemPreview() {
    RankItem(
        null,
        Artist(
            id = 0L,
            name = "BTS",
            score = 1230,
        )
    )
}