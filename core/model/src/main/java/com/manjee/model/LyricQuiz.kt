package com.manjee.model

data class LyricQuiz(
    val answer: String,
    var answerList: List<Lyric>,
    val stringTime: Float,
    val endTime: Float,
    val videoId: String
)
