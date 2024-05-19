package com.manjee.title

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.manjee.designsystem.ui.ManduGreen50
import com.manjee.designsystem.ui.Yellow30

@Composable
fun TitleRoute() {

}

@Composable
fun TitleScreen() {
    val configuration = LocalConfiguration.current

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
            border = BorderStroke(1.dp, Color.Black),
        ) {}
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = (configuration.screenHeightDp * 0.15).dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .width((configuration.screenWidthDp * 0.8).dp)
                    .aspectRatio(3f / 2f)
                    .background(Color.White, shape = RoundedCornerShape(20.dp))
            )
        }
    }
}

@Preview
@Composable
fun TitleScreenPreview() {
    TitleScreen()
}
