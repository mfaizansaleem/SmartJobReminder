package com.example.smartjobreminder.data.db

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun fromJobStatus(value: JobStatus): String {
        return value.name
    }

    @TypeConverter
    fun toJobStatus(value: String): JobStatus {
        return JobStatus.valueOf(value)
    }
}