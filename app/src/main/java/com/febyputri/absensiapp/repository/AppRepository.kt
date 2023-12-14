package com.febyputri.absensiapp.repository

import com.febyputri.absensiapp.base.safeApiCall
import com.febyputri.absensiapp.mapper.toMap
import com.febyputri.absensiapp.model.AttendanceResponse
import com.febyputri.absensiapp.model.CheckAttendanceResponse
import com.febyputri.absensiapp.model.CheckUserResponse
import com.febyputri.absensiapp.model.LoginResponse
import com.febyputri.absensiapp.model.RegisterResponse
import com.febyputri.absensiapp.model.request.AttendanceRequest
import com.febyputri.absensiapp.model.request.LoginRequest
import com.febyputri.absensiapp.model.request.RegisterRequest
import com.febyputri.absensiapp.network.ApiClient

interface AppRepository {
    suspend fun login(request: LoginRequest): LoginResponse
    suspend fun register(request: RegisterRequest): RegisterResponse
    suspend fun attendance(request: AttendanceRequest): AttendanceResponse
    suspend fun getListAttendance(userId: Int): CheckAttendanceResponse
    suspend fun checkUser(): CheckUserResponse
}

class AppRepositoryImpl(
    private val api: ApiClient
) : AppRepository {
    override suspend fun login(request: LoginRequest): LoginResponse {
        return safeApiCall {
            api.login(request)
        }
    }

    override suspend fun register(request: RegisterRequest): RegisterResponse {
        return safeApiCall {
            api.register(request)
        }
    }

    override suspend fun attendance(request: AttendanceRequest): AttendanceResponse {
        return safeApiCall {
            api.attendance(request)
        }
    }

    override suspend fun getListAttendance(userId: Int): CheckAttendanceResponse {
        return safeApiCall {
            api.getListAttendance(userId).toMap()
        }
    }

    override suspend fun checkUser(): CheckUserResponse {
        return safeApiCall {
            api.checkUser()
        }
    }

}