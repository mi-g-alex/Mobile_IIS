package by.g_alex.mobile_iis.presentation.study_screen.add_mark_sheet

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import by.g_alex.mobile_iis.data.remote.dto.study.mark_sheet.MarkSheetSubjectsDto
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date
import java.util.Locale

@Composable
fun AddMarkSheetScreen(
    viewModel: AddMarkSheetViewModel = hiltViewModel()
) {
    val cnt = LocalContext.current

    LaunchedEffect(
        viewModel.selectedTypeOfSubj.value
    ) {
        if (viewModel.selectedTypeOfSubj.value != null) {
            viewModel.getListById()
        } else {
            viewModel.employeeListById.value = emptyList()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Заказать ведомостичку")
                },
            )
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(horizontal = 15.dp)
        ) {
            LazyColumn {
                item {
                    if (viewModel.subjOfMarkSheet.value.isNotEmpty()) SelectSubjMenuBox(
                        viewModel.subjOfMarkSheet.value,
                        viewModel
                    )
                }

                item {
                    SelectTypeOfSubjMenuBox(viewModel)
                }

                item {
                    SelectEmployeeMenuBox(viewModel)
                }

                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                modifier = Modifier, text = "Уважительная причина", fontSize = 25.sp
                            )
                            Text(
                                modifier = Modifier,
                                text = "Если выключено - не уважительная.",
                                fontSize = 15.sp
                            )
                        }
                        Switch(modifier = Modifier.padding(start = 10.dp),
                            checked = viewModel.isGoodReason.value,
                            onCheckedChange = {
                                viewModel.isGoodReason.value = it
                            })
                    }
                }

                item {
                    val dateTime = LocalDateTime.now()
                    val bq = remember {
                        mutableStateOf(false)
                    }

                    val datePickerState = remember {
                        DatePickerState(
                            yearRange = (dateTime.year.minus(10)..dateTime.year),
                            initialDisplayedMonthMillis = dateTime.toMiles(),
                            initialDisplayMode = DisplayMode.Picker,
                            initialSelectedDateMillis = dateTime.toMiles()
                        )
                    }

                    OutlinedTextField(readOnly = true,
                        value = SimpleDateFormat(
                            "dd.MM.yyyy", Locale.getDefault()
                        ).format(Date(datePickerState.selectedDateMillis!!)),
                        onValueChange = {},
                        modifier = Modifier
                            .clickable {
                                bq.value = true
                            }
                            .fillMaxWidth(),
                        trailingIcon = {
                            Icon(Icons.Filled.DateRange, null, Modifier.clickable {
                                bq.value = true
                            })
                        })

                    if (bq.value) DatePickerDialog(onDismissRequest = { bq.value = false },
                        confirmButton = {
                            TextButton(onClick = { bq.value = false }) {
                                Text("Выбрать")
                            }
                        }) {
                        DatePicker(state = datePickerState, showModeToggle = false)
                    }
                }
                item {

                    OutlinedTextField(
                        value = viewModel.studentIdText.value,
                        shape = MaterialTheme.shapes.large,
                        onValueChange = { newText ->
                            if (newText.isNotEmpty()) {
                                if (newText[newText.length - 1] in '0'..'9') viewModel.studentIdText.value =
                                    newText
                            } else {
                                viewModel.studentIdText.value = newText
                            }
                        },
                        label = { Text(text = "Часов пропусков") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp),
                        enabled = viewModel.selectedTypeOfSubj.value?.isLab == true
                    )
                }
                item {
                    OutlinedButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                        onClick = {
                            Toast.makeText(cnt, "Will Come soom", Toast.LENGTH_SHORT).show()
                        },
                    ) {

                        Text("Заказать")
                    }
                }
            }
        }
    }
}

@Composable
fun SelectSubjMenuBox(a: List<MarkSheetSubjectsDto?>, viewModel: AddMarkSheetViewModel) {
    val expandedTypes = viewModel.expandedSubj
    val selectedTypes = viewModel.selectedSubj
    val selectedTextTypes = viewModel.selectedTextSubj
    ExposedDropdownMenuBox(
        expanded = expandedTypes.value, onExpandedChange = {
            expandedTypes.value = !expandedTypes.value
        }, modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    ) {
        OutlinedTextField(
            readOnly = true,
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            value = selectedTextTypes.value,
            onValueChange = { selectedTextTypes.value = it; expandedTypes.value = true },
            label = { Text("Дисциплина") },
            singleLine = true
        )
        ExposedDropdownMenu(expanded = expandedTypes.value, onDismissRequest = {
            expandedTypes.value = false
        }) {
            a.forEach { b ->
                if (b != null) {

                    DropdownMenuItem(
                        onClick = {
                            expandedTypes.value = false
                            selectedTypes.value = b
                            selectedTextTypes.value = b.abbrev + " (${b.term.toString()} сем.)"
                            viewModel.selectedTypeOfSubj.value = null
                            viewModel.selectedTextTypeOfSubj.value = ""
                        },
                        text = {
                            Text(
                                b.abbrev + " (${b.term.toString()}) сем.",
                            )
                        },
                    )
                    Divider(
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .height(1.dp)
                            .fillMaxWidth(),
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            }
        }
    }
}

@Composable
fun SelectTypeOfSubjMenuBox(viewModel: AddMarkSheetViewModel) {
    val expandedTypes = viewModel.expandedTypeOfSubj
    val selectedTypes = viewModel.selectedTypeOfSubj
    val selectedTextTypes = viewModel.selectedTextTypeOfSubj
    ExposedDropdownMenuBox(
        expanded = expandedTypes.value,
        onExpandedChange = {
            expandedTypes.value = !expandedTypes.value
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
    ) {
        OutlinedTextField(
            readOnly = true,
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            value = selectedTextTypes.value,
            onValueChange = { selectedTextTypes.value = it; expandedTypes.value = true },
            label = { Text("Тип") },
            singleLine = true,
            enabled = viewModel.selectedSubj.value != null
        )
        ExposedDropdownMenu(expanded = expandedTypes.value, onDismissRequest = {
            expandedTypes.value = false
        }) {
            viewModel.selectedSubj.value?.lessonTypes?.forEach { b ->
                if (b != null) {

                    DropdownMenuItem(
                        onClick = {
                            expandedTypes.value = false
                            selectedTypes.value = b
                            selectedTextTypes.value = b.abbrev.toString()
                        },
                        text = {
                            Text(
                                b.abbrev.toString(),
                            )
                        },
                    )
                    Divider(
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .height(1.dp)
                            .fillMaxWidth(),
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            }
        }
    }
}

@Composable
fun SelectEmployeeMenuBox(viewModel: AddMarkSheetViewModel) {
    val expandedTypes = viewModel.expandedEmployeeListById
    val selectedTypes = viewModel.selectedEmployeeListById
    val selectedTextTypes = viewModel.selectedTextEmployeeListById
    ExposedDropdownMenuBox(
        expanded = expandedTypes.value,
        onExpandedChange = {
            if (viewModel.employeeListById.value.isNotEmpty() || viewModel.employeeList.value.isNotEmpty()) expandedTypes.value =
                !expandedTypes.value
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
    ) {
        OutlinedTextField(
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            value = selectedTextTypes.value,
            onValueChange = { selectedTextTypes.value = it; expandedTypes.value = true },
            label = { Text("Преподаватель") },
            singleLine = true,
            enabled = viewModel.employeeListById.value.isNotEmpty() || viewModel.employeeList.value.isNotEmpty()
        )
        ExposedDropdownMenu(expanded = expandedTypes.value, onDismissRequest = {
            expandedTypes.value = false
        }) {

            viewModel.employeeListById.value.forEach { b ->
                DropdownMenuItem(
                    onClick = {
                        expandedTypes.value = false
                        selectedTypes.value = b
                        selectedTextTypes.value = b?.fio.toString()
                    },
                    text = {
                        Text(b?.fio.toString())
                    },
                )
                Divider(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .height(1.dp)
                        .fillMaxWidth(),
                    color = MaterialTheme.colorScheme.outline
                )
            }
            viewModel.employeeList.value.forEach { b ->
                if (b?.fio?.startsWith(
                        selectedTextTypes.value, ignoreCase = true
                    ) == true && selectedTextTypes.value.length > 1
                ) {
                    DropdownMenuItem(
                        onClick = {
                            expandedTypes.value = false
                            selectedTypes.value = b
                            selectedTextTypes.value = b.fio.toString()
                        },
                        text = {
                            Text(b.fio.toString())
                        },
                    )
                    Divider(
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .height(1.dp)
                            .fillMaxWidth(),
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            }
        }
    }
}


fun LocalDateTime.toMiles() = this.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

