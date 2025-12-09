package com.sorbonne.atom_d.entities.data_latency_experiments

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface DataLatencyExperimentsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(data: DataLatencyExperiments)
}