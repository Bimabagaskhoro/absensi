package com.febyputri.absensiapp.model.request

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class LoginRequest(

	@SerializedName("password")
	val password: String? = null,

	@SerializedName("email")
	val email: String? = null
) : Parcelable
