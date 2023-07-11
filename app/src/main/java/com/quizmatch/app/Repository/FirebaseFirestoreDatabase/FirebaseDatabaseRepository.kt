package com.quizmatch.app.Repository.FirebaseFirestoreDatabase

import com.quizmatch.app.data.model.firebase.Match
import com.quizmatch.app.data.model.firebase.Score
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
        matchId:String,
        score: Int?,
        userId:String,
        onSuccess: (Int) -> Unit,
        onError: (Exception?) -> Unit
    )

    /**
     * Get List of User in application
     */

    fun getPlayerList(userId:String,onSuccess: (List<User>) -> Unit,
    onError: (Exception?) -> Unit)

    /**
     * create Match collection "match"
     */

    fun createMatch(  userID: String,
                      opponentId: String,onSuccess: (String) -> Unit,
                      onError: (Exception?) -> Unit)

    /**
     * Get score detail in application
     */

    fun getScoreDetail(matchId: String,scoreId:String, onSuccess: (List<Score>) -> Unit,
                       onError: (Exception?) -> Unit)
}