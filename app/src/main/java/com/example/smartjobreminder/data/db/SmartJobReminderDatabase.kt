package com.example.smartjobreminder.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [JobEntity::class], version = 1)
abstract class SmartJobReminderDatabase : RoomDatabase() {
    abstract fun jobDao(): JobDao
}
