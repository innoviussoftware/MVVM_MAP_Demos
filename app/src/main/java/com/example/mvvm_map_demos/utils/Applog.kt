package com.example.mvvm_map_demos.utils

import android.util.Log

object Applog {
    fun E(msg: String) {

        Log.e("Maei App", msg)

    }

    fun e(tag: String, msg: String) {

        Log.e(tag, msg)

    }

    fun d(tag: String, msg: String) {

        Log.d(tag, msg)

    }

    fun v(tag: String, msg: String) {

        Log.v(tag, msg)

    }

    fun i(tag: String, msg: String) {

        Log.i(tag, msg)

    }

    fun Res(apiName: String, msg: String) {
        var apiName = "----===>"+ apiName;
        Log.e(apiName, msg)
    }

}