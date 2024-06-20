package com.manjee.info

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.manjee.designsystem.ui.Grey80
import com.manjee.designsystem.ui.Grey90
import com.manjee.designsystem.ui.ManduGreen50
import com.manjee.designsystem.ui.Yellow40
import com.manjee.designsystem.ui.Yellow70

@Composable
fun InfoRoute() {
    InfoScreen()
}

@Composable
internal fun InfoScreen() {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ManduGreen50)
            .padding(8.dp)
    ) {
        Text(
            modifier = Modifier.clickable {
                val intent =
                    Intent(Intent.ACTION_VIEW, Uri.parse("https://forms.gle/Qv7DWMTXMUv8Lw7D7"))
                context.startActivity(intent)
            },
            text = "If you want to request an artist addition\nclick here.",
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = Grey80
        )
        Spacer(
            modifier = Modifier
                .height(10.dp)
        )
        Text(
            modifier = Modifier.clickable {
                val intent =
                    Intent(Intent.ACTION_VIEW, Uri.parse("https://forms.gle/yjjswUxCYuEcqLST9"))
                context.startActivity(intent)
            },
            text = "If you have any requests or feedback for this app, please click here.",
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = Grey80
        )

        Spacer(
            modifier = Modifier
                .height(20.dp)
        )

        Text(
            text = "MuMu Rules.",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Yellow70
        )
        Spacer(
            modifier = Modifier
                .height(4.dp)
        )
        Text(
            text = "This app resets scores at 00:00 GMT on the 14th and 28th of each month.\nThe app allows you to accumulate points over two weeks to rank your favorite artist in the top position and help them grow within the app!",
            fontSize = 14.sp,
            color = Grey90
        )
        Spacer(
            modifier = Modifier
                .height(4.dp)
        )
        Text(
            text = "Each mode has a different scoring system, with harder modes awarding more points. There is no concept of \"clearing\" this game\nit is solely about accumulating points to help your artist grow within the app.",
            fontSize = 14.sp,
            color = Grey90
        )
        Spacer(
            modifier = Modifier
                .height(14.dp)
        )
        Text(
            text = "Now, let's go over the rewards",
            fontSize = 18.sp,
            color = Yellow70
        )
        Spacer(
            modifier = Modifier
                .height(4.dp)
        )
        Text(
            text = "2 wins: Addition of the artist's profile image.",
            fontSize = 14.sp,
            color = Grey90
        )
        Spacer(
            modifier = Modifier
                .height(6.dp)
        )
        Text(
            text = "4 wins: Addition of another profile image (from 4 wins onward, multiple artist photos will be displayed randomly).",
            fontSize = 15.sp,
            color = Grey90
        )
        Spacer(
            modifier = Modifier
                .height(6.dp)
        )
        Text(
            text = "6 wins: Artist's name color changes to the fan club color.",
            fontSize = 16.sp,
            color = Grey90
        )
        Spacer(
            modifier = Modifier
                .height(6.dp)
        )
        Text(
            text = "8 wins: Addition of the \"1st Win\" badge to the artist's name.",
            fontSize = 17.sp,
            color = Grey90
        )
        Spacer(
            modifier = Modifier
                .height(6.dp)
        )
        Text(
            text = "10 wins: A new game mode dedicated to the artist.",
            fontSize = 18.sp,
            color = Grey90
        )

    }
}

@Preview
@Composable
fun InfoScreenPreview() {
    InfoScreen()
}