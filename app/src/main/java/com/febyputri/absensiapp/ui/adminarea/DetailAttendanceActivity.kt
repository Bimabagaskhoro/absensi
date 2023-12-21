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
import com.febyputri.absensiapp.utils.Constant
import com.febyputri.absensiapp.utils.showToastError
import com.febyputri.absensiapp.viewmodel.AppViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Locale.filter

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
                .onSuccess { response ->
                    adapter.submitList(filterDataByUserId(response.data, dataParse))
//                    val attendanceData = response.data?.filter {
//                        it?.status?.split(",")?.get(0)?.trim().equals(Constant.ATTENDANCE, true)
//                    }?.size
//                    val sickData = response.data?.filter {
//                        it?.status?.split(",")?.get(0)?.trim().equals(Constant.SICK_LEFT, true)
//                    }?.size
//                    val paidLeftData = response.data?.filter {
//                        it?.status?.split(",")?.get(0)?.trim().equals(Constant.PAID_LEFT, true)
//                    }?.size
//                    val stringOk = "$attendanceData ${Constant.ATTENDANCE}, $sickData ${Constant.SICK_LEFT}, $paidLeftData ${Constant.PAID_LEFT}"
//                    binding.tvCount.text = stringOk
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