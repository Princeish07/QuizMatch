package com.quizmatch.app.Repository.question

import com.quizmatch.app.Repository.FirebaseFirestoreDatabase.FirebaseDatabaseRepository
import com.quizmatch.app.data.model.api.QuestionListResponse
import com.quizmatch.app.data.model.firebase.User
import com.quizmatch.app.data.remote.APICallMethods

interface QuestionRepo {
    fun getQuestList(
        apiCallMethods: APICallMethods,
        firebaseDatabaseRepo: FirebaseDatabaseRepository,
        onSuccess: (QuestionListResponse) -> Unit,
        onError: (Exception?) -> Unit
    )
}