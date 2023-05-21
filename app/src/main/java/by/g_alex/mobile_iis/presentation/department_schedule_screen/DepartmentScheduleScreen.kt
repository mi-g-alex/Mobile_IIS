package by.g_alex.mobile_iis.presentation.department_schedule_screen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun DepartmentScheduleScreen(
    viewModel: DepartmentScheduleViewModel = hiltViewModel()
) {
    val expanded = viewModel.expanded
    val selectedText = viewModel.selectedText
    val selectedId = viewModel.selectedId
    val departments = viewModel.state.value.departmentState ?: emptyList()
    val anonses = viewModel.AnState.value.anonsState ?: emptyList()
    val clicked = remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Расписание кафедры", fontSize = 20.sp) }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            ExposedDropdownMenuBox(
                expanded = expanded.value,
                onExpandedChange = {
                    expanded.value = !expanded.value
                },
            ) {
                OutlinedTextField(
                    readOnly = true,
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    value = selectedText.value,
                    onValueChange = { },
                    label = { Text("Кафедра") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                        /*textColor = MaterialTheme.colorScheme.inverseSurface*/
                    ),
                )
                ExposedDropdownMenu(
                    expanded = expanded.value,
                    onDismissRequest = {
                        expanded.value = false
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    departments.forEach { selectionOption ->
                        DropdownMenuItem(
                            onClick = {
                                expanded.value = false
                                selectedText.value = selectionOption.abbrev
                                selectedId.value = selectionOption.id
                                viewModel.getAnnounsments(selectionOption.id)
                            },
                            text = {
                                Text(
                                    text = selectionOption.abbrev,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
            Button(
                onClick = {
                    if (selectedText.value != "")
                        clicked.value = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://iis.bsuir.by/api/v1/departments/report?department-id=" + selectedId.value.toString())
                )
                if (clicked.value) {
                    LocalContext.current.startActivity(intent)
                    clicked.value = false
                }
                Text(text = "Скачать расписание кафедры", color = MaterialTheme.colorScheme.surface)
            }
            if (anonses.isNotEmpty()) {
                LazyColumn() {
                    items(anonses) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp)
                            ) {
                                Text(
                                    text = it.content ?: "",
                                    modifier = Modifier
                                        .align(Alignment.CenterHorizontally)
                                        .padding(bottom = 5.dp),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp
                                )
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(text = it.startTime + "-" + it.endTime)
                                    Text(text = it.date ?: "")
                                }
                                Text(text = it.auditory ?: "")
                                Text(text = it.employee ?: "")
                                val groups = remember { mutableStateOf("") }
                                groups.value = ""
                                it.studentGroups?.onEach { group ->
                                    groups.value += group.name + if (it.studentGroups.indexOf(group) != it.studentGroups.size - 1) ","
                                    else "."
                                }
                                Text(text = groups.value)
                            }
                        }
                    }
                }
            }
            if (viewModel.AnState.value.isLoading) {
                CircularProgressIndicator()
            }
        }
    }
}