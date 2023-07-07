package com.quizmatch.app.Repository.FirebaseAuth

import com.google.firebase.auth.FirebaseUser
import com.quizmatch.app.Repository.FirebaseFirestoreDatabase.FirebaseDatabaseRepository
import com.quizmatch.app.data.model.firebase.User

/*
* todo Created By Mohit_Kaushik at 25/05/23.
*/
interface FirebaseAuthRepo {
    fun logIn(
        email: String,
        password: String,
        firebaseDatabaseRepo: FirebaseDatabaseRepository,
        onSuccess: (User?) -> Unit,
        onError: (Exception?) -> Unit
    )
    fun signUp(email: String,
              password: String,
              firebaseDatabaseRepo: FirebaseDatabaseRepository,
              onSuccess: (FirebaseUser?) -> Unit,
              onError: (Exception?) -> Unit)



}