package com.sorbonne.atom_d.entities.data_latency_experiments

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "data_latency_experiments")
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

    @ColumnInfo(name = "sample")
    val sample: Int,

    @ColumnInfo(name = "total_samples")
    val totalSamples: Int,

    @ColumnInfo(name = "latency")
    val latency: Double,

    @ColumnInfo(name = "strategy")
    val strategy: Int
)