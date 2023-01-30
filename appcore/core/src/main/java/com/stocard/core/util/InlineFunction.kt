package com.stocard.core.util

import android.os.Build
import android.os.Bundle
import android.os.Parcelable

/**
 * Created by Rafiqul Hasan
 */

inline fun <reified T : Parcelable> Bundle.parcelableData(key: String): T? = when {
	Build.VERSION.SDK_INT >= 33 -> getParcelable(key, T::class.java)
	else -> @Suppress("DEPRECATION") getParcelable(key) as? T
}