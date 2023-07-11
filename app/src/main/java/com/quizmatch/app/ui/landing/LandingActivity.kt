package com.quizmatch.app.ui.landing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.quizmatch.app.R
import com.quizmatch.app.databinding.ActivityLandingBinding
import com.quizmatch.app.ui.dashboard.DashboardActivity
import com.quizmatch.app.utils.routeToDashboardActivityScreen
import com.quizmatch.app.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LandingActivity : AppCompatActivity() {
    lateinit var binding: ActivityLandingBinding

    @Inject
    lateinit var viewModel: LandingActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandingBinding.inflate(layoutInflater)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_landing);
        binding.viewModel = viewModel
        showPrompt()
        showLoader()
        loggedInSuccess()
        binding.login.setOnClickListener{
          viewModel.login(email = binding.etEmail.text.toString(), password =  binding.etPassword.text
              .toString())
        }

    }

    //Method to show prompt on screen
    fun showPrompt(){
        viewModel.promptMessage.observe(this) {it -> it as Int
            this.showToast(it)
        }
    }

    //Method to show loader
    fun showLoader(){
        viewModel.loader.observe(this) {it ->
            if(it){
                binding.rlProgress.visibility = View.VISIBLE
            }else{
                binding.rlProgress.visibility = View.GONE

            }
        }
    }

    //Method to handle login success
    fun loggedInSuccess(){
        viewModel.loginSuccess.observe(this){
            routeToDashboardActivityScreen()
            finish()
        }
    }

}