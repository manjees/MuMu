package com.manjee.info

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.manjee.designsystem.ui.ManduGreen50

@Composable
fun InfoRoute() {
    InfoScreen()
}

@Composable
internal fun InfoScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ManduGreen50)
    ) {
        Text(text = "Info Screen")
    }
}

@Preview
@Composable
fun InfoScreenPreview() {
    InfoScreen()
}