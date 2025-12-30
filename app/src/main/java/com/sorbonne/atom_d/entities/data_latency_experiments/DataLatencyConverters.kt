package com.sorbonne.atom_d.entities.data_latency_experiments

import androidx.room.TypeConverter
import org.json.JSONArray

class DataLatencyConverters {

    @TypeConverter
    fun fromLatencySamples(samples: ArrayList<Double>?): String {
        if (samples == null) return "[]"

        val jsonArray = JSONArray()
        samples.forEach { jsonArray.put(it) }
        return jsonArray.toString()
    }

    @TypeConverter
    fun toLatencySamples(data: String?): ArrayList<Double> {
        val result = ArrayList<Double>()
        if (data.isNullOrEmpty()) return result

        val jsonArray = JSONArray(data)
        for (i in 0 until jsonArray.length()) {
            result.add(jsonArray.getDouble(i))
        }
        return result
    }
}