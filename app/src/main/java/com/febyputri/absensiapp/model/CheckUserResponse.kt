package com.febyputri.absensiapp.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class CheckUserResponse(

	@SerializedName("data")
	val data: List<DataItem?>? = null,

	@SerializedName("statusCode")
	val statusCode: String? = null,

	@SerializedName("status")
	val status: String? = null
) : Parcelable {

	@Parcelize
	data class DataItem(

		@SerializedName("createdAt")
		val createdAt: String? = null,

		@SerializedName("password")
		val password: String? = null,

		@SerializedName("name")
		val name: String? = null,

		@SerializedName("id")
		val id: Int? = null,

		@SerializedName("email")
		val email: String? = null,

		@SerializedName("updatedAt")
		val updatedAt: String? = null
	) : Parcelable

}
