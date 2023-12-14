package com.febyputri.absensiapp.ui.adminarea

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.febyputri.absensiapp.base.launchAndCollectIn
import com.febyputri.absensiapp.base.onError
import com.febyputri.absensiapp.base.onLoading
import com.febyputri.absensiapp.base.onSuccess
import com.febyputri.absensiapp.base.stateUI
import com.febyputri.absensiapp.databinding.ActivityAdminBinding
import com.febyputri.absensiapp.local.LocalData
import com.febyputri.absensiapp.ui.SplashScreenActivity
import com.febyputri.absensiapp.ui.adminarea.adapter.ItemUser
import com.febyputri.absensiapp.utils.showToastError
import com.febyputri.absensiapp.utils.showToastExit
import com.febyputri.absensiapp.viewmodel.AppViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminBinding
    private val viewModel: AppViewModel by viewModels()
    private var backPressedTime: Long = 0
    private val doubleBackPressInterval = 2000

    private val adapterUser by lazy {
        ItemUser { data ->
            val intent = Intent(this, DetailAttendanceActivity::class.java)
            intent.putExtra(DetailAttendanceActivity.DATA_PARSE, data.id)
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeData()
        binding.rvUser.adapter = adapterUser
        binding.btRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        binding.btnLogout.setOnClickListener {
            LocalData.clearData(this)
            val intent = Intent(this, SplashScreenActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
    }

    private fun observeData() = lifecycleScope.launch {
        viewModel.checkUser()
        viewModel.checkUser.launchAndCollectIn(this@AdminActivity) { state ->
            state.stateUI(
                viewSuccess = listOf(
                    binding.rvUser,
                    binding.btRegister,
                    binding.rlToolbar,
                ),
                viewLoading = binding.progressBar
            )
            state
                .onLoading { }
                .onSuccess {
                    adapterUser.submitList(it.data)
                }
                .onError {
                    showToastError()
                }
        }
    }

    override fun onBackPressed() {
        if (backPressedTime + doubleBackPressInterval > System.currentTimeMillis()) {
            super.onBackPressed()
        } else {
            showToastExit()
        }
        backPressedTime = System.currentTimeMillis()
    }
}