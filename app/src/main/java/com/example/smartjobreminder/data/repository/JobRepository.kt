package com.example.smartjobreminder.data.repository

import com.example.smartjobreminder.data.db.JobDao
import com.example.smartjobreminder.data.db.JobEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class JobRepository @Inject constructor(private val dao: JobDao) {

    val allJobs: Flow<List<JobEntity>> = dao.getAllJobs()

    suspend fun insert(job:JobEntity) = dao.insert(job)

    suspend fun delete(job:JobEntity) = dao.delete(job)
    suspend fun update(job: JobEntity) =dao.update(job)
}