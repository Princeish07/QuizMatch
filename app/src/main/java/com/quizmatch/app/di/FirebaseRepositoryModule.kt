package com.quizmatch.app.di
import com.quizmatch.app.Repository.FirebaseAuthRepo
import com.quizmatch.app.Repository.FirebaseAuthRepoImp
import com.quizmatch.app.ui.landing.LandingActivityViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//@Module
//@InstallIn(SingletonComponent::class)
//abstract class FirebaseRepositoryModule {
////    @Binds
////    @Singleton
////    abstract fun firebaseAuthRepo(
////        firebaseAuthRepoImp: FirebaseAuthRepoImp
////    ):FirebaseAuthRepo
//
//}