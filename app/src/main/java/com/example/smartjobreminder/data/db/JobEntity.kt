package com.example.smartjobreminder.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class JobEntity(
    @PrimaryKey val jobId: Int,
    val jobTitle : String,
    val jobDescription : String,

)