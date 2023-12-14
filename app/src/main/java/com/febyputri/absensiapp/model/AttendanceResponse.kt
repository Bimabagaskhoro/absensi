package com.febyputri.absensiapp.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class AttendanceResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("statusCode")
	val statusCode: String? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable {

	@Parcelize
	data class Data(

		@field:SerializedName("createdAt")
		val createdAt: String? = null,

		@field:SerializedName("name")
		val name: String? = null,

		@field:SerializedName("location")
		val location: String? = null,

		@field:SerializedName("id")
		val id: Int? = null,

		@field:SerializedName("userID")
		val userID: Int? = null,

		@field:SerializedName("email")
		val email: String? = null,

		@field:SerializedName("status")
		val status: String? = null,

		@field:SerializedName("updatedAt")
		val updatedAt: String? = null
	) : Parcelable

}