package com.quizmatch.app.ui.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.quizmatch.app.R
import com.quizmatch.app.ui.dashboard.DashboardActivity
import com.quizmatch.app.ui.landing.LandingActivity
import com.quizmatch.app.ui.landing.LandingActivityViewModel
import com.quizmatch.app.utils.routeToDashboardActivityScreen
import com.quizmatch.app.utils.routeToLandingActivityScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: SplashActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler(Looper.getMainLooper()).postDelayed({

            if(viewModel.authenticationHandle()){
                routeToDashboardActivityScreen()
            }else{
                routeToLandingActivityScreen()
            }
            finish()
        }, 2500)


    }

}