package com.example.mvvm_map_demos.utils.SessionManager

import android.content.Context


object SessionManager {
    private val PREFS_NAME = "App Preference"
    private val FIRST_TIMEPREF_NAME = "First time App Preference"

    private val PARAM_USERID = "user_id"
    private val PARAM_EMAIL = "email"
    private val KEY_SHARED_ISLOGGEDIN = "logIn"
    private val PARAM_PROFILE="profile"



    //.........................
    fun setIsUserLoggedin(context: Context, isSelected: Boolean) {
        try {
            val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            val editor = preferences.edit()
            editor.putBoolean(KEY_SHARED_ISLOGGEDIN, isSelected)
            editor.commit()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getIsUserLoggedin(context: Context): Boolean {
        val preferences = context
            .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return preferences.getBoolean(KEY_SHARED_ISLOGGEDIN, false)
    }



    fun setUserId(context: Context, `val`: Int) {
        try {
            val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

            val editor = preferences.edit()
            editor.putInt(PARAM_USERID, `val`)
            editor.commit()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }




    fun getUserId(context: Context): Int {
        val preferences = context
            .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return preferences.getInt(PARAM_USERID, 0)
    }


    fun clearAppSession(context: Context) {
        try {
            val preferences =
                context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            val editor = preferences.edit()
            editor.clear()
            editor.apply()
        } catch (e: Exception) {
            // TODO: handle exception
            e.printStackTrace()
        }

    }


    fun setUserEmail(context: Context, strEmail: String) {
        try {
            val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

            val editor = preferences.edit()
            editor.putString(PARAM_EMAIL, strEmail)
            editor.commit()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun getUserEMail(context: Context): String? {
        val preferences = context
            .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return preferences.getString(PARAM_EMAIL, "")
    }

    fun setUserProfile(context: Context, strPhoneNO: String) {
        try {
            val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

            val editor = preferences.edit()
            editor.putString(PARAM_PROFILE, strPhoneNO)
            editor.commit()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun getUserProfile(context: Context): String? {
        val preferences = context
            .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return preferences.getString(PARAM_PROFILE, "")
    }



}
