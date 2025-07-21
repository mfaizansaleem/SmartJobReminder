package com.example.smartjobreminder.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class JobEntity(
    @PrimaryKey(autoGenerate = true) val jobId: Int = 0,
    val title: String,
    val company: String,
    val status: JobStatus,
    val deadLine: String,
    var bookMarked: Boolean = false,

    )