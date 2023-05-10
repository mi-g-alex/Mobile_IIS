package by.g_alex.mobile_iis.presentation.departments.employee_info

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun DepartmentsEmployeesInfoScreen(
    id: String,
    viewModel: DepartmentsEmployeesInfoViewModel = hiltViewModel()
) {

    val state = viewModel.state.value
    remember { mutableStateOf(TextFieldValue("")) }

    remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {
        viewModel.getEmployees(id)
    }


    Scaffold(
        topBar = {
            if (state.employeesState != null)
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = state.employeesState.lastName.toString(),
                            fontSize = 20.sp
                        )
                    }
                )
        }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            if (state.employeesState != null) {
                LazyColumn {

                    item() {
                        Text(text = state.employeesState.toString())
                    }
                }
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
                }
            }

            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}
