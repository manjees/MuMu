package com.manjee.title

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
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.manjee.designsystem.ui.ManduGreen50
import com.manjee.designsystem.ui.ManduGreen90
import com.manjee.designsystem.ui.Red70
import com.manjee.designsystem.ui.Yellow30
import com.manjee.designsystem.ui.Yellow70

@Composable
fun TitleRoute() {
    TitleScreen()
}

@Composable
fun TitleScreen() {
    val configuration = LocalConfiguration.current

    val list = listOf("aaaa", "aaaa", "aaaa", "aaaa")

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
                            text = "08",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = ManduGreen90
                        )
                        LinearProgressIndicator(
                            modifier = Modifier
                                .height(10.dp)
                                .weight(1f)
                                .padding(horizontal = 4.dp),
                            progress = { 1f },
                            strokeCap = StrokeCap.Round,
                            color = ManduGreen90,
                            trackColor = Color.White
                        )
                        LinearProgressIndicator(
                            modifier = Modifier
                                .height(10.dp)
                                .weight(1f)
                                .padding(horizontal = 4.dp),
                            progress = { 1f },
                            strokeCap = StrokeCap.Round,
                            color = Red70,
                            trackColor = Color.White
                        )
                        Text(
                            text = "08",
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
                        text = "Question 1/30",
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
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                repeat(list.size) {
                    Button(
                        modifier = Modifier
                            .width((configuration.screenWidthDp * 0.8).dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black,
                        ),
                        border = BorderStroke(2.dp, Yellow70),
                        onClick = { /*TODO*/ }
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Button $it",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Button Sub $it",
                                fontSize = 14.sp,
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
                    text = "30",
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Black
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
                contentDescription = "backbutton"
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

@Preview
@Composable
fun TitleScreenPreview() {
    TitleScreen()
}
