package com.example.smartjobreminder.data.repository

import com.example.smartjobreminder.data.db.JobDao
import com.example.smartjobreminder.data.db.JobEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class JobRepository @Inject constructor(private val dao: JobDao) {

    val allNotes: Flow<List<JobEntity>> = dao.getAllNotes()

    suspend fun insert(job:JobEntity) = dao.insert(job)

    suspend fun delete(job:JobEntity) = dao.delete(job)
}