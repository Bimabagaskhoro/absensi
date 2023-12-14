package com.febyputri.absensiapp.ui.adminarea.adapter

import android.text.TextUtils.split
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.febyputri.absensiapp.R
import com.febyputri.absensiapp.base.BaseListAdapter
import com.febyputri.absensiapp.databinding.ItemAttendenceBinding
import com.febyputri.absensiapp.model.CheckAttendanceResponse
import com.febyputri.absensiapp.utils.Constant
import com.febyputri.absensiapp.utils.dayOfWeekString
import com.febyputri.absensiapp.utils.dayString
import com.febyputri.absensiapp.utils.mounth3String

class ListAttendence : BaseListAdapter<CheckAttendanceResponse.DataItem, ItemAttendenceBinding>
    (ItemAttendenceBinding::inflate) {
    override fun onItemBind(): (CheckAttendanceResponse.DataItem, ItemAttendenceBinding, View, Int) -> Unit =
        { item, binding, itemview, _ ->
            binding.tvDate.text = item.formatDate?.mounth3String()
            binding.tvDate2.text = item.formatDate?.dayString()
            binding.tvDay.text = item.formatDate?.dayOfWeekString()
            binding.tvTime.text = item.formatTime
            binding.tvStatus.text = item.status?.split(",")?.get(0) ?: ""
            binding.tvStatusDesc.text = item.status?.split(",")?.get(1) ?: ""
            binding.tvStatusDesc.isVisible = item.status != Constant.ATTENDANCE

            when {
                item.status?.split(",")?.get(0).equals(Constant.ATTENDANCE, true) -> {
                    binding.view.background = ContextCompat.getDrawable(itemview.context, R.color.grey)
                    binding.tvStatus.setTextColor(ContextCompat.getColor(itemview.context, R.color.black))
                }

                item.status?.split(",")?.get(0).equals(Constant.SICK_LEFT, true) -> {
                    binding.view.background = ContextCompat.getDrawable(itemview.context, R.color.red)
                    binding.tvStatus.setTextColor(ContextCompat.getColor(itemview.context, R.color.red))
                }

                item.status?.split(",")?.get(0).equals(Constant.PAID_LEFT, true) -> {
                    binding.view.background = ContextCompat.getDrawable(itemview.context, R.color.progress_color)
                    binding.tvStatus.setTextColor(ContextCompat.getColor(itemview.context, R.color.progress_color))
                }
            }
        }
}