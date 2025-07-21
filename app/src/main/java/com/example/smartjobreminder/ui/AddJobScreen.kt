package com.example.smartjobreminder.ui

import android.widget.Toast
import android.widget.CalendarView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.smartjobreminder.data.db.JobStatus
import com.example.smartjobreminder.viewmodels.AddJobViewModel
import com.example.smartjobreminder.utils.scheduleJobReminder
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun AddJobScreen(
    navController: NavController,
    viewModel: AddJobViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.uiMessages.collect { message ->
            message.let {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        }
    }
    LaunchedEffect(Unit) {
        viewModel.setJobReminder.collect { job ->
            scheduleJobReminder(context, job!!)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                WindowInsets.systemBars
                    .only(WindowInsetsSides.Bottom + WindowInsetsSides.Top)
                    .asPaddingValues()
            )
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Add a Job",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            value = viewModel.title.collectAsState().value,
            onValueChange = { viewModel.title.value = it },
            label = { Text("Job Title") }
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            value = viewModel.company.collectAsState().value,
            onValueChange = { viewModel.company.value = it },
            label = { Text("Company") }
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            text = "Select Deadline",
            style = MaterialTheme.typography.titleMedium
        )

        AndroidView(
            factory = { context ->
                CalendarView(context).apply {
                    setOnDateChangeListener { _, year, month, dayOfMonth ->
                        val formattedDate = "%04d-%02d-%02d".format(year, month + 1, dayOfMonth)
                        viewModel.deadline.value = formattedDate
                    }
                }
            },
            update = { calendarView ->

                if (viewModel.deadline.value.isNotBlank()) {
                    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    val date = formatter.parse(viewModel.deadline.value)
                    date?.let { calendarView.date = it.time }
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            text = "Selected Deadline: ${viewModel.deadline.collectAsState().value}",
            style = MaterialTheme.typography.bodyMedium
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = viewModel.isBookmarked.collectAsState().value,
                onCheckedChange = { viewModel.isBookmarked.value = it }
            )
            Text("Bookmarked")
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            JobStatus.values().forEach { statusOption ->
                FilterChip(
                    selected = viewModel.selectedStatus.collectAsState().value == statusOption,
                    onClick = { viewModel.selectedStatus.value = statusOption },
                    label = { Text(statusOption.name) }
                )
            }
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp, vertical = 20.dp),
            onClick = {
                viewModel.saveJob()
                navController.navigateUp()
            },
        ) {
            Text("Save Job")
        }
    }
}