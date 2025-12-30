package com.sorbonne.atom_d.entities.data_latency_experiments

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "data_latency_experiments")
@TypeConverters(DataLatencyConverters::class)
data class DataLatencyExperiments(

    @PrimaryKey(autoGenerate = true)
    val Id:Int,

    @ColumnInfo(name = "experiment_id")
    val experimentId: Long,

    @ColumnInfo(name = "experiment_name")
    val experimentName: String,

    @ColumnInfo(name = "source_id")
    val sourceId: String,

    @ColumnInfo(name = "target_id")
    val targetId: String,

    @ColumnInfo(name = "strategy")
    val strategy: String,

    @ColumnInfo(name = "emission_mode")
    val emissionMode: String,

    @ColumnInfo(name = "total_samples")
    val totalSamples: Int,

    @ColumnInfo(name = "latency_samples")
    val latencySamples: ArrayList<Double>,

    @ColumnInfo(name = "average_latency")
    val avgLatency: Double,

    @ColumnInfo(name = "minimum_latency")
    val minLatency: Double,

    @ColumnInfo(name = "maximum_latency")
    val maxLatency: Double,

    @ColumnInfo(name = "standard_deviation_of_latency")
    val sdLatency: Double
)