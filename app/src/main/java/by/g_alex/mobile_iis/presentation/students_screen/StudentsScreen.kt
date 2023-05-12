package by.g_alex.mobile_iis.presentation.students_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Switch
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import by.g_alex.mobile_iis.R
import by.g_alex.mobile_iis.data.remote.dto.students.StudentsRequestDto
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce

@OptIn(FlowPreview::class)
@Composable
fun StudentsScreen(
    viewModel: StudentsViewModel = hiltViewModel()
) {
    val bufRequest = StudentsRequestDto(
        course = emptyList(),
        currentPage = 0,
        faculties = emptyList(),
        lastName = "",
        searchJob = false,
        skills = emptyList()
    )
    val res = remember { mutableStateOf(viewModel.pageList) }
    val currentPage = remember { mutableStateOf(0) }
    val currentInd = remember { mutableStateOf(0) }
    val lastName = remember { mutableStateOf("") }
    val openDialog = remember { mutableStateOf(false) }
    val expandedFaculties = viewModel.expandedFaculties
    val selectedFacultiesText = viewModel.selectedFacultiesText
    val faculties = viewModel.state.value.FacultiesState ?: emptyList()
    val years = mutableListOf<String>()
    val expandedYears = viewModel.expandedYears
    val selectedYearsText = viewModel.selectedYearsText
    years.add("Любой")
    for (n in 1..6) {
        years.add(n.toString())
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { androidx.compose.material3.Text(text = "Студенты", fontSize = 20.sp) }
            )
        }
    ) {
        val lazyListState = rememberLazyListState()
        LaunchedEffect(lazyListState) {
            snapshotFlow {
                lazyListState.firstVisibleItemIndex
            }
                .debounce(200L)
                .collectLatest {
                    currentInd.value = it
                }
        }
        if (currentInd.value >= currentPage.value / 10 && (currentPage.value < (viewModel.Sstate.value.StudentsState?.totalPages
                ?: 1))
        ) {
            bufRequest.currentPage = ++currentPage.value
            viewModel.getprofiles(
                bufRequest
            )
        }
        LazyColumn(
            state = lazyListState,
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            item {
                OutlinedTextField(
                    value = lastName.value,
                    shape = MaterialTheme.shapes.large,
                    onValueChange = { newText ->
                        lastName.value = newText
                        bufRequest.lastName = lastName.value
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    keyboardActions = KeyboardActions(onDone = {
                        viewModel.pageList.clear()
                        currentPage.value = 1
                        viewModel.getprofiles(bufRequest)
                    }),
                    singleLine = true,
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.Search,
                            contentDescription = "Search"
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 10.dp)
                )
                Button(
                    onClick = { openDialog.value = true },
                    modifier = Modifier.padding(start = 10.dp),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Row() {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_filter_alt_24),
                            modifier = Modifier
                                .size(18.dp)
                                .padding(end = 5.dp),
                            contentDescription = "sdcscds",
                        )
                        Text(text = "Фильтр")
                    }
                }
            }
            if (res.value.isNotEmpty()) {
                res.value.onEach {
                    items(it.cvs) { cv ->
                        StudentItem(item = cv)
                    }
                }
            } else if (currentPage.value < (viewModel.Sstate.value.StudentsState?.totalPages
                    ?: 1)
            ) {
                bufRequest.currentPage = ++currentPage.value
                viewModel.getprofiles(bufRequest)
            }
            if (viewModel.Sstate.value.isLoading) {
                item {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center).padding(10.dp))
                    }
                }
            }
        }
        if (openDialog.value) {
            AlertDialog(
                onDismissRequest = {
                    openDialog.value = false
                },
                title = {
                    Text(
                        text = "Фильтр",
                        color = MaterialTheme.colorScheme.inverseSurface,
                        fontSize = 25.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                text = {
                    Column() {
                        ExposedDropdownMenuBox(
                            expanded = expandedFaculties.value,
                            onExpandedChange = {
                                expandedFaculties.value = !expandedFaculties.value
                            }, modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            OutlinedTextField(
                                readOnly = true,
                                modifier = Modifier
                                    .menuAnchor()
                                    .fillMaxWidth(),
                                value = selectedFacultiesText.value,
                                onValueChange = { },
                                label = { androidx.compose.material3.Text("Факультет") },
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                                    textColor = MaterialTheme.colorScheme.inverseSurface
                                ),
                            )
                            ExposedDropdownMenu(
                                expanded = expandedFaculties.value,
                                onDismissRequest = {
                                    expandedFaculties.value = false
                                }
                                // modifier = Modifier.fillMaxWidth()
                            ) {
                                DropdownMenuItem(
                                    onClick = {
                                        bufRequest.faculties = emptyList()
                                        expandedFaculties.value = false
                                        selectedFacultiesText.value = "Любой"
                                        viewModel.pageList.clear()
                                        currentPage.value = 1
                                        viewModel.getprofiles(bufRequest)
                                    },
                                    text = { androidx.compose.material3.Text(text = "Любой") },
                                )
                                faculties.forEach { selectionOption ->
                                    DropdownMenuItem(
                                        onClick = {
                                            bufRequest.faculties = listOf(selectionOption.id)
                                            expandedFaculties.value = false
                                            selectedFacultiesText.value = selectionOption.text
                                            viewModel.pageList.clear()
                                            currentPage.value = 1
                                            viewModel.getprofiles(bufRequest)
                                        },
                                        text = { androidx.compose.material3.Text(text = selectionOption.text) },
                                    )
                                }
                            }
                        }
                        ExposedDropdownMenuBox(
                            expanded = expandedYears.value,
                            onExpandedChange = {
                                expandedYears.value = !expandedYears.value
                            },
                        ) {
                            OutlinedTextField(
                                readOnly = true,
                                modifier = Modifier
                                    .menuAnchor()
                                    .width(150.dp)
                                    .padding(end = 5.dp),
                                value = selectedYearsText.value,
                                onValueChange = { },
                                label = { androidx.compose.material3.Text("Курс") },
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                                    textColor = MaterialTheme.colorScheme.inverseSurface
                                ),
                            )
                            ExposedDropdownMenu(
                                expanded = expandedYears.value,
                                onDismissRequest = {
                                    expandedYears.value = false
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                years.forEach { selectionOption ->
                                    DropdownMenuItem(
                                        onClick = {
                                            if (selectionOption == "Любой"){
                                                bufRequest.course = emptyList()
                                            }
                                            else{
                                                bufRequest.course = listOf(selectionOption)
                                            }
                                            selectedYearsText.value = selectionOption
                                            expandedYears.value = false
                                        },
                                        text = {
                                            androidx.compose.material3.Text(
                                                text = selectionOption,
                                                modifier = Modifier.fillMaxWidth()
                                            )
                                        },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }
                        }
                        val checkedState = remember { mutableStateOf(bufRequest.searchJob) }
                        Row(modifier = Modifier.padding(top = 5.dp)) {
                            Text(
                                text = "Ищет работу",
                                modifier = Modifier.align(Alignment.CenterVertically),
                                color = MaterialTheme.colorScheme.inverseSurface,
                                fontSize = 15.sp
                            )
                            Switch(
                                modifier = Modifier.padding(start = 10.dp),
                                checked = checkedState.value,
                                onCheckedChange = {
                                    checkedState.value = it
                                    bufRequest.searchJob = it
                                }
                            )
                        }

                    }
                },
                confirmButton = {
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = MaterialTheme.colorScheme.inverseSurface
                        ),
                        onClick = {
                            openDialog.value = false
                            viewModel.pageList.clear()
                            currentPage.value = 1
                            viewModel.getprofiles(bufRequest)
                        }) {
                        androidx.compose.material3.Text("Применить")
                    }
                }
            )
        }
    }
}
