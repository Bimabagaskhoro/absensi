package com.febyputri.absensiapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.febyputri.absensiapp.base.DataState
import com.febyputri.absensiapp.base.asMutableStateFlow
import com.febyputri.absensiapp.model.AttendanceResponse
import com.febyputri.absensiapp.model.CheckAttendanceResponse
import com.febyputri.absensiapp.model.CheckUserResponse
import com.febyputri.absensiapp.model.LoginResponse
import com.febyputri.absensiapp.model.RegisterResponse
import com.febyputri.absensiapp.model.request.AttendanceRequest
import com.febyputri.absensiapp.model.request.LoginRequest
import com.febyputri.absensiapp.model.request.RegisterRequest
import com.febyputri.absensiapp.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val repo: AppRepository
) : ViewModel() {
    private val _login = MutableStateFlow<DataState<LoginResponse>>(DataState.Empty)
    val login = _login.asStateFlow()

    private val _attendance = MutableStateFlow<DataState<AttendanceResponse>>(DataState.Loading)
    val attendance = _attendance.asStateFlow()

    private val _register = MutableStateFlow<DataState<RegisterResponse>>(DataState.Empty)
    val register = _register.asStateFlow()

    private val _getListAttendance =
        MutableStateFlow<DataState<CheckAttendanceResponse>>(DataState.Empty)
    val getListAttendance = _getListAttendance.asStateFlow()

    private val _checkUser = MutableStateFlow<DataState<CheckUserResponse>>(DataState.Empty)
    val checkUser = _checkUser.asStateFlow()

    fun login(request: LoginRequest) = viewModelScope.launch {
        _login.asMutableStateFlow {
            repo.login(request)
        }
    }

    fun attendance(request: AttendanceRequest) = viewModelScope.launch {
        _attendance.asMutableStateFlow {
            repo.attendance(request)
        }
    }

    fun register(request: RegisterRequest) = viewModelScope.launch {
        _register.asMutableStateFlow {
            repo.register(request)
        }
    }

    fun getListAttendance(userId: Int) = viewModelScope.launch {
        _getListAttendance.asMutableStateFlow {
            repo.getListAttendance(userId)
        }
    }

    fun checkUser() = viewModelScope.launch {
        _checkUser.asMutableStateFlow {
            repo.checkUser()
        }
    }

}