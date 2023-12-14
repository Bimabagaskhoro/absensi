package com.febyputri.absensiapp.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.febyputri.absensiapp.base.launchAndCollectIn
import com.febyputri.absensiapp.base.onError
import com.febyputri.absensiapp.base.onLoading
import com.febyputri.absensiapp.base.onSuccess
import com.febyputri.absensiapp.databinding.ActivityMainBinding
import com.febyputri.absensiapp.local.LocalData
import com.febyputri.absensiapp.model.request.AttendanceRequest
import com.febyputri.absensiapp.ui.SplashScreenActivity
import com.febyputri.absensiapp.utils.Constant
import com.febyputri.absensiapp.utils.getCurrentTimeInIndonesia
import com.febyputri.absensiapp.utils.requestLocationPermission
import com.febyputri.absensiapp.utils.showToastError
import com.febyputri.absensiapp.utils.showToastSuccess
import com.febyputri.absensiapp.viewmodel.AppViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: AppViewModel by viewModels()
    private var isDialogShown = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestLocationPermission(Constant.REQ_PERMISSION_LOCATION)
        initView()
        initListener()
    }

    private fun initView() = with(binding) {
        val name = "Hi ${LocalData.getData(this@MainActivity, Constant.USERNAME, "")}"
        tvNameUser.text = name
        tvTools.text = Constant.TV_TOOLS
        tvIzin.text = Constant.PAID_LEFT
        tvSick.text = Constant.SICK_LEFT
        tvAttendance.text = Constant.ATTENDANCE
        tvJam.text = getCurrentTimeInIndonesia()
        textView2.text = Constant.TV_TOOLS_2
        tvLogout.text = Constant.LOGOUT
        tvLogout.setOnClickListener {
            LocalData.clearData(this@MainActivity)
            val intent = Intent(this@MainActivity, SplashScreenActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initListener() = with(binding) {
        if (LocalData.isActionAllowed(this@MainActivity)) {
            btnAttendance.setOnClickListener {
                observeData(requestData("${Constant.ATTENDANCE},"))
            }
            tvSick.setOnClickListener {
                if (!isDialogShown) {
                    val dialog = AttendenceBottomSheet.newInstance(Constant.KEY_TITTLE_SICK, Constant.SICK_LEFT)
                    dialog.show(supportFragmentManager, Constant.BS_ATTENDANCE)
                    isDialogShown = true
                    dialog.setOnDismissListener {
                        isDialogShown = false
                    }
                }
            }
            tvIzin.setOnClickListener {
                if (!isDialogShown) {
                    val dialog = AttendenceBottomSheet.newInstance(Constant.KEY_TITTLE_PAID_LEFT, Constant.PAID_LEFT)
                    dialog.show(supportFragmentManager, Constant.BS_ATTENDANCE)
                    isDialogShown = true
                    dialog.setOnDismissListener {
                        isDialogShown = false
                    }
                }
            }
        }
    }

    private fun requestData(status: String): AttendanceRequest {
        return AttendanceRequest(
            location = "cityName",
            userID = LocalData.getData(this, Constant.USERID, "")?.toInt(),
            status = status
        )
    }

    private fun observeData(requestData: AttendanceRequest) {
        viewModel.attendance(requestData)
        viewModel.attendance.launchAndCollectIn(this@MainActivity) { state ->
            state
                .onLoading {}
                .onSuccess {
                    showToastSuccess()
                    LocalData.saveLastActionTime(this@MainActivity)
                }
                .onError {
                    showToastError()
                }
        }
    }
}