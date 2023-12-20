package com.febyputri.absensiapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.febyputri.absensiapp.R
import com.febyputri.absensiapp.local.LocalData
import com.febyputri.absensiapp.ui.adminarea.AdminActivity
import com.febyputri.absensiapp.ui.login.LoginActivity
import com.febyputri.absensiapp.ui.main.MainActivity
import com.febyputri.absensiapp.ui.onboard.OnBoardingActivity
import com.febyputri.absensiapp.utils.Constant
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        lifecycleScope.launch {
            delay(5000)
            checkOnBoarding()
        }
    }

    private fun checkOnBoarding() {
        val onBoarding: String? = LocalData.getDataOnBoarding(this, Constant.ONBOARDING, "")
        if (onBoarding == "") {
            val intent = Intent(this@SplashScreenActivity, OnBoardingActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        } else {
            checkLogin()
        }
    }

    private fun checkLogin() {
        val userId: String? = LocalData.getData(this, Constant.USERID, "")
        val userName: String? = LocalData.getData(this, Constant.USERNAME, "")
        val userEmail: String? = LocalData.getData(this, Constant.USER_EMAIL, "")
        val admin: String? = LocalData.getData(this, Constant.ADMIN, "")
        if (userId != "" || userName != "" || userEmail != "") {
            val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        } else if (admin != "") {
            val intent = Intent(this@SplashScreenActivity, AdminActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        } else {
            val intent = Intent(this@SplashScreenActivity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }
}