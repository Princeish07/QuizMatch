package com.quizmatch.app.di

import android.content.Context
import com.policypal.app.data.remote.FirebaseDatabaseRepositoryImpl
import com.quizmatch.app.Repository.FirebaseAuth.FirebaseAuthRepo
import com.quizmatch.app.Repository.FirebaseAuth.FirebaseAuthRepoImp
import com.quizmatch.app.Repository.FirebaseFirestoreDatabase.FirebaseDatabaseRepository
import com.quizmatch.app.Repository.question.QuestionRepo
import com.quizmatch.app.Repository.question.QuestionRepoImp
import com.quizmatch.app.data.local.pref.PrefManager
import com.quizmatch.app.data.remote.APICallMethods
import com.quizmatch.app.data.remote.APIHandler
import com.quizmatch.app.ui.landing.LandingActivityViewModel
import com.quizmatch.app.utils.Constants

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun getApiHandler(): APICallMethods {
        return APIHandler(Constants.BASE_URL).handler
    }

    @Provides
    @Singleton
    fun getPrefManager(@ApplicationContext context: Context): PrefManager {
        return PrefManager().initPref(context.applicationContext)
    }
    @Provides
    @Singleton
    fun getFirebaseAuthRepo(apiCallMethods: APICallMethods): FirebaseAuthRepo {
        return FirebaseAuthRepoImp(apiCallMethods)
    }

    @Provides
    @Singleton
    fun getFirebaseFirestoreDatabase(): FirebaseDatabaseRepository {
        return FirebaseDatabaseRepositoryImpl()
    }
    @Provides
    @Singleton
    fun getQuestionRepo(): QuestionRepo {
        return QuestionRepoImp()
    }
}