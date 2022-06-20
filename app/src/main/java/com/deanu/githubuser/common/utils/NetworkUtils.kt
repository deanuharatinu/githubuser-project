package com.deanu.githubuser.common.utils

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.provider.Settings
import androidx.annotation.IntRange
import androidx.appcompat.app.AlertDialog
import com.deanu.githubuser.R

class NetworkUtils {

    /**
     *  Returns connection type. 0: none; 1: mobile data; 2: wifi; 3: vpn
     */
    @IntRange(from = 0, to = 3)
    fun getConnectionType(context: Context): Int {
        var result = 0
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cm?.run {
                cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                    when {
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                            result = 2
                        }
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                            result = 1
                        }
                        hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> {
                            result = 3
                        }
                    }
                }
            }
        } else {
            cm?.run {
                cm.activeNetworkInfo?.run {
                    when (type) {
                        ConnectivityManager.TYPE_WIFI -> {
                            result = 2
                        }
                        ConnectivityManager.TYPE_MOBILE -> {
                            result = 1
                        }
                        ConnectivityManager.TYPE_VPN -> {
                            result = 3
                        }
                    }
                }
            }
        }
        return result
    }

    fun showNetworkDialog(context: Context) {
        AlertDialog.Builder(context)
            .setTitle(context.getString(R.string.no_internet))
            .setMessage(context.getString(R.string.turn_on_internet))
            .setCancelable(false)
            .setNegativeButton(context.getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(context.getString(R.string.ok)) { dialog, _ ->
                dialog.dismiss()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    val intent = Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY)
                    context.startActivity(intent)
                } else {
                    val intent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                    context.startActivity(intent)
                }
            }
            .show()
    }
}