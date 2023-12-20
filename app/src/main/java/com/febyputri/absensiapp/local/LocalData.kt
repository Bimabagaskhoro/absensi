package com.febyputri.absensiapp.local

import android.content.Context
import android.content.SharedPreferences
import com.febyputri.absensiapp.utils.Constant
import com.febyputri.absensiapp.utils.Constant.KEY_LAST_ACTION_TIME
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

object LocalData {
    fun saveData(context: Context, key: String, value: String) {
        val sharedPreferences =
            context.getSharedPreferences(Constant.MY_SHARED_PREF, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getData(context: Context, key: String, defaultValue: String): String? {
        val sharedPreferences =
            context.getSharedPreferences(Constant.MY_SHARED_PREF, Context.MODE_PRIVATE)
        return sharedPreferences.getString(key, defaultValue)
    }

    fun clearData(context: Context) {
        val sharedPreferences =
            context.getSharedPreferences(Constant.MY_SHARED_PREF, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    fun saveDataOnBoarding(context: Context, key: String, value: String) {
        val sharedPreferences =
            context.getSharedPreferences(Constant.MY_SHARED_PREF_ONBOARDING, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getDataOnBoarding(context: Context, key: String, defaultValue: String): String? {
        val sharedPreferences =
            context.getSharedPreferences(Constant.MY_SHARED_PREF_ONBOARDING, Context.MODE_PRIVATE)
        return sharedPreferences.getString(key, defaultValue)
    }

    fun clearDataOnBoarding(context: Context) {
        val sharedPreferences =
            context.getSharedPreferences(Constant.MY_SHARED_PREF_ONBOARDING, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    fun isActionAllowed(context: Context): Boolean {
        val lastActionTime = getLastActionTime(context)
        val currentTime = Calendar.getInstance().time
        val yesterday = Calendar.getInstance()
        yesterday.add(Calendar.DAY_OF_YEAR, -1)
        val yesterdayDate = yesterday.time

        return lastActionTime.before(yesterdayDate) || lastActionTime == yesterdayDate || lastActionTime.after(
            currentTime
        )
    }

    fun getLastActionTime(context: Context): Date {
        val sharedPreferences =
            context.getSharedPreferences(Constant.MY_SHARED_PREF, Context.MODE_PRIVATE)
        val defaultTime = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("1970-01-01")
        return Date(sharedPreferences.getLong(KEY_LAST_ACTION_TIME, defaultTime.time))
    }

    fun saveLastActionTime(context: Context) {
        val sharedPreferences =
            context.getSharedPreferences(Constant.MY_SHARED_PREF, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putLong(KEY_LAST_ACTION_TIME, Calendar.getInstance().time.time)
        editor.apply()
    }
}