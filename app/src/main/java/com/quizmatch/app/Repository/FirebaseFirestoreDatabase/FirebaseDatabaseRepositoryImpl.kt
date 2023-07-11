package com.policypal.app.data.remote

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.quizmatch.app.Repository.FirebaseFirestoreDatabase.FirebaseDatabaseRepository
import com.quizmatch.app.data.model.firebase.Match
import com.quizmatch.app.data.model.firebase.Score
import com.quizmatch.app.data.model.firebase.User
import com.quizmatch.app.utils.CustomException
import com.quizmatch.app.utils.FirebaseKeys
import com.quizmatch.app.utils.FirebaseKeys.EMAIL_DB_KEY
import com.quizmatch.app.utils.FirebaseKeys.FIRST_NAME_DB_KEY
import com.quizmatch.app.utils.FirebaseKeys.LAST_NAME_DB_KEY
import com.quizmatch.app.utils.FirebaseKeys.MATCH_COLLECTION
import com.quizmatch.app.utils.FirebaseKeys.MATCH_ID_DB_KEY
import com.quizmatch.app.utils.FirebaseKeys.MATCH_STATUS_DB_KEY
import com.quizmatch.app.utils.FirebaseKeys.OPPONENT_DB_KEY
import com.quizmatch.app.utils.FirebaseKeys.PENDING_DB_KEY
import com.quizmatch.app.utils.FirebaseKeys.SCORE_DB_KEY
import com.quizmatch.app.utils.FirebaseKeys.SCORE_ID_DB_KEY
import com.quizmatch.app.utils.FirebaseKeys.USER_COLLECTION
import com.quizmatch.app.utils.FirebaseKeys.USER_ID_DB_KEY
import java.sql.Timestamp


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
     * Method to get User Detail from Collection:-  "Users"
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
                        matchId = snapshot.data!![MATCH_ID_DB_KEY] as MutableList<String>
                    )
                onSuccess(userData)
            } else {
                onError(CustomException(message = "User doesn't exist with this email."))
            }
        }
    }

    override fun getPlayerList(userID:String,onSuccess: (List<User>) -> Unit, onError: (Exception?) -> Unit) {
        database.collection(USER_COLLECTION).whereNotEqualTo(USER_ID_DB_KEY,userID).addSnapshotListener(){
                snapshot,e->

            if (e != null) {
                onError(e)
                return@addSnapshotListener
            }
            if (snapshot != null) {
                onSuccess(snapshot.toObjects(User::class.java).toList<User>())
            } else {
                onError(
                    CustomException(
                        message = "user list does not exist"
                    )
                )
            }

        }
    }

    override fun createMatch(
        userID: String,
        opponentId: String,
        onSuccess: (String) -> Unit,
        onError: (Exception?) -> Unit
    ) {
       val matchRef =  database.collection(MATCH_COLLECTION).document()
        val matchData = hashMapOf<String, Any?>(
            FirebaseKeys.CREATED_AT_DB_KEY to com.google.firebase.Timestamp.now() ,
            FirebaseKeys.MATCH_ID_DB_KEY to matchRef.id,
            FirebaseKeys.MATCH_NAME_DB_KEY to "default",
            FirebaseKeys.MATCH_STATUS_DB_KEY to PENDING_DB_KEY,
            FirebaseKeys.PLAYER_DB_KEY to listOf<String>(userID,opponentId)
        )
        val userRef = database.collection(USER_COLLECTION).document(userID)
        val oponentRef = database.collection(USER_COLLECTION).document(opponentId)
        database.runBatch { batch ->
            matchRef.set(matchData)
            val userScore = hashMapOf<String, Any?>(
                FirebaseKeys.CREATED_AT_DB_KEY to com.google.firebase.Timestamp.now() ,
                FirebaseKeys.UPDATED_AT_DB_KEY to com.google.firebase.Timestamp.now() ,
                SCORE_ID_DB_KEY to userID,
                FirebaseKeys.SCORE_DB_KEY to 0)
            val opponentScore = hashMapOf<String, Any?>(
                FirebaseKeys.CREATED_AT_DB_KEY to com.google.firebase.Timestamp.now() ,
                FirebaseKeys.UPDATED_AT_DB_KEY to com.google.firebase.Timestamp.now() ,
                SCORE_ID_DB_KEY to opponentId,
                FirebaseKeys.SCORE_DB_KEY to 0)
            matchRef.collection(SCORE_DB_KEY).document(userID).set(userScore)
            matchRef.collection(SCORE_DB_KEY).document(opponentId).set(opponentScore)
            userRef.update(MATCH_ID_DB_KEY, FieldValue.arrayUnion(matchRef.id))
            oponentRef.update(MATCH_ID_DB_KEY, FieldValue.arrayUnion(matchRef.id))
        }.addOnSuccessListener { result ->
            onSuccess(matchRef.id)
        }.addOnFailureListener { e ->

            onError(e)
        }

    }

    override fun getScoreDetail(matchId: String,scoreId:String,
        onSuccess: (List<Score>) -> Unit,
        onError: (Exception?) -> Unit
    ) {
       val matchRef =  database.collection(MATCH_COLLECTION).document(matchId)
        matchRef.collection(SCORE_DB_KEY).whereNotEqualTo(SCORE_ID_DB_KEY,scoreId).addSnapshotListener{ snapshot, e->

            if (e != null) {
                onError(e)
                return@addSnapshotListener
            }
            if (snapshot != null) {
                onSuccess(snapshot.toObjects(Score::class.java).toList<Score>())
            } else {
                onError(
                    CustomException(
                        message = "user list does not exist"
                    )
                )
            }
        }

    }

    override fun updateScore(
        matchId:String,
        score: Int?,
        userId:String,
        onSuccess: (Int) -> Unit,
        onError: (Exception?) -> Unit
    ) {

database.collection(MATCH_COLLECTION).document(matchId).collection(SCORE_DB_KEY).document(userId)
    .update(SCORE_DB_KEY,score).addOnSuccessListener {

            onSuccess(score!!)


    }
    .addOnFailureListener{
        onError(it)

    }




        database.runBatch{

        }


    }

}