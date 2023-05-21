package by.g_alex.mobile_iis.presentation.study_screen.add_certificates

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import by.g_alex.mobile_iis.data.remote.dto.study.CertificatePlacesDto

@Composable
fun AddCertificateScreen(
    navController: NavController,
    viewModel: AddCertificateViewModel = hiltViewModel()
) {

    val checkString = listOf<String>("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
    val commentText = viewModel.commentText

    if ((viewModel.selectedPlace.value?.type ?: -1) > 0) {
        viewModel.isHerb.value = true
    }

    LaunchedEffect(viewModel.sendState.value) {
        if (viewModel.sendState.value.success == true) {
            navController.navigate("studyHome")
            navController.backQueue.remove(navController.previousBackStackEntry)
            navController.backQueue.remove(navController.previousBackStackEntry)
        }
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Заказать справку")
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
            if (viewModel.state.value.listOfItems != null)
                LazyColumn {
                    item {
                        Spacer(modifier = Modifier.height(10.dp))
                        MenuBox(a = viewModel.state.value.listOfItems!!, viewModel = viewModel)
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
                                modifier = Modifier
                                    .weight(1f)
                            ) {
                                Text(
                                    modifier = Modifier,
                                    text = "Гербовая печать",
                                    fontSize = 25.sp
                                )
                                Text(
                                    modifier = Modifier,
                                    text = "Иначе будет обычная.",
                                    fontSize = 15.sp
                                )
                            }
                            if ((viewModel.selectedPlace.value?.type ?: 0) > 0) {
                                Icon(
                                    Icons.Outlined.Lock,
                                    null
                                )
                            }
                            Switch(
                                modifier = Modifier.padding(start = 10.dp),
                                checked = viewModel.isHerb.value,
                                onCheckedChange = {
                                    if ((viewModel.selectedPlace.value?.type ?: 0) == 0)
                                        viewModel.isHerb.value = it
                                },
                                //enabled = (viewModel.selectedPlace.value?.type ?: -1) == 0
                            )
                        }
                    }

                    item {
                        OutlinedTextField(
                            value = commentText.value,
                            shape = MaterialTheme.shapes.large,
                            onValueChange = { newText ->
                                commentText.value = newText
                            },
                            label = { Text(text = "Комментарий") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp),
                            enabled = (viewModel.selectedPlace.value?.type ?: -1) == 0
                        )
                    }

                    item {
                        OutlinedTextField(
                            value = viewModel.numberText.value,
                            shape = MaterialTheme.shapes.large,
                            onValueChange = { newText ->
                                viewModel.numberText.value = newText
                            },
                            label = { Text(text = "Количество (1-10)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp)
                        )
                    }

                    item {
                        OutlinedButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp),
                            onClick = {
                                viewModel.sendCertificate()
                            },
                            enabled = !viewModel.sendState.value.isLoading && viewModel.numberText.value in checkString && viewModel.selectedText.value.isNotEmpty()
                        ) {
                            if (viewModel.numberText.value !in checkString) {
                                Text("Кол-во не от 1 до 10!")
                            } else
                                if (viewModel.sendState.value.success == null && !viewModel.sendState.value.isLoading && viewModel.sendState.value.error.isBlank())
                                    Text("Заказать")
                                else if (viewModel.sendState.value.isLoading)
                                    Text("Загрузка...")
                                else if (viewModel.sendState.value.isLoading)
                                    Text("Ошибка. Повторите попытку")
                        }
                    }
                }

            if (viewModel.state.value.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            if (viewModel.state.value.error.isNotBlank()) {
                Box(modifier = Modifier.align(Alignment.Center)) {
                    Column(modifier = Modifier) {
                        Text(
                            text = viewModel.state.value.error,
                            fontSize = 25.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MenuBox(a: List<CertificatePlacesDto>, viewModel: AddCertificateViewModel) {
    val expandedPlaces = viewModel.expandedPlaces
    val selectedPlace = viewModel.selectedPlace
    val selectedText = viewModel.selectedText
    ExposedDropdownMenuBox(
        expanded = expandedPlaces.value,
        onExpandedChange = {
            expandedPlaces.value = !expandedPlaces.value
        }, modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    ) {
        OutlinedTextField(
            readOnly = true,
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            value = selectedText.value,
            onValueChange = { },
            label = { Text("Место") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = MaterialTheme.colorScheme.primary,
           //     textColor = MaterialTheme.colorScheme.inverseSurface
            ),
        )
        ExposedDropdownMenu(
            expanded = expandedPlaces.value,
            onDismissRequest = {
                expandedPlaces.value = false
            }
        ) {
            DropdownMenuItem(
                onClick = {
                    expandedPlaces.value = false
                    selectedPlace.value = null
                },
                text = { Text(text = "") },
            )
            a.forEach { b ->
                Text(b.type.toString(), Modifier.padding(horizontal = 10.dp))
                Divider(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .height(1.dp)
                        .fillMaxWidth(), color = MaterialTheme.colorScheme.outline
                )
                b.places.forEach { selectionOption ->
                    DropdownMenuItem(
                        onClick = {
                            selectedPlace.value = selectionOption
                            expandedPlaces.value = false
                            selectedText.value = selectionOption.name.toString()
                            Log.e("~~~", selectionOption.toString())
                        },
                        text = { Text(text = selectionOption.name.toString()) },
                    )
                }
            }
        }
    }
}