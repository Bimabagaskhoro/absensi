package com.febyputri.absensiapp.ui.onboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import com.febyputri.absensiapp.R
import com.febyputri.absensiapp.databinding.ActivityMainBinding
import com.febyputri.absensiapp.databinding.ActivityOnBoardingBinding
import com.febyputri.absensiapp.local.LocalData
import com.febyputri.absensiapp.ui.login.LoginActivity
import com.febyputri.absensiapp.utils.Constant

class OnBoardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnBoardingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        stepOne()
        setButton()
    }

    private fun setButton() = with(binding) {
        btnNext.setOnClickListener {
            stepTwo()
        }
        btnBack2.setOnClickListener {
            stepOne()
        }
        btnNext2.setOnClickListener {
            stepLast()
        }
        btnLast.setOnClickListener {
            LocalData.saveDataOnBoarding(this@OnBoardingActivity, Constant.ONBOARDING,Constant.ONBOARDING)
            startActivity(Intent(this@OnBoardingActivity, LoginActivity::class.java)).apply {
                intent.flags =  Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            finish()
        }
        btnSkip.setOnClickListener {
            LocalData.saveDataOnBoarding(this@OnBoardingActivity, Constant.ONBOARDING,Constant.ONBOARDING)
            startActivity(Intent(this@OnBoardingActivity, LoginActivity::class.java)).apply {
                intent.flags =  Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            finish()
        }
    }

    private fun stepOne() = with(binding) {
        llOnboard1.isVisible = true
        llOnboard2.isVisible = false
        llOnboard3.isVisible = false
    }

    private fun stepTwo() = with(binding) {
        llOnboard1.isVisible = false
        llOnboard2.isVisible = true
        llOnboard3.isVisible = false
    }

    private fun stepLast() = with(binding) {
        llOnboard1.isVisible = false
        llOnboard2.isVisible = false
        llOnboard3.isVisible = true
        btnSkip.isVisible = false
    }

}