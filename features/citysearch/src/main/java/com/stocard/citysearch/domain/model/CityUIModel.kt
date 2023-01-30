package com.stocard.citysearch.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Rafiqul Hasan
 */
@Parcelize
data class CityUIModel(
	val cityName: String,
	val country: String,
	val lat: Double,
	val lon: Double
) : Parcelable