package com.stocard.core.network

import android.content.Context
import android.net.ConnectivityManager
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * Created by Rafiqul Hasan
 */

class ForceCacheInterceptor(val context: Context) : Interceptor {
	override fun intercept(chain: Interceptor.Chain): Response {
		val builder: Request.Builder = chain.request().newBuilder()
		if (!isInternetAvailable(context)) {
			builder.cacheControl(CacheControl.FORCE_CACHE)
		}
		return chain.proceed(builder.build())
	}

	private fun isInternetAvailable(context: Context): Boolean {
		val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

		val network = connectivityManager.activeNetwork
		val capabilities = connectivityManager.getNetworkCapabilities(network)
		return capabilities != null
	}
}

