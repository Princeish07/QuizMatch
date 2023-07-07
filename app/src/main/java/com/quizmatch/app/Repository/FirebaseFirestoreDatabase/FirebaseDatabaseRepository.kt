package com.quizmatch.app.Repository.FirebaseFirestoreDatabase

import com.quizmatch.app.data.model.firebase.User


interface FirebaseDatabaseRepository {

    /**
     * Method to create user in firebase
     */
    fun createUser(
        user: HashMap<String, Any?>,
        onSuccess: () -> Unit,
        onError: (Exception?) -> Unit
    )

    /**
     * Method to get user details from firebase
     */
    fun getUser(
        userId: String?,
        onSuccess: (User) -> Unit,
        onError: (Exception?) -> Unit
    )

    /**
     * Method to update user details in firebase
     * Collection 'users'
     */
    fun updateScore(
        score: String?,
        onSuccess: (User) -> Unit,
        onError: (Exception?) -> Unit
    )
}