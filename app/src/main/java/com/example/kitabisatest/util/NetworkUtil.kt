package com.example.kitabisatest.util

import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by irwancannady on 1/29/18.
 */
object NetworkUtil {
    fun hasNetwork(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}