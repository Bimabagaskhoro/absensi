package com.febyputri.absensiapp.model.request

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class RegisterRequest(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("email")
	val email: String? = null
) : Parcelable
