package com.manjee.firebase.mapper

import com.manjee.firebase.model.LyricQuizDataModel
import org.junit.Assert.*
import org.junit.Test

class LyricQuizDataModelTest {

    @Test
    fun `test toLyric`() {
        val lyricDataModel = LyricQuizDataModel(
            answer = "Hello,world",
            startTime = 0,
            endTime = 10,
            videoId = "123"
        )

        // 데이터 모델을 변환
        val lyricQuiz = lyricDataModel.toLyric()

        assertEquals("Helloworld", lyricQuiz.answer) // 쉼표가 제거되었는지 확인
        assertEquals(2, lyricQuiz.answerList.size) // 정답 수 확인
        assertEquals("Hello", lyricQuiz.answerList[0].str) // 첫 번째 정답 확인
        assertEquals("world", lyricQuiz.answerList[1].str) // 두 번째 정답 확인
        assertEquals(0.0f, lyricQuiz.stringTime) // 시작 시간 확인
        assertEquals(10.0f, lyricQuiz.endTime) // 끝 시간 확인
        assertEquals("123", lyricQuiz.videoId) // 비디오 ID 확인
    }
}