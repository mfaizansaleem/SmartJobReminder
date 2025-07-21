package com.example.smartjobreminder.viewmodels

import androidx.compose.ui.graphics.Path.Companion.combine
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartjobreminder.data.db.JobEntity
import com.example.smartjobreminder.data.db.JobStatus
import com.example.smartjobreminder.data.repository.JobRepository
import com.example.smartjobreminder.utils.scheduleJobReminder
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AddJobViewModel @Inject constructor(private val repository: JobRepository) : ViewModel() {


    val title = MutableStateFlow("")
    val company = MutableStateFlow("")
    val selectedStatus = MutableStateFlow(JobStatus.UpComing)
    val deadline = MutableStateFlow("")
    val isBookmarked = MutableStateFlow(false)

    private val _uiMessages = MutableSharedFlow<String>()
    val uiMessages = _uiMessages.asSharedFlow()

    private val _setJobReminder = MutableSharedFlow<JobEntity>()
    val setJobReminder = _setJobReminder.asSharedFlow().stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        null
    )
    private fun validateFields(): Boolean {
        return title.value.isNotBlank() &&
                company.value.isNotBlank() &&
                deadline.value.isNotBlank()
    }

    fun saveJob() {
        if (validateFields()) {
            val job = JobEntity(
                title = title.value,
                company = company.value,
                status = selectedStatus.value,
                deadLine = deadline.value,
                bookMarked = isBookmarked.value
            )
            viewModelScope.launch {
                repository.insert(job)
                _uiMessages.emit("Job saved successfully")
                _setJobReminder.emit(job)
            }

        } else {
            viewModelScope.launch {
                _uiMessages.emit("Please fill in all required fields.")
            }
        }
    }
}
