package com.febyputri.absensiapp.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

fun Context.showToastError() {
    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
}

fun Context.showToastSuccess() {
    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
}

fun Context.showToastExit() {
    Toast.makeText(this, "Klik satu kali lagi untuk keluar", Toast.LENGTH_SHORT).show()
}

fun Context.isLocationPermissionGranted(): Boolean {
    val lowerThanM = Build.VERSION.SDK_INT < Build.VERSION_CODES.M
    val fine = checkPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)
    val coarse = checkPermissionGranted(Manifest.permission.ACCESS_COARSE_LOCATION)

    return lowerThanM || (fine && coarse)
}

fun Context.checkPermissionGranted(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(
        this,
        permission
    ) == PackageManager.PERMISSION_GRANTED
}

fun Activity.requestLocationPermission(req: Int) {
    if (!isLocationPermissionGranted()) {
        val relationalFine = ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        val relationalCoarse = ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        if (relationalFine || relationalCoarse) {
            abandonThisWarning(relationalFine)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                req
            )
        }
    }
}

fun abandonThisWarning(any: Any? = null) {
    any.let {}
}

fun getCityName(context: Context, latitude: Double, longitude: Double): String {
    val geocoder = Geocoder(context, Locale.getDefault())
    var cityName = ""

    try {
        val addresses: List<Address> =
            geocoder.getFromLocation(latitude, longitude, 1) as List<Address>
        if (addresses.isNotEmpty()) {
            cityName = addresses[0].locality
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }

    return cityName
}

fun formatDate(inputDateString: String?): String? {
    return try {
        val dateFormatInput = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val date = inputDateString?.let { dateFormatInput.parse(it) }
        val dateFormatOutput = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        dateFormatOutput.format(date!!)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun formatTime(inputDateString: String?): String? {
    return try {
        val dateFormatInput = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val date = inputDateString?.let { dateFormatInput.parse(it) }
        val timeFormatOutput = SimpleDateFormat("HH:mm", Locale.getDefault())
        timeFormatOutput.format(date!!)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun getCurrentTimeInIndonesia(): String {
    val timeZone = TimeZone.getTimeZone("Asia/Jakarta")
    val calendar = Calendar.getInstance(timeZone)
    val dateFormat = "HH:mm"
    val simpleDateFormat = SimpleDateFormat(dateFormat, Locale.getDefault())
    return simpleDateFormat.format(calendar.time)
}

fun String.mounth3String() : String {
    val inputFormat = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))
    val date = inputFormat.parse(this)
    return if (date != null) {
        val outputFormat = SimpleDateFormat("MMM", Locale("id", "ID"))
        outputFormat.format(date)
    } else {
        "Invalid Date"
    }
}
fun String.dayString(): String {
    val inputFormat = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))
    val date = inputFormat.parse(this)
    return if (date != null) {
        val outputFormat = SimpleDateFormat("dd", Locale("id", "ID"))
        outputFormat.format(date)
    } else {
        "Invalid Date"
    }
}

fun String.dayOfWeekString(): String {
    val inputFormat = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))
    val date = inputFormat.parse(this)
    return if (date != null) {
        val outputFormat = SimpleDateFormat("EEEE", Locale("id", "ID"))
        outputFormat.format(date)
    } else {
        "Invalid Date"
    }
}
