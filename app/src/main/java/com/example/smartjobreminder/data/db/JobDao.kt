package com.example.smartjobreminder.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface JobDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: JobEntity)

    @Delete
    suspend fun delete(note: JobEntity)

    @Query("SELECT * FROM JobEntity ORDER BY jobId DESC")
    fun getAllJobs(): Flow<List<JobEntity>>

    @Update
    suspend fun update(job: JobEntity)
}