package com.quizmatch.app.Repository.FirebaseAuth

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.quizmatch.app.Repository.FirebaseAuth.FirebaseAuthRepo
import com.quizmatch.app.Repository.FirebaseFirestoreDatabase.FirebaseDatabaseRepository
import com.quizmatch.app.data.model.firebase.User
import com.quizmatch.app.data.remote.APICallMethods
import com.quizmatch.app.utils.FirebaseKeys
import com.quizmatch.app.utils.FirebaseKeys.PENDING_DB_KEY
import com.quizmatch.app.utils.FirebaseKeys.SCORE_DB_KEY
import javax.inject.Inject

/*
*/class FirebaseAuthRepoImp @Inject constructor(private val apiCallMethod:APICallMethods)
    : FirebaseAuthRepo {
     var auth: FirebaseAuth

    init {
        auth = Firebase.auth
    }

    override fun logIn(
        email: String,
        password: String,
        firebaseDatabaseRepo: FirebaseDatabaseRepository,
        onSuccess: (User?) -> Unit,
        onError: (Exception?) -> Unit
    )  {
    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                firebaseDatabaseRepo.getUser(user?.uid, onSuccess = { userData ->
                    onSuccess(userData)
                }, onError = {
                    onError(it)
                })

            } else {
                onError(task.exception)
            }
        }.addOnFailureListener {
            println(it.toString())
        }
    }



    override fun signUp( email: String,
                        password: String,
                        firebaseDatabaseRepo: FirebaseDatabaseRepository,
                        onSuccess: (FirebaseUser?) -> Unit,
                        onError: (Exception?) -> Unit) {

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val userData = hashMapOf<String, Any?>(
                        FirebaseKeys.FIRST_NAME_DB_KEY to "Rohit",
                        FirebaseKeys.LAST_NAME_DB_KEY to "Sharma",
                        FirebaseKeys.EMAIL_DB_KEY to email,
                        FirebaseKeys.USER_ID_DB_KEY to user?.uid,
                        FirebaseKeys.MATCH_ID_DB_KEY to emptyList<String>()
                    )
                    firebaseDatabaseRepo.createUser(userData, onSuccess = {
                        onSuccess(user)
                    }, onError = {
                        onError(it)
                    })

                } else {
                    onError(task.exception)
                }
            }
            .addOnFailureListener {
                Log.d("FirebaseAuthRepoImp", "login Failure: $it")
            }
            .addOnSuccessListener {
                Log.d("FirebaseAuthRepoImp", "login Success: $it")

            }
    }

//fun logIn(
//    email: String,
//    password: String,
//    firebaseDatabaseRepo: FirebaseDatabaseRepository,
//    onSuccess: (User?) -> Unit,
//    onError: (Exception?) -> Unit
//) {
//    auth.signInWithEmailAndPassword(email, password)
//        .addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                val user = auth.currentUser
//                firebaseDatabaseRepo.getUser(user?.uid, onSuccess = { userData ->
//                    onSuccess(userData)
//                }, onError = {
//                    onError(it)
//                })
//
//            } else {
//                onError(task.exception)
//            }
//        }.addOnFailureListener {
//            println(it.toString())
//        }
//}
}