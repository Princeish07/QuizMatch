package com.quizmatch.app.di

import com.quizmatch.app.Repository.FirebaseAuthRepo
import com.quizmatch.app.data.local.pref.PrefManager
import com.quizmatch.app.ui.landing.LandingActivityViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ViewModelModule {
    
//    @Provides
//    @Singleton
//    fun getViewModel(firebaseAuthRepo: FirebaseAuthRepo, prefManager: PrefManager): LandingActivityViewModel {
//        return LandingActivityViewModel(firebaseAuthRepo,prefManager)
//    }
}