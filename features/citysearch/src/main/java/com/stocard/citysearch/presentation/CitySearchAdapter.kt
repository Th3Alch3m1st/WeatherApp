package com.stocard.citysearch.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.stocard.citysearch.databinding.SingleItemCityBinding
import com.stocard.citysearch.domain.model.CityUIModel
import com.stocard.core.util.setSafeOnClickListener

/**
 * Created By Rafiqul Hasan
 */

class CitySearchAdapter(private val callBack: CitySearchAdapterCallBack) : RecyclerView.Adapter<CitySearchAdapter.CityViewHolder>(),Filterable {
    private var cityList = listOf<CityUIModel>()
    private var filteredList = listOf<CityUIModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        return CityViewHolder(
            SingleItemCityBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bind(filteredList[position].cityName)
        holder.binding.root.setSafeOnClickListener {
            callBack.onCitySelect(filteredList[position])
        }
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }


    override fun getFilter() : Filter{
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence): FilterResults {
                val filteredList = cityList.filter { city ->
                    city.cityName.contains(constraint, true)
                }
                val results = FilterResults()
                results.values = filteredList
                return results
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (results?.values is List<*>) {
                    val list = results.values as List<CityUIModel>
                    filteredList = list
                    notifyDataSetChanged()
                    callBack.onFilterComplete(list.isEmpty())
                }
            }
        }
    }

    fun setCityList(list: List<CityUIModel>) {
        this.cityList = list
        this.filteredList = list
    }

    inner class CityViewHolder(val binding: SingleItemCityBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(name: String) = with(binding) {
            city = name
        }
    }

    interface CitySearchAdapterCallBack {
        fun onFilterComplete(isEmpty: Boolean, message:String? = null)
        fun onCitySelect(cityUIModel: CityUIModel)
    }
}