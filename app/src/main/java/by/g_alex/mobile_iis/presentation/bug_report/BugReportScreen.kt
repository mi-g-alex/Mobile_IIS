package by.g_alex.mobile_iis.presentation.bug_report

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun BugReportScreen() {
    val fioText = remember { mutableStateOf("") }
    val emailText = remember { mutableStateOf("") }
    val studentIdText = remember { mutableStateOf("") }
    val groupText = remember { mutableStateOf("") }
    val problemText = remember { mutableStateOf("") }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Баг Репорт")
                },
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = fioText.value,
                shape = MaterialTheme.shapes.large,
                onValueChange = { newText ->
                    fioText.value = newText
                },
                label = {Text(text = "ФИО")},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 10.dp)

            )
        OutlinedTextField(
                value = emailText.value,
                shape = MaterialTheme.shapes.large,
                onValueChange = { newText ->
                    emailText.value = newText
                },
            label = {Text(text = "Почта")},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 10.dp)

            )
            OutlinedTextField(
                value = studentIdText.value,
                shape = MaterialTheme.shapes.large,
                onValueChange = { newText ->
                    studentIdText.value = newText
                },
                label = {Text(text = "Студак")},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 10.dp)

            )
            OutlinedTextField(
                value = groupText.value,
                shape = MaterialTheme.shapes.large,
                onValueChange = { newText ->
                    groupText.value = newText
                },
                label = {Text(text = "Группа")},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 10.dp)

            )
            OutlinedTextField(
                value = problemText.value,
                shape = MaterialTheme.shapes.large,
                onValueChange = { newText ->
                    problemText.value = newText
                },
                label = {Text(text = "Проблема")},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 10.dp)

            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                onClick = {
                    problemText.value = ""
                },
                enabled = fioText.value.isNotBlank() &&
                        emailText.value.isNotBlank() &&
                        groupText.value.isNotBlank() &&
                        studentIdText.value.isNotBlank() &&
                        problemText.value.isNotBlank()
            ) {
                Text(text = "Отправить")
            }
        }
    }
}
