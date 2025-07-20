package com.example.smartjobreminder.viewmodels

import androidx.lifecycle.ViewModel
import com.example.smartjobreminder.data.repository.JobRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: JobRepository) : ViewModel() {


}