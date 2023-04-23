package by.g_alex.mobile_iis.presentation.login_screen.restore_password_screen.restore_end

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import by.g_alex.mobile_iis.data.remote.dto.login.RestorePasswordEnterLoginResponseDto
import by.g_alex.mobile_iis.presentation.login_screen.restore_password_screen.select_how_restore.components.RestorePasswordSelectDialog
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@Composable
fun RestorePasswordEndScreen(
    navController: NavController,
    data: RestorePasswordEnterLoginResponseDto,
    login: String
) {

    val inputText = remember { mutableStateOf(TextFieldValue("")) }
    val inputText1 = remember { mutableStateOf(TextFieldValue("")) }
    val inputText2 = remember { mutableStateOf(TextFieldValue("")) }
    val countOfTry = remember { mutableStateOf(data.remainingAttempts) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val cnt = LocalContext.current

    val ticks = remember { mutableStateOf(0) }
    LaunchedEffect(ticks.value) {
        while (ticks.value > 0) {
            delay(1.seconds)
            ticks.value--
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
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                visualTransformation = PasswordVisualTransformation()
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
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                visualTransformation = PasswordVisualTransformation()
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
                        ticks.value = 120
                        countOfTry.value--
                    },
                    modifier = Modifier.padding(end = 60.dp).align(CenterVertically).weight(0.5f),
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

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 60.dp),
                enabled = inputText.value.text != "" && inputText1.value.text != "" && inputText.value.text == inputText1.value.text && inputText2.value.text.length == 6
            ) {
                if (inputText.value.text != "" && inputText1.value.text != "" && inputText.value.text != inputText1.value.text) {
                    Text(text = "Пароли не совпадают")
                } else
                    if (inputText.value.text == "" || inputText1.value.text == "" || inputText2.value.text.length != 6) {
                        Text(text = "Заполните все поля")
                    } else {
                        Text(text = "Восстановить пароль")
                    }
            }
        }
    }
}