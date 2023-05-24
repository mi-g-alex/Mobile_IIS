package by.g_alex.mobile_iis.presentation.departments.employees_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DepartmentsEmployeesListScreen(
    navController: NavController,
    id: Int,
    name: String,
    viewModel: DepartmentsEmployeesListViewModel = hiltViewModel()
) {

    val state = viewModel.state.value
    val searchText = remember { mutableStateOf(TextFieldValue("")) }

    remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {
        viewModel.getEmployees(id)
    }


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = name, fontSize = 20.sp) }
            )
        }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            if (state.employeesState?.isNotEmpty() == true) {
                LazyColumn {
                    stickyHeader {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.background),
                        ) {
                            OutlinedTextField(
                                value = searchText.value,
                                shape = MaterialTheme.shapes.large,
                                onValueChange = { newText ->
                                    searchText.value = newText
                                },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
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
                        }
                    }

                    items(state.employeesState.size) { i ->
                        if (state.employeesState[i].lastName != null && state.employeesState[i].firstName != null) {
                            if (
                                (state.employeesState[i].lastName.toString().lowercase() + " " +
                                        state.employeesState[i].firstName.toString()
                                            .lowercase() + " " +
                                        state.employeesState[i].middleName.toString().lowercase())
                                    .contains(searchText.value.text.lowercase()) ||
                                (state.employeesState[i].jobPositions.toString().lowercase())
                                    .contains(searchText.value.text.lowercase())
                            )
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight()
                                        .clickable {
                                            navController.navigate("departmentsEmployeeInfo/${state.employeesState[i].urlId.toString()}")
                                        }
                                        .padding(horizontal = 10.dp, vertical = 5.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.inverseOnSurface
                                    )
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .wrapContentHeight()
                                            .padding(10.dp)
                                    ) {
                                        Text(
                                            text = state.employeesState[i].lastName.toString() + " " +
                                                    state.employeesState[i].firstName.toString() + " " +
                                                    state.employeesState[i].middleName.toString(),
                                        )
                                    }
                                }
                        }
                    }
                }
            }

            if (state.employeesState!!.isEmpty() && !state.isLoading && state.error.isBlank()) {
                Text(text = "Тут никого нет(", fontSize = 25.sp, modifier = Modifier.align(Alignment.Center))
            }

            if (state.error.isNotBlank()) {
                if (state.error == "LessCookie") {
                    Box(modifier = Modifier.align(Alignment.Center)) {
                        Column(modifier = Modifier) {
                            Text(
                                text = "Сначала войдите в аккаунт...",
                                fontSize = 25.sp
                            )
                        }
                    }
                } else {
                    Box(modifier = Modifier.align(Alignment.Center)) {
                        Column(modifier = Modifier) {
                            Text(
                                text = "Ошибка подключения к серверу...",
                                fontSize = 25.sp
                            )
                        }
                    }
                }
            }

            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}
