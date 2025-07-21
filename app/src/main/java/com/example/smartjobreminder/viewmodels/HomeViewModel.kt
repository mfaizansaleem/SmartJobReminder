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
class HomeViewModel @Inject constructor(private val repository: JobRepository) : ViewModel() {


    val selectedFilter = MutableStateFlow("All")
    val searchQuery = MutableStateFlow("")
    private val allJobs = repository.allJobs
    val jobsList: StateFlow<List<JobEntity>> = combine(
        allJobs,
        searchQuery,
        selectedFilter
    ) { jobs, query, filter ->
        jobs.filter { job ->
            val matchesQuery = if (query.isBlank()) true else job.title.contains(query, ignoreCase = true)

            val matchesFilter = when (filter) {
                "All" -> true
                "UpComing" -> job.status == JobStatus.UpComing
                "Applied" -> job.status == JobStatus.Applied
                "BookMarked" -> job.bookMarked
                else -> true
            }

            matchesQuery && matchesFilter
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    fun updateJob(entity: JobEntity) {
        viewModelScope.launch {
            repository.update(entity)
        }

    }
}