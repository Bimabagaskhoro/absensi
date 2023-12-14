package com.febyputri.absensiapp.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class LoginResponse(
    @SerializedName("data")
    val data: Data? = null,

    @SerializedName("statusCode")
    val statusCode: String? = null,

    @SerializedName("status")
    val status: String? = null
) : Parcelable {
    @Keep
    @Parcelize
    data class Data(

        @SerializedName("name")
        val name: String? = null,

        @SerializedName("userID")
        val userID: Int? = null,

        @SerializedName("email")
        val email: String? = null
    ) : Parcelable
}
