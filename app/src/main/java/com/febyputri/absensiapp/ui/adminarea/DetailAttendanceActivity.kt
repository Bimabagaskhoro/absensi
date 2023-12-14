package com.febyputri.absensiapp.ui.adminarea

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.febyputri.absensiapp.base.launchAndCollectIn
import com.febyputri.absensiapp.base.onError
import com.febyputri.absensiapp.base.onLoading
import com.febyputri.absensiapp.base.onSuccess
import com.febyputri.absensiapp.base.stateUI
import com.febyputri.absensiapp.databinding.ActivityDetailAttendanceBinding
import com.febyputri.absensiapp.model.CheckAttendanceResponse
import com.febyputri.absensiapp.ui.adminarea.adapter.ListAttendence
import com.febyputri.absensiapp.utils.showToastError
import com.febyputri.absensiapp.viewmodel.AppViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailAttendanceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailAttendanceBinding
    private val viewModel: AppViewModel by viewModels()
    private var dataParse = 0
    private val adapter = ListAttendence()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailAttendanceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvUser.adapter = adapter
        dataParse = intent.extras?.getInt(DATA_PARSE) ?: 0
        observeData(dataParse)
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun observeData(dataParse: Int) = lifecycleScope.launch {
        viewModel.getListAttendance(dataParse)
        viewModel.getListAttendance.launchAndCollectIn(this@DetailAttendanceActivity) { state ->
            state.stateUI(
                viewLoading = binding.progressBar
            )
            state
                .onLoading {}
                .onSuccess {
                    adapter.submitList(filterDataByUserId(it.data, dataParse))
                }
                .onError {
                    showToastError()
                }
        }
    }

    private fun filterDataByUserId(
        data: List<CheckAttendanceResponse.DataItem?>?,
        userId: Int
    ): List<CheckAttendanceResponse.DataItem?>? {
        return data?.filter { it?.userID == userId }
    }

    companion object {
        const val DATA_PARSE = ""
    }
}