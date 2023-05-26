package by.g_alex.mobile_iis.presentation.bug_report

import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun BugReportScreen(viewModel: BugRepostViewModel = hiltViewModel()) {
    val problemText = viewModel.text
    val tgText = viewModel.link

    val cnt = LocalContext.current

    LaunchedEffect(viewModel.needShowThanks.value) {
        if (viewModel.needShowThanks.value) {
            Toast.makeText(cnt, "Отзыв отпарвлен успешно", Toast.LENGTH_SHORT).show()
            viewModel.needShowThanks.value = false
            viewModel.isLoading.value = false
            viewModel.needShowErr.value = false
            problemText.value = ""
        }
    }

    LaunchedEffect(viewModel.needShowErr.value) {
        if (viewModel.needShowErr.value) {
            Toast.makeText(cnt, "Ошибка отправки", Toast.LENGTH_SHORT).show()
            viewModel.needShowThanks.value = false
            viewModel.isLoading.value = false
            viewModel.needShowErr.value = false
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Feedback")
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
                value = problemText.value,
                shape = MaterialTheme.shapes.large,
                onValueChange = { newText ->
                    problemText.value = newText
                },
                label = { Text(text = "Проблема / Предложение") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 10.dp)

            )

            val reg = ("^@[a-zA-Z]\\w{1,29}[0-9a-zA-Z_][a-zA-Z0-9]$")
            val regex = Regex(reg)
            val matches = regex.matches(tgText.value) || tgText.value.isEmpty()

            OutlinedTextField(
                value = tgText.value,
                shape = MaterialTheme.shapes.large,
                onValueChange = { newText ->
                    tgText.value = newText
                },
                label = { Text(text = "ТГ (@name), не обязательно") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 10.dp)

            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                onClick = {
                    var a = tgText.value
                    if (tgText.value.isNotBlank()) a = "t.me/" + tgText.value.removePrefix("@")
                    viewModel.sendFeedback(problemText.value, a)
                },
                enabled = problemText.value.isNotBlank() && !viewModel.isLoading.value && matches
            ) {
                if (viewModel.isLoading.value) Text(text = "Загрузка...")
                else Text(text = "Отправить")
            }
        }
    }
}
