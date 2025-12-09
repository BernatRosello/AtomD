package com.sorbonne.atom_d.entities.latency_experiments

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "latency_experiments",
    indices = [
        Index(
            value = ["experiment_name"],
            unique = true
        )
    ]
)
data class LatencyExperiments(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "experiment_name")
    val expName: String,

    @ColumnInfo(name = "tries")
    val tries: Int,

    @ColumnInfo(name = "average_latency")
    val avgLatency: Double,

    @ColumnInfo(name = "minimum_latency")
    val minLatency: Double,

    @ColumnInfo(name = "maximum_latency")
    val maxLatency: Double,

    @ColumnInfo(name = "standard_deviation_of_latency")
    val sdLatency: Double
)

