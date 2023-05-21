package by.g_alex.mobile_iis.presentation.diciplines_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import java.time.LocalDate

@Composable
fun DiciplinesScreen(
    viewModel: DiciplinesViewModel = hiltViewModel()
) {

    val years = mutableListOf<String>()
    val expandedYears = viewModel.expandedYears
    val selectedYearsText = viewModel.selectedYearsText

    val faculties = viewModel.state.value.FacultiesState ?: emptyList()
    val expandedFaculties = viewModel.expandedFaculties
    val selectedFacultiesText = viewModel.selectedFacultiesText


    val specialities = viewModel.spState.value.SpecialState ?: emptyList()
    val expandedSpecialities = viewModel.expandedSpecialities
    val selectedSpecialityText = viewModel.selectedSpecialityText

    val date = LocalDate.now()
    if (date.monthValue > 8) {
        for (n in 0..3) {
            years.add((date.year - n).toString())
        }
    } else {
        for (n in 1..4) {
            years.add((date.year - n).toString())
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { androidx.compose.material3.Text(text = "Дисциплины", fontSize = 20.sp) }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Row(modifier = Modifier.padding(horizontal = 10.dp)) {
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
                        label = { androidx.compose.material3.Text("Год") },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                            /*textColor = MaterialTheme.colorScheme.inverseSurface*/
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
                                    selectedYearsText.value = selectionOption
                                    expandedYears.value = false
                                    selectedFacultiesText.value = ""
                                    selectedSpecialityText.value = ""
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
                       //     textColor = MaterialTheme.colorScheme.inverseSurface
                        ),
                    )
                    ExposedDropdownMenu(
                        expanded = expandedFaculties.value,
                        onDismissRequest = {
                            expandedFaculties.value = false
                        }
                        // modifier = Modifier.fillMaxWidth()
                    ) {
                        faculties.forEach { selectionOption ->
                            DropdownMenuItem(
                                onClick = {
                                    selectedFacultiesText.value = selectionOption.text
                                    expandedFaculties.value = false
                                    viewModel.getSpecialities(
                                        selectionOption.id,
                                        selectedYearsText.value.toInt()
                                    )
                                    selectedSpecialityText.value = ""
                                },
                                text = { androidx.compose.material3.Text(text = selectionOption.text) },
                                //modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
            ExposedDropdownMenuBox(
                expanded = expandedSpecialities.value,
                onExpandedChange = {
                    expandedSpecialities.value = !expandedSpecialities.value
                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            ) {
                OutlinedTextField(
                    readOnly = true,
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    value = selectedSpecialityText.value,
                    onValueChange = { },
                    label = { androidx.compose.material3.Text("Специальность") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedBorderColor = MaterialTheme.colorScheme.primary,
               //         textColor = MaterialTheme.colorScheme.inverseSurface
                    ),
                )
                ExposedDropdownMenu(
                    expanded = expandedSpecialities.value,
                    onDismissRequest = {
                        expandedSpecialities.value = false
                    },
                    // modifier = Modifier.fillMaxWidth()
                ) {
                    specialities.forEach { selectionOption ->
                        DropdownMenuItem(
                            onClick = {
                                selectedSpecialityText.value = selectionOption.text
                                expandedSpecialities.value = false
                                viewModel.getDiciplines(year = selectedYearsText.value.toInt(),id = selectionOption.id)
                            },
                            text = { androidx.compose.material3.Text(text = selectionOption.text) },
                            //modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
            Box(modifier = Modifier.fillMaxSize()) {
                if (viewModel.disState.value.isLoading)
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                else{
                    DiciplineList(diciplines = viewModel.disState.value.diciplineState?: emptyList())
                }

            }

        }

    }
}