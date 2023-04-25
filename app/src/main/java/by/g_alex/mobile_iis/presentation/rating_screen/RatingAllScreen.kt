package by.g_alex.mobile_iis.presentation.rating_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.ExperimentalMaterialApi
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import java.time.LocalDate

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RatingAllScreen(
    viewModel: RatingAllViewModel = hiltViewModel(),
    navController: NavController
) {
    val years = mutableListOf<String>()
    val expandedYears = remember { mutableStateOf(false) }
    val selectedYearsText = remember { mutableStateOf("2022") }

    val faculties = viewModel.state.value.FacultiesState?: emptyList()
    val expandedFaculties = remember { mutableStateOf(false) }
    val selectedFacultiesText = remember { mutableStateOf("") }


    val specialities = viewModel.spState.value.SpecialState?: emptyList()
    val expandedSpecialities = remember { mutableStateOf(false) }
    val selectedSpecialityText = remember { mutableStateOf("") }


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
                title = { Text(text = "Рейтинг", fontSize = 20.sp) }
            )
        }
    ) {
        Column(modifier = Modifier.padding(it).fillMaxSize() ) {
            Row(modifier = Modifier.padding(horizontal = 10.dp)) {
                ExposedDropdownMenuBox(
                    expanded = expandedYears.value,
                    onExpandedChange = {
                        expandedYears.value = !expandedYears.value
                    },//.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        readOnly = true,
                        modifier = Modifier
                            .menuAnchor()
                            .width(150.dp).padding(end = 5.dp),//.fillMaxWidth(),
                        value = selectedYearsText.value,
                        onValueChange = { },
                        label = { Text("Год") },
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
                                    selectedYearsText.value = selectionOption
                                    expandedYears.value = false
                                },
                                text = { Text(text = selectionOption, modifier = Modifier.fillMaxWidth()) },
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
                        label = { Text("Факультет") },
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
                        faculties.forEach { selectionOption ->
                            DropdownMenuItem(
                                onClick = {
                                    selectedFacultiesText.value = selectionOption.text
                                    expandedFaculties.value = false
                                    viewModel.getSpecialities(selectionOption.id,selectedYearsText.value.toInt())
                                },
                                text = { Text(text = selectionOption.text) },
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
                    .fillMaxWidth().padding(horizontal = 10.dp)
            ) {
                OutlinedTextField(
                    readOnly = true,
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    value = selectedSpecialityText.value,
                    onValueChange = { },
                    label = { Text("Специальность") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                        textColor = MaterialTheme.colorScheme.inverseSurface
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
                                viewModel.getRating(year = selectedYearsText.value.toInt(),id = selectionOption.id)
                            },
                            text = { Text(text = selectionOption.text) },
                            //modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
            Box(modifier = Modifier.fillMaxSize()) {
                if (viewModel.rState.value.isLoading)
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                RatingColumn(students = viewModel.rState.value.RatingState?.sortedByDescending {it.average}, navController = navController)
            }

        }

    }
}