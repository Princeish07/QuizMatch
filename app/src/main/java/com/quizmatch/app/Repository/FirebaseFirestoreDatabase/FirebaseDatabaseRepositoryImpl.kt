package com.policypal.app.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.quizmatch.app.Repository.FirebaseFirestoreDatabase.FirebaseDatabaseRepository
import com.quizmatch.app.data.model.firebase.User
import com.quizmatch.app.utils.CustomException
import com.quizmatch.app.utils.FirebaseKeys.EMAIL_DB_KEY
import com.quizmatch.app.utils.FirebaseKeys.FIRST_NAME_DB_KEY
import com.quizmatch.app.utils.FirebaseKeys.LAST_NAME_DB_KEY
import com.quizmatch.app.utils.FirebaseKeys.MATCH_STATUS_DB_KEY
import com.quizmatch.app.utils.FirebaseKeys.OPPONENT_DB_KEY
import com.quizmatch.app.utils.FirebaseKeys.USER_COLLECTION
import com.quizmatch.app.utils.FirebaseKeys.USER_ID_DB_KEY


class FirebaseDatabaseRepositoryImpl : FirebaseDatabaseRepository {

    private var database: FirebaseFirestore = Firebase.firestore

    /**
     * Method to create User
     */
    override fun createUser(
        user: HashMap<String, Any?>, onSuccess: () -> Unit, onError: (Exception?) -> Unit
    ) {
        val userRef = database.collection(USER_COLLECTION).document(user[USER_ID_DB_KEY].toString())
        userRef.set(user).addOnSuccessListener {
            onSuccess()
        }.addOnFailureListener { e ->
            onError(e)
        }
    }

    /**
     * Method to get User from Collection:-  "Users"
     * Get List of documents from "Users" Collection
     */
    override fun getUser(
        userId: String?, onSuccess: (User) -> Unit, onError: (Exception?) -> Unit
    ) {
        val userRef = database.collection(USER_COLLECTION).document(userId!!)
        userRef.addSnapshotListener() { snapshot,e ->
            if (snapshot != null && snapshot.data != null) {
                val userData =
                    User(
                        email = snapshot.data!![EMAIL_DB_KEY]?.toString()!!,
                        first_name = snapshot.data!![FIRST_NAME_DB_KEY]?.toString()!!,
                        last_name = snapshot.data!![LAST_NAME_DB_KEY]?.toString()!!,
                        user_id = snapshot.data!![USER_ID_DB_KEY]?.toString()!!,
                        match_status = snapshot.data!![MATCH_STATUS_DB_KEY]?.toString()!!,
                        opponent_id = snapshot.data!![OPPONENT_DB_KEY]?.toString()!!
                        )
                onSuccess(userData)
            } else {
                onError(CustomException(message = "User doesn't exist with this email."))
            }
        }
    }

    override fun updateScore(
        score: String?,
        onSuccess: (User) -> Unit,
        onError: (Exception?) -> Unit
    ) {

    }

}