package com.example.showlogsapp

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.Request
import kotlinx.coroutines.*

class Requester(val url: String) {
    private var httpClient: OkHttpClient = OkHttpClient()
    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory()).build()

    public fun getLastNLogsFromServer(n: Int) : ArrayList<Log> {
        return runBlocking {
            val deferred = async(Dispatchers.IO) {
                val request = Request.Builder().url(url + "?count=" + n.toString())
                    .get().build()

                val response = httpClient.newCall(request).execute()
                if (response.isSuccessful) {
                    val logList = response.body!!.string()

                    val jsonAdapterResponse = moshi.adapter<ArrayList<Log>>(
                        Types.newParameterizedType(List::class.java, Log::class.java)
                    )

                    jsonAdapterResponse.fromJson(logList)!!
                }
                else {
                    arrayListOf()
                }
            }

            return@runBlocking deferred.await()
        }
    }

    companion object {
        @JvmStatic
        fun isInternetConnect(context: Context) : Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
                    as ConnectivityManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val network = connectivityManager.activeNetwork ?: return false
                val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

                return when {
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    else -> false
                }
            }
            else {
                @Suppress("DEPRECATION") val networkInfo =
                    connectivityManager.activeNetworkInfo ?: return false
                @Suppress("DEPRECATION")
                return networkInfo.isConnected
            }
        }
    }
}