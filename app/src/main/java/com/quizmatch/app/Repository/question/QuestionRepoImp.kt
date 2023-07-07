package com.quizmatch.app.Repository.question

import com.quizmatch.app.Repository.FirebaseFirestoreDatabase.FirebaseDatabaseRepository
import com.quizmatch.app.data.model.api.QuestionListResponse
import com.quizmatch.app.data.model.firebase.User
import com.quizmatch.app.data.remote.APICallMethods
import com.quizmatch.app.utils.CustomException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuestionRepoImp:QuestionRepo {
    override  fun getQuestList(
        apiCallMethods: APICallMethods,
        firebaseDatabaseRepo: FirebaseDatabaseRepository,
        onSuccess: (QuestionListResponse) -> Unit,
        onError: (Exception?) -> Unit
    ){
        apiCallMethods.getQuestionList(5).enqueue(object : Callback<QuestionListResponse> {
            override fun onResponse(
                call: Call<QuestionListResponse>,
                response: Response<QuestionListResponse>
            ) {
                if (response.isSuccessful) {
                    val questionListResponse = response.body()
                    onSuccess(questionListResponse!!)

                } else {
                    onError(CustomException())

                }
            }

            override fun onFailure(call: Call<QuestionListResponse>, t: Throwable) {
                onError(CustomException(t.message))

            }

        })



    }
}