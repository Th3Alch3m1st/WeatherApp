package com.stocard.weatherinfo.data.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Build
import androidx.core.content.ContextCompat
import com.stocard.weatherinfo.R
import com.stocard.weatherinfo.domain.model.LocationInfo
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.*
import javax.inject.Inject
import kotlin.coroutines.resume


/**
 * Created by Rafiqul Hasan
 */

class LocationTrackerImpl @Inject constructor(
	private val locationClient: FusedLocationProviderClient,
	@ApplicationContext val context: Context
) : ILocationTracker {
	@SuppressLint("MissingPermission")
	override suspend fun getCurrentLocation(): LocationInfo? {

		val hasAccessCoarseLocationPermission = ContextCompat.checkSelfPermission(
			context, Manifest.permission.ACCESS_COARSE_LOCATION
		) == PackageManager.PERMISSION_GRANTED

		val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
		val isGpsEnabled =
			locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) || locationManager.isProviderEnabled(
				LocationManager.GPS_PROVIDER
			)
		if (!hasAccessCoarseLocationPermission || !isGpsEnabled) {
			return null
		}

		return suspendCancellableCoroutine { canCon ->
			locationClient.lastLocation.addOnCompleteListener { task ->
				if (task.isComplete && task.result != null) {
					getLocationWithAddress(task.result, canCon)
				} else {
					canCon.resume(null)
				}

			}.addOnFailureListener {
				canCon.resume(null)
			}.addOnCanceledListener {
				canCon.resume(null)
				canCon.cancel()
			}
		}
	}

	private fun getLocationWithAddress(
		location: Location, canCon: CancellableContinuation<LocationInfo>
	) {
		try {
			val geocoder = Geocoder(context, Locale.getDefault())

			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
				geocoder.getFromLocation(
					location.latitude,
					location.longitude,
					1,
					object : Geocoder.GeocodeListener {
						override fun onGeocode(addresses: MutableList<Address>) {
							canCon.resume(
								LocationInfo(
									location.latitude, location.longitude, getAddress(addresses)
								)
							)
						}

						override fun onError(errorMessage: String?) {
							super.onError(errorMessage)
							canCon.resume(
								LocationInfo(
									location.latitude, location.longitude, getAddress(null)
								)
							)
						}
					})
			} else {
				val address = geocoder.getFromLocation(location.latitude, location.longitude, 1)
				canCon.resume(
					LocationInfo(
						location.latitude, location.longitude, getAddress(address)
					)
				)
			}
		} catch (ex: Exception) {
			canCon.resume(
				LocationInfo(location.latitude, location.longitude, getAddress(null))
			)
		}
	}

	fun getAddress(list: List<Address>?): String {
		return if (list.isNullOrEmpty()) {
			context.getString(R.string.current_location)
		} else {
			val string = StringBuilder("")
			list[0].locality?.let {
				string.append(it)
			}

			list[0].adminArea?.let {
				string.append(", $it")
			}
			string.toString()
		}
	}
}