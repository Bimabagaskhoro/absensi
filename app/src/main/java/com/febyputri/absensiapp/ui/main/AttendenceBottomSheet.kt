package com.febyputri.absensiapp.ui.main

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import com.febyputri.absensiapp.base.launchAndCollectIn
import com.febyputri.absensiapp.base.onError
import com.febyputri.absensiapp.base.onLoading
import com.febyputri.absensiapp.base.onSuccess
import com.febyputri.absensiapp.databinding.BottomSheetAttendenceBinding
import com.febyputri.absensiapp.local.LocalData
import com.febyputri.absensiapp.model.request.AttendanceRequest
import com.febyputri.absensiapp.utils.Constant
import com.febyputri.absensiapp.utils.showToastError
import com.febyputri.absensiapp.utils.showToastSuccess
import com.febyputri.absensiapp.viewmodel.AppViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AttendenceBottomSheet : BottomSheetDialogFragment() {
    private var _binding: BottomSheetAttendenceBinding? = null
    private val binding get() = _binding!!
    private var tittle = ""
    private var status = ""
    private var onDismissListener: (() -> Unit)? = null
    private val viewModel: AppViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetAttendenceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            tittle = it.getString(ARGS) ?: ""
            status = it.getString(ARGS_STATUS) ?: ""
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

    private fun initView() = with(binding) {
        tvTittle.text = tittle
        etFreeText.doOnTextChanged { text, _, _, _ ->
            btnAttendance.isEnabled = !(text?.isEmpty() == true || text?.isBlank() == true)
            val requestString = "$status, ${etFreeText.text.toString().trim()}"
            btnAttendance.setOnClickListener {
                observeData(requestData(requestString))
            }
        }
        tvBtn.text = "Submit"
    }

    private fun requestData(status: String): AttendanceRequest {
        return AttendanceRequest(
            location = "cityName",
            userID = LocalData.getData(requireContext(), Constant.USERID, "")?.toInt(),
            status = status
        )
    }


    private fun observeData(requestData: AttendanceRequest) {
        viewModel.attendance(requestData)
        viewModel.attendance.launchAndCollectIn(viewLifecycleOwner) { state ->
            state
                .onLoading {}
                .onSuccess {
                    requireContext().showToastSuccess()
                    dialog?.dismiss()
                    LocalData.saveLastActionTime(requireContext())
                }
                .onError {
                    requireContext().showToastError()
                }
        }
    }

    companion object {
        private const val ARGS = "dataTittle"
        private const val ARGS_STATUS = "dataStatus"
        fun newInstance(data: String, status: String): AttendenceBottomSheet =
            AttendenceBottomSheet().apply {
                arguments = Bundle().apply {
                    putString(ARGS, data)
                    putString(ARGS_STATUS, status)
                }
            }
    }

    fun setOnDismissListener(listener: () -> Unit) {
        onDismissListener = listener
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDismissListener?.invoke()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}