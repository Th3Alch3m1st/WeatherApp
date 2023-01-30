package com.stocard.weatherinfo.utils

import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import com.stocard.core.model.UnitMeasurement
import com.stocard.weatherinfo.R

/**
 * Created By Rafiqul Hasan
 */
@BindingAdapter("temperature", "unit", requireAll = true)
fun AppCompatTextView.setTemperature(temperature: Float?, unit: UnitMeasurement?) {
	temperature?.let {
		if(unit == UnitMeasurement.METRIC){
			this.text = this.context.getString(R.string.x_degree_celsius,temperature)
		}else{
			this.text = this.context.getString(R.string.x_degree_Fahrenheit,temperature)
		}
	}
}

@BindingAdapter("wind_speed", "unit", requireAll = true)
fun AppCompatTextView.setWindSpeed(windSpeed: Float?, unit: UnitMeasurement?) {
	windSpeed?.let {
		if(unit == UnitMeasurement.METRIC){
			this.text = this.context.getString(R.string.x_windspeed_metric,windSpeed)
		}else{
			this.text = this.context.getString(R.string.x_windspeed_imperial,windSpeed)
		}
	}
}