package com.sorbonne.atom_d.entities.latency_experiments

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

class EmissionMode {
    public val PARALLEL: Int = 0
    public val SERIES: Int = 1
}

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
    val id: Int,

    @ColumnInfo(name = "experiment_name")
    val expName: String,

    @ColumnInfo(name = "samples")
    val samples: Int,

    @ColumnInfo(name = "emission_mode")
    val emissionMode: Int
)

