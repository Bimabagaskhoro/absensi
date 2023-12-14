package com.febyputri.absensiapp.ui.adminarea

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
import com.febyputri.absensiapp.databinding.ActivityRegisterBinding
import com.febyputri.absensiapp.model.request.RegisterRequest
import com.febyputri.absensiapp.utils.showToastError
import com.febyputri.absensiapp.viewmodel.AppViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnRegister.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() = lifecycleScope.launch {
        val request = RegisterRequest(
            name = binding.edtNameRegister.text.toString().trim(),
            email = binding.edtEmailRegister.text.toString().trim(),
            password = binding.edtPasswordRegister.text.toString().trim()
        )
        viewModel.register(request)
        viewModel.register.launchAndCollectIn(this@RegisterActivity) { state ->
            state.stateUI(
                viewLoading = binding.progressBar
            )
            state
                .onLoading { }
                .onSuccess {
                    val intent = Intent(this@RegisterActivity, AdminActivity::class.java)
                    intent.flags
                    startActivity(intent)
                }
                .onError {
                    showToastError()
                }
        }
    }

}