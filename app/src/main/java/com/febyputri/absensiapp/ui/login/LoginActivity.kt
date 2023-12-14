package com.febyputri.absensiapp.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.febyputri.absensiapp.base.launchAndCollectIn
import com.febyputri.absensiapp.base.onError
import com.febyputri.absensiapp.base.onLoading
import com.febyputri.absensiapp.base.onSuccess
import com.febyputri.absensiapp.base.stateUI
import com.febyputri.absensiapp.databinding.ActivityLoginBinding
import com.febyputri.absensiapp.local.LocalData
import com.febyputri.absensiapp.model.request.LoginRequest
import com.febyputri.absensiapp.ui.adminarea.AdminActivity
import com.febyputri.absensiapp.ui.main.MainActivity
import com.febyputri.absensiapp.utils.Constant
import com.febyputri.absensiapp.utils.showToastError
import com.febyputri.absensiapp.viewmodel.AppViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() = with(binding) {
        btnLogin.setOnClickListener {
            observeData()
        }
    }


    private fun observeData() = lifecycleScope.launch {
        val checkEmail = binding.edtEmailLogin.text.toString().equals("adminabsen", true)
        val checkPassword = binding.edtPasswordLogin.text.toString().equals("admin123", true)
        if (checkEmail && checkPassword) {
            LocalData.saveData(this@LoginActivity, Constant.ADMIN, "admin")
            val intent = Intent(this@LoginActivity, AdminActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        } else {
            val request = LoginRequest(
                email = binding.edtEmailLogin.text.toString().trim(),
                password = binding.edtPasswordLogin.text.toString().trim()
            )
            viewModel.login(request)
            viewModel.login.launchAndCollectIn(this@LoginActivity) { state ->
                state.stateUI(
                    viewLoading = binding.progressBar
                )
                state
                    .onLoading {
                    }
                    .onSuccess {
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)
                        LocalData.saveData(
                            this@LoginActivity,
                            Constant.USERNAME,
                            it.data?.name.toString()
                        )
                        LocalData.saveData(
                            this@LoginActivity,
                            Constant.USER_EMAIL,
                            it.data?.email.toString()
                        )
                        LocalData.saveData(
                            this@LoginActivity,
                            Constant.USERID,
                            it.data?.userID.toString()
                        )
                    }
                    .onError {
                        showToastError()
                    }
            }
        }
    }
}