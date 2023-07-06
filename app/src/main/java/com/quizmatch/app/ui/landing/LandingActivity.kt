package com.quizmatch.app.ui.landing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.quizmatch.app.R
import com.quizmatch.app.databinding.ActivityLandingBinding
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
        setContentView(binding.root)
        binding.login.setOnClickListener{
           var p =  viewModel.getList()
        }

    }
}