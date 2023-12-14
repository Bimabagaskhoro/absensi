package com.febyputri.absensiapp.model.request

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class AttendanceRequest(

	@SerializedName("location")
	val location: String? = null,

	@SerializedName("userID")
	val userID: Int? = null,

	@SerializedName("status")
	val status: String? = null
) : Parcelable
