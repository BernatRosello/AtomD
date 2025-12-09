package com.sorbonne.atom_d.entities

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import com.sorbonne.atom_d.entities.chunk_experiments.ChunkExperiments
import com.sorbonne.atom_d.entities.chunk_experiments.ChunkExperimentsDao
import com.sorbonne.atom_d.entities.connections_attempts.ConnectionAttempts
import com.sorbonne.atom_d.entities.connections_attempts.ConnectionAttemptsDao
import com.sorbonne.atom_d.entities.custom_queries.CustomQueriesDao
import com.sorbonne.atom_d.entities.data_connection_attempts.DataConnectionAttempts
import com.sorbonne.atom_d.entities.data_connection_attempts.DataConnectionAttemptsDao
import com.sorbonne.atom_d.entities.data_file_experiments.DataFileExperiments
import com.sorbonne.atom_d.entities.data_file_experiments.DataFileExperimentsDao
import com.sorbonne.atom_d.entities.data_latency_experiments.DataLatencyExperimentsDao
import com.sorbonne.atom_d.entities.file_experiments.FileExperiments
import com.sorbonne.atom_d.entities.file_experiments.FileExperimentsDao
import com.sorbonne.atom_d.entities.latency_experiments.LatencyExperiments
import com.sorbonne.atom_d.entities.latency_experiments.LatencyExperimentsDao

@Database(entities = [
    ChunkExperiments::class,
    FileExperiments::class,
    DataFileExperiments::class,
    ConnectionAttempts::class,
    DataConnectionAttempts::class,
    LatencyExperiments::class
], version = 2)

abstract class RoomDatabase: androidx.room.RoomDatabase() {

    abstract fun chunkExperimentsDao(): ChunkExperimentsDao
    abstract fun FileExperimentsDao(): FileExperimentsDao
    abstract fun connectionAttemptsDao(): ConnectionAttemptsDao
    abstract fun latencyExperimentsDao() : LatencyExperimentsDao
    abstract fun customQueriesDao(): CustomQueriesDao

    abstract fun dataFileExperimentsDao(): DataFileExperimentsDao
    abstract fun dataConnectionAttemptsDao(): DataConnectionAttemptsDao
    abstract fun dataLatencyExperimentsDao(): DataLatencyExperimentsDao

    companion object {
        @Volatile
        private var INSTANCE: RoomDatabase? = null
        fun getDatabase(context: Context): RoomDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    RoomDatabase::class.java, "atomd_db"
                ).build()
            }
            return INSTANCE as RoomDatabase
        }
    }
}