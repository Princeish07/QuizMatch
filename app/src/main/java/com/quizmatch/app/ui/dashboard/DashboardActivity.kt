package com.quizmatch.app.ui.dashboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.quizmatch.app.R
import com.quizmatch.app.databinding.ActivityDashboardBinding
import com.quizmatch.app.utils.navigateToQuestionListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {
    lateinit var binding: ActivityDashboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        navigateToQuestionListScreen()
    }
}