package com.sorbonne.atom_d.entities.latency_experiments

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LatencyExperimentsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(result: LatencyExperiments)

    @Query("DELETE FROM latency_experiments")
    suspend fun deleteAll()

    @Query("DELETE FROM latency_experiments WHERE experiment_name LIKE :name")
    suspend fun delete(name: String)

    @Query("SELECT * FROM latency_experiments ORDER BY id ASC")
    fun getAll(): LiveData<List<LatencyExperiments>>

    @Query("SELECT COUNT(*) FROM latency_experiments")
    fun count(): Int
}
