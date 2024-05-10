package com.manjee.firebase.mapper

import com.manjee.firebase.model.LyricQuizDataModel
import com.manjee.model.Lyric
import com.manjee.model.LyricQuiz

fun LyricQuizDataModel.toLyric(): LyricQuiz {
    val answerString = this.answer.replace(",", "")

    val answerSplit = this.answer.split(",")
    val answerList = answerSplit.mapIndexed { index: Int, str: String ->
        Lyric(
            index = index,
            str = str,
            isVisible = true
        )
    }

    return LyricQuiz(
        answer = answerString,
        answerList = answerList,
        stringTime = this.startTime.toFloat(),
        endTime = this.endTime.toFloat(),
        videoId = this.videoId
    )
}