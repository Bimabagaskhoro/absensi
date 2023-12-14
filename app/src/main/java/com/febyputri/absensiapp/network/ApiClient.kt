package com.febyputri.absensiapp.network

import com.febyputri.absensiapp.model.AttendanceResponse
import com.febyputri.absensiapp.model.CheckAttendanceResponse
import com.febyputri.absensiapp.model.CheckUserResponse
import com.febyputri.absensiapp.model.LoginResponse
import com.febyputri.absensiapp.model.RegisterResponse
import com.febyputri.absensiapp.model.request.AttendanceRequest
import com.febyputri.absensiapp.model.request.LoginRequest
import com.febyputri.absensiapp.model.request.RegisterRequest
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiClient {
    @POST("/api/absensi/v1/login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse

    @POST("api/absensi/v1/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): RegisterResponse

    @POST("/api/absensi/v1/absen")
    suspend fun attendance(
        @Body request: AttendanceRequest
    ): AttendanceResponse

    @GET("/api/absensi/v1/kehadiran")
    suspend fun getListAttendance(
        @Query("userID") userId: Int
    ): CheckAttendanceResponse

    @GET("/api/absensi/v1/users")
    suspend fun checkUser(): CheckUserResponse


}