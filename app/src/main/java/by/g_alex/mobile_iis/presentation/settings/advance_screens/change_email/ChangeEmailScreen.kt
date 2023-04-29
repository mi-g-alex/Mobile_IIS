package by.g_alex.mobile_iis.presentation.settings.advance_screens.change_email

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@Composable
fun ChangeEmailScreen(
    navController: NavController,
    id: Int,
    viewModel: ChangeEmailViewModel = hiltViewModel()
) {

    val emailText = remember { mutableStateOf(TextFieldValue("")) }
    val codeText = remember { mutableStateOf(TextFieldValue("")) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val countOfTry = remember { mutableStateOf(0) }
    val cnt = LocalContext.current
    val state = viewModel.state

    val ticks = remember { mutableStateOf(0) }
    LaunchedEffect(ticks.value) {
        while (ticks.value > 0) {
            delay(1.seconds)
            ticks.value--
        }
    }

    LaunchedEffect(state.value) {
        if (viewModel.state.value.error == "200") {
            navController.navigateUp()
        }
        countOfTry.value = viewModel.count.value
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Изменить почту")
                },
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column {
                OutlinedTextField(
                    value = emailText.value,
                    label = { Text(text = viewModel.email.value) },
                    onValueChange = { emailText.value = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(60.dp, 5.dp, 60.dp, 5.dp),
                    enabled = viewModel.state.value.information == null,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    OutlinedTextField(
                        value = codeText.value,
                        label = { Text(text = "Код") },
                        onValueChange = { codeText.value = it },
                        modifier = Modifier
                            .padding(60.dp, 5.dp, 5.dp, 5.dp)
                            .weight(0.5f),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                keyboardController?.hide()
                            }
                        ),
                    )

                    Button(
                        onClick = {
                            ticks.value = 90
                            countOfTry.value
                            viewModel.editEmailGetCode(id, emailText.value.text)
                        },
                        modifier = Modifier
                            .padding(end = 60.dp)
                            .align(Alignment.CenterVertically)
                            .weight(0.5f),
                        enabled = ticks.value == 0 && countOfTry.value != 0,
                    ) {
                        if (ticks.value == 0) Text("Получить\nкод", textAlign = TextAlign.Center)
                        else Text("${ticks.value}c.", textAlign = TextAlign.Center)
                    }
                }

                Text(
                    "Осталось попыток ${countOfTry.value}",
                    modifier = Modifier.padding(horizontal = 60.dp)
                )

                val reg = ("^[A-Za-z0-9._%+-]+@[A-Za-z0-9-]+.+.[A-Za-z]{2,4}$")
                val regex = Regex(reg)

                Button(
                    onClick = {
                        viewModel.confirmEmailCode(id, codeText.value.text)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp, horizontal = 60.dp),
                    enabled = emailText.value.text != "" && codeText.value.text.length == 6 && regex.matches(
                        emailText.value.text
                    ) && ticks.value != 0
                ) {
                    if (!regex.matches(emailText.value.text)) {
                        Text(text = "Неправильный формат почты")
                    } else {
                        if (emailText.value.text == "" || codeText.value.text.length != 6) {
                            Text(text = "Заполните все поля")
                        } else if (ticks.value == 0) {
                            Text(text = "Код не действителен")
                        } else {
                            Text(text = "Сохранить почту")
                        }
                    }
                }
            }
        }
    }
}