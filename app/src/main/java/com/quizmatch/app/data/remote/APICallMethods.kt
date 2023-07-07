package com.quizmatch.app.data.remote

import com.quizmatch.app.data.model.api.QuestionListResponse
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface APICallMethods {
    //region 5.Interests(GET)
    @GET("api.php")
    fun getQuestionList(@Query("amount") amount: Int): Call<QuestionListResponse>
    //endregion
}
