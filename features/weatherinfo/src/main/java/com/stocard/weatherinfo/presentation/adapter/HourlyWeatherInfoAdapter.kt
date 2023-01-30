package com.stocard.weatherinfo.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stocard.weatherinfo.databinding.SingleItemHourlyWeatherInfoBinding
import com.stocard.weatherinfo.domain.model.WeatherInfoHourly

/**
 * Created By Rafiqul Hasan
 */
class HourlyWeatherInfoAdapter :
    RecyclerView.Adapter<HourlyWeatherInfoAdapter.HourlyWeatherInfoViewHolder>() {
    private var list = listOf<WeatherInfoHourly>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyWeatherInfoViewHolder {
        return HourlyWeatherInfoViewHolder(
            SingleItemHourlyWeatherInfoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HourlyWeatherInfoViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setHourlyWeatherInfo(list: List<WeatherInfoHourly>) {
        this.list = list
        notifyDataSetChanged()
    }

    inner class HourlyWeatherInfoViewHolder(private val binding: SingleItemHourlyWeatherInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(hourlyInfo: WeatherInfoHourly) = with(binding) {
            weatherInfo = hourlyInfo
        }
    }
}