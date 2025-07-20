package com.example.smartjobreminder.di

import android.content.Context
import androidx.room.Room
import com.example.smartjobreminder.data.db.JobDao
import com.example.smartjobreminder.data.db.SmartJobReminderDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): SmartJobReminderDatabase {
        return Room.databaseBuilder(
            context,
            SmartJobReminderDatabase::class.java,
            "SmartJobReminderDatabase"
        ).build()
    }

    @Provides
    fun provideJobDao(db: SmartJobReminderDatabase): JobDao = db.jobDao()
}