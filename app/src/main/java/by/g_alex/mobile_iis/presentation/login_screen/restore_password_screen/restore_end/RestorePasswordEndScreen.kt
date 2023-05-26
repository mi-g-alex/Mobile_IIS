package by.g_alex.mobile_iis.presentation.login_screen.restore_password_screen.restore_end

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import by.g_alex.mobile_iis.R
import by.g_alex.mobile_iis.data.remote.dto.login.RestorePasswordEnterLoginResponseDto
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@Composable
fun RestorePasswordEndScreen(
    navController: NavController,
    data: RestorePasswordEnterLoginResponseDto,
    login: String,
    viewModel: RestorePasswordEndViewModel = hiltViewModel()
) {
    val showPasswordState = remember {
        mutableStateOf(false)
    }

    val inputText = remember { mutableStateOf(TextFieldValue("")) }
    val inputText1 = remember { mutableStateOf(TextFieldValue("")) }
    val inputText2 = remember { mutableStateOf(TextFieldValue("")) }
    val countOfTry = remember { mutableStateOf(data.remainingAttempts) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val cnt = LocalContext.current

    val state = viewModel.state

    val ticks = remember { mutableStateOf(0) }
    LaunchedEffect(ticks.value) {
        while (ticks.value > 0) {
            delay(1.seconds)
            ticks.value--
        }
    }

    val reg = ("^[0-9A-Za-z@#\$%^&+=_]{8,}\$")
    val regex = Regex(reg)
    val matches = regex.matches(inputText.value.text) && inputText.value.text.isNotBlank()


    LaunchedEffect(state.value.information) {
        Log.e("~~~~!", state.value.information.toString())
        if (state.value.information == false) {
            Toast.makeText(cnt, "Error", Toast.LENGTH_LONG).show()
        } else if (state.value.information == true) {
            Log.e("~~~", "ALL GOOD")
            navController.navigate("login")
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            OutlinedTextField(
                value = inputText.value,
                label = { Text(text = "Пароль") },
                onValueChange = { inputText.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(60.dp, 5.dp, 60.dp, 5.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                trailingIcon = {
                    if (!showPasswordState.value) {
                        Icon(
                            painterResource(id = R.drawable.hide_password_icon),
                            "Посмотреть пароль",
                            Modifier.clickable {
                                showPasswordState.value = true
                            }
                        )
                    }
                    if (showPasswordState.value) {
                        Icon(
                            painterResource(id = R.drawable.show_password_icon),
                            "Спрятать пароль",
                            Modifier.clickable {
                                showPasswordState.value = false
                            }
                        )
                    }
                },
                visualTransformation = if (!showPasswordState.value) PasswordVisualTransformation() else VisualTransformation.None
            )
            OutlinedTextField(
                value = inputText1.value,
                label = { Text(text = "Повторите пароль") },
                onValueChange = { inputText1.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(60.dp, 5.dp, 60.dp, 5.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                trailingIcon = {
                    if (!showPasswordState.value) {
                        Icon(
                            painterResource(id = R.drawable.hide_password_icon),
                            "Посмотреть пароль",
                            Modifier.clickable {
                                showPasswordState.value = true
                            }
                        )
                    }
                    if (showPasswordState.value) {
                        Icon(
                            painterResource(id = R.drawable.show_password_icon),
                            "Спрятать пароль",
                            Modifier.clickable {
                                showPasswordState.value = false
                            }
                        )
                    }
                },
                visualTransformation = if (!showPasswordState.value) PasswordVisualTransformation() else VisualTransformation.None
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = CenterVertically
            ) {

                OutlinedTextField(
                    value = inputText2.value,
                    label = { Text(text = "Код") },
                    onValueChange = { inputText2.value = it },
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
                        countOfTry.value--
                        data.contacts?.get(0)?.contactValue?.let {
                            viewModel.restorePasswordGetCode(
                                login,
                                it
                            )
                        }
                    },
                    modifier = Modifier
                        .padding(end = 60.dp)
                        .align(CenterVertically)
                        .weight(0.5f),
                    enabled = ticks.value == 0,
                ) {
                    if (ticks.value == 0) Text("Получить\nкод", textAlign = TextAlign.Center)
                    else Text("${ticks.value}c.", textAlign = TextAlign.Center)
                }
            }
            Text(
                "Осталось попыток ${countOfTry.value}",
                modifier = Modifier.padding(horizontal = 60.dp)
            )
            Button(
                onClick = {
                    data.contacts?.get(0)?.contactValue?.let {
                        viewModel.restorePasswordApply(
                            login, inputText.value.text,
                            it, inputText2.value.text
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 60.dp),
                enabled = inputText.value.text != "" && inputText1.value.text != "" && inputText.value.text == inputText1.value.text && inputText2.value.text.length == 6 && matches
            ) {
                if (!matches) {
                    Text("Неправильный формат пароля")
                } else if (inputText.value.text != "" && inputText1.value.text != "" && inputText.value.text != inputText1.value.text) {
                    Text(text = "Пароли не совпадают")
                } else if (inputText.value.text == "" || inputText1.value.text == "" || inputText2.value.text.length != 6) {
                    Text(text = "Заполните все поля")
                } else {
                    Text(text = "Восстановить пароль")
                }
            }
        }
    }
}