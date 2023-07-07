package com.quizmatch.app.di

import com.quizmatch.app.Repository.FirebaseAuth.FirebaseAuthRepo
import com.quizmatch.app.Repository.FirebaseFirestoreDatabase.FirebaseDatabaseRepository
import com.quizmatch.app.Repository.question.QuestionRepo
import com.quizmatch.app.data.local.pref.PrefManager
import com.quizmatch.app.data.remote.APICallMethods
import com.quizmatch.app.ui.landing.LandingActivityViewModel
import com.quizmatch.app.ui.question.QuestionListViewModel
import com.quizmatch.app.ui.splash.SplashActivityViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ViewModelModule {

    @Provides
    @Singleton
    fun getLandingActivityViewModel(firebaseAuthRepo: FirebaseAuthRepo, prefManager: PrefManager,
                     firebaseFirestoreDatabase: FirebaseDatabaseRepository):
            LandingActivityViewModel {
        return LandingActivityViewModel(firebaseAuthRepo,prefManager,firebaseFirestoreDatabase)
    }

    @Provides
    @Singleton
    fun getSplashViewModel(prefManager: PrefManager):
            SplashActivityViewModel {
        return SplashActivityViewModel(prefManager)
    }

    @Provides
    @Singleton
    fun getQuestionListViewModel(apiCallMethods: APICallMethods, prefManager: PrefManager,
                                    firebaseFirestoreDatabase: FirebaseDatabaseRepository,questionRepo: QuestionRepo):
            QuestionListViewModel {
        return QuestionListViewModel(apiCallMethods,prefManager,firebaseFirestoreDatabase,
            questionRepo)
    }
}