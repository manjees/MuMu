package com.manjee.main.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.manjee.designsystem.ui.Grey50
import com.manjee.designsystem.ui.Grey90
import com.manjee.designsystem.ui.ManduGreen50
import com.manjee.designsystem.ui.Red20
import com.manjee.designsystem.ui.Red50

@Composable
fun RequestArtistDialog(
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    Dialog(
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            decorFitsSystemWindows = false
        ),
        onDismissRequest = {
            // no-op
        }
    ) {
        RequestArtistDialogContent(
            onConfirm = onConfirm,
            onCancel = onCancel
        )
    }
}

@Composable
fun RequestArtistDialogContent(
    onConfirm: () -> Unit = {},
    onCancel: () -> Unit = {}
) {
    val configuration = LocalConfiguration.current

    Box(
        modifier = Modifier
            .width((configuration.screenWidthDp * 0.8).dp)
            .wrapContentHeight()
            .background(Color.White, RoundedCornerShape(20.dp))
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = "Please select your favorite artist!",
                fontSize = 18.sp,
                color = Grey90,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = "You can support\nthe growth of the artist you choose",
                fontSize = 14.sp,
                color = Grey50,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
            Spacer(
                modifier = Modifier
                    .height(16.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Button(
                    modifier = Modifier
                        .weight(1f),
                    colors = ButtonColors(
                        containerColor = Red50,
                        contentColor = Color.White,
                        disabledContentColor = Color.White,
                        disabledContainerColor = Red20
                    ),
                    onClick = {
                        onCancel()
                    }
                ) {
                    Text("Nope")
                }
                Spacer(
                    modifier = Modifier
                        .width(4.dp)
                )
                Button(
                    modifier = Modifier
                        .weight(1f),
                    colors = ButtonColors(
                        containerColor = ManduGreen50,
                        contentColor = Color.White,
                        disabledContentColor = Color.White,
                        disabledContainerColor = Red20
                    ),
                    onClick = {
                        onConfirm()
                    }
                ) {
                    Text("Okay")
                }
            }
        }
    }
}

@Composable
@Preview
fun RequestArtistDialogPreview() {
    RequestArtistDialogContent()
}