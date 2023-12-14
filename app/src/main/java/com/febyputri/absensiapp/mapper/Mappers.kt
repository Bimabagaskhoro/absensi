package com.febyputri.absensiapp.mapper

import com.febyputri.absensiapp.model.CheckAttendanceResponse
import com.febyputri.absensiapp.utils.formatDate
import com.febyputri.absensiapp.utils.formatTime

fun CheckAttendanceResponse.DataItem.toMap(): CheckAttendanceResponse.DataItem {
    return CheckAttendanceResponse.DataItem(
        createdAt = createdAt,
        name = name,
        location = location,
        id = id,
        userID = userID,
        email = email,
        status = status,
        updatedAt = updatedAt,
        formatDate = formatDate(createdAt),
        formatTime = formatTime(createdAt),
    )
}

fun CheckAttendanceResponse.toMap(): CheckAttendanceResponse {
    return CheckAttendanceResponse(
        data = data?.map { it?.toMap() },
        statusCode = statusCode,
        status = status
    )
}