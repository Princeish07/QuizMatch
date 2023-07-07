package com.quizmatch.app.data.model.api

data class QuestionListResponse(
    val response_code: Int,
    var results: MutableList<Result>
) {
    data class Result(
        val category: String,
        val correct_answer: String,
        val difficulty: String,
        val incorrect_answers: MutableList<String>,
        val question: String,
        val type: String
    )
}