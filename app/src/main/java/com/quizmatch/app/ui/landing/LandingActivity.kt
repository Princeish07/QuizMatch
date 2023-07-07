package com.quizmatch.app.ui.landing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.quizmatch.app.R
import com.quizmatch.app.databinding.ActivityLandingBinding
import com.quizmatch.app.ui.dashboard.DashboardActivity
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

        showPrompt()
        loggedInSuccess()
        binding.login.setOnClickListener{
          viewModel.login(email = binding.etEmail.text.toString(), password =  binding.etPassword.text
              .toString())
        }

    }
    fun showPrompt(){
        viewModel.promptMessage.observe(this) {it -> it as Int
            this.showToast(it)
        }
    }
    fun loggedInSuccess(){
        viewModel.loginSuccess.observe(this){
            moveUserToDashboardActivity()
        }
    }

    fun moveUserToDashboardActivity(){
        startActivity(Intent(this, DashboardActivity::class.java))

    }
}