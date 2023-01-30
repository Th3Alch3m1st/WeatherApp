package com.stocard.weatherinfo.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stocard.weatherinfo.databinding.SingleItemDailyWeatherInfoBinding
import com.stocard.weatherinfo.domain.model.WeatherInfoDaily

/**
 * Created By Rafiqul Hasan
 */
class DailyWeatherInfoAdapter :
    RecyclerView.Adapter<DailyWeatherInfoAdapter.DailyWeatherInfoViewHolder>() {
    private var list = listOf<WeatherInfoDaily>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyWeatherInfoViewHolder {
        return DailyWeatherInfoViewHolder(
            SingleItemDailyWeatherInfoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DailyWeatherInfoViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setHourlyWeatherInfo(list: List<WeatherInfoDaily>) {
        this.list = list
        notifyDataSetChanged()
    }

    inner class DailyWeatherInfoViewHolder(private val binding: SingleItemDailyWeatherInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(hourlyInfo: WeatherInfoDaily) = with(binding) {
            weatherInfo = hourlyInfo
        }
    }
}