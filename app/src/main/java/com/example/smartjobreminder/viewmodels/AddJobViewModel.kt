package com.example.smartjobreminder.viewmodels

import androidx.compose.ui.graphics.Path.Companion.combine
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartjobreminder.data.db.JobEntity
import com.example.smartjobreminder.data.db.JobStatus
import com.example.smartjobreminder.data.repository.JobRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddJobViewModel @Inject constructor(private val repository: JobRepository) : ViewModel() {


    val title = MutableStateFlow("")
    val company = MutableStateFlow("")
    val selectedStatus = MutableStateFlow(JobStatus.UpComing)
    val deadline = MutableStateFlow("")
    val isBookmarked = MutableStateFlow(false)

    fun saveJob() {
        val job = JobEntity(
            title = title.value,
            company = company.value,
            status = selectedStatus.value,
            deadLine = deadline.value,
            bookMarked = isBookmarked.value
        )

        viewModelScope.launch {
            repository.insert(job)
        }
    }
}