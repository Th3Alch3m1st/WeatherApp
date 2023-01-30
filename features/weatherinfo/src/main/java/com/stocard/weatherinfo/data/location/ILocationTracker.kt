package com.stocard.weatherinfo.data.location

import com.stocard.weatherinfo.domain.model.LocationInfo

/**
 * Created by Rafiqul Hasan
 */
interface ILocationTracker {
	suspend fun getCurrentLocation(): LocationInfo?
}