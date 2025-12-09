package com.sorbonne.atom_d.entities

import android.app.Application
import androidx.lifecycle.LiveData
import com.sorbonne.atom_d.entities.chunk_experiments.ChunkExperiments
import com.sorbonne.atom_d.entities.chunk_experiments.ChunkExperimentsDao
import com.sorbonne.atom_d.entities.connections_attempts.ConnectionAttempts
import com.sorbonne.atom_d.entities.connections_attempts.ConnectionAttemptsDao
import com.sorbonne.atom_d.entities.custom_queries.CustomQueriesDao
import com.sorbonne.atom_d.entities.data_connection_attempts.DataConnectionAttempts
import com.sorbonne.atom_d.entities.data_connection_attempts.DataConnectionAttemptsDao
import com.sorbonne.atom_d.entities.data_file_experiments.DataFileExperiments
import com.sorbonne.atom_d.entities.data_file_experiments.DataFileExperimentsDao
import com.sorbonne.atom_d.entities.file_experiments.FileExperiments
import com.sorbonne.atom_d.entities.file_experiments.FileExperimentsDao
import com.sorbonne.atom_d.entities.latency_experiments.LatencyExperiments
import com.sorbonne.atom_d.entities.latency_experiments.LatencyExperimentsDao
import com.sorbonne.atom_d.entities.data_latency_experiments.DataLatencyExperiments
import com.sorbonne.atom_d.entities.data_latency_experiments.DataLatencyExperimentsDao

class DatabaseRepository(application: Application){

    private var chunkExperimentsDao: ChunkExperimentsDao
    private var fileExperimentsDao: FileExperimentsDao
    private var connectionAttemptsDao: ConnectionAttemptsDao
    private var latencyExperimentsDao: LatencyExperimentsDao
    private var customQueriesDao: CustomQueriesDao

    private var dataFileExperimentsDao: DataFileExperimentsDao
    private var dataConnectionAttemptsDao: DataConnectionAttemptsDao
    private var dataLatencyExperimentsDao: DataLatencyExperimentsDao


    init {
        val db = RoomDatabase.getDatabase(application)

        chunkExperimentsDao = db.chunkExperimentsDao()
        fileExperimentsDao = db.FileExperimentsDao()
        connectionAttemptsDao = db.connectionAttemptsDao()
        latencyExperimentsDao = db.latencyExperimentsDao()
        customQueriesDao = db.customQueriesDao()

        dataFileExperimentsDao = db.dataFileExperimentsDao()
        dataConnectionAttemptsDao = db.dataConnectionAttemptsDao()
        dataLatencyExperimentsDao = db.dataLatencyExperimentsDao()

    }

    /*
     * =========================================================================
     * Chunk Experiments
     * =========================================================================
     */

    fun getAllChunks(): LiveData<List<ChunkExperiments>> {
        return chunkExperimentsDao.getMessages()
    }

    suspend fun insertChunkExperiment(chunkExperiments: ChunkExperiments) {
        chunkExperimentsDao.insert(chunkExperiments)
    }

    suspend fun deleteChunkExperiment(name: String) {
        chunkExperimentsDao.delete(name)
    }

    fun chunkExperimentExists(name: String, size: Int, attempts: Int): Boolean {
        return chunkExperimentsDao.messageExists(name, size, attempts)
    }

    /*
     * =========================================================================
     * File Experiments
     * =========================================================================
     */

    fun getAllFileExperiments(): LiveData<List<FileExperiments>> {
        return fileExperimentsDao.getFiles()
    }

    suspend fun insertFileExperiment(fileExperiments: FileExperiments){
        fileExperimentsDao.insert(fileExperiments)
    }

    suspend fun deleteFileExperiment(name: String){
        fileExperimentsDao.delete(name)
    }

    /*
     * =========================================================================
     * connectionAttempts
     * =========================================================================
     */

    fun getAllConnectionAttempts(): LiveData<List<ConnectionAttempts>> {
        return connectionAttemptsDao.getRepetitions()
    }

    suspend fun insertConnectionAttempts(connectionAttempts: ConnectionAttempts){
        connectionAttemptsDao.insert(connectionAttempts)
    }

    suspend fun deleteConnectionAttempts(name: String){
        connectionAttemptsDao.delete(name)
    }

    /*
     * =========================================================================
     * customQuery
     * =========================================================================
     */

    fun getAllExperimentsName(): LiveData<List<CustomQueriesDao.AllExperimentsName>> {
        return customQueriesDao.getAllExperimentsName()
    }

    /*
     * =========================================================================
     * dataFileExperiments
     * =========================================================================
     */

    suspend fun insertDataFileExperiments(dataFileExperiments: DataFileExperiments){
        dataFileExperimentsDao.insert(dataFileExperiments)
    }

    /*
     * =========================================================================
     * dataConnectionAttempts
     * =========================================================================
     */

    suspend fun insertDataConnectionAttempts(dataConnectionAttempts: DataConnectionAttempts){
        dataConnectionAttemptsDao.insert(dataConnectionAttempts)
    }


    /*
     * =========================================================================
     * Latency Experiments  ✅ NEW SECTION
     * =========================================================================
     */

    fun getAllLatencyExperiments(): LiveData<List<LatencyExperiments>> {
        return latencyExperimentsDao.getAll()
    }

    suspend fun insertLatencyExperiment(latency: LatencyExperiments) {
        latencyExperimentsDao.insert(latency)
    }

    suspend fun deleteLatencyExperiment(name: String) {
        latencyExperimentsDao.delete(name)
    }

    suspend fun deleteAllLatencyExperiments() {
        latencyExperimentsDao.deleteAll()
    }
    /*
     * =========================================================================
     * dataLatencyExperiments
     * =========================================================================
     */

    suspend fun insertDataLatencyExperiments(dataLatencyExperiments: DataLatencyExperiments){
        dataLatencyExperimentsDao.insert(dataLatencyExperiments)
    }
}
