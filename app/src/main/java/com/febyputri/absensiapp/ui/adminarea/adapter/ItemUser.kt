package com.febyputri.absensiapp.ui.adminarea.adapter

import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import com.febyputri.absensiapp.base.BaseListAdapter
import com.febyputri.absensiapp.databinding.ItemUserBinding
import com.febyputri.absensiapp.model.CheckUserResponse

class ItemUser(
    private val onSubscribeClick: (CheckUserResponse.DataItem) -> Unit
) : BaseListAdapter<CheckUserResponse.DataItem, ItemUserBinding>
    (ItemUserBinding::inflate) {
    override fun onItemBind(): (CheckUserResponse.DataItem, ItemUserBinding, View, Int) -> Unit =
        { item, binding, _, _ ->
            binding.tvName.text = item.name
            binding.tvEmail.text = item.email
            binding.tvPasswd.text = item.password
            binding.checkBoxShowPassword.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    binding.tvPasswd.transformationMethod = HideReturnsTransformationMethod.getInstance()
                } else {
                    binding.tvPasswd.transformationMethod = PasswordTransformationMethod.getInstance()
                }
            }

            binding.root.setOnClickListener {
                onSubscribeClick(item)
            }
        }
}