package com.manjee.designsystem.component

interface LottieState {
    object Idle : LottieState
    data class Playing(val lottieResId: Int) : LottieState
}