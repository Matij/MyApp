package com.martafode.lib.rest.helper

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.martafode.lib.di.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class NetworkConnectivity @Inject constructor(
    private val application: Application
) {

    @SuppressLint("ServiceCast")
    fun hasNetwork(): Boolean? {
        val connectivityManager =
            application.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)?.let { networkCapabilities ->
            return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                    || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
        }

        return false
    }
}
