package by.g_alex.mobile_iis.presentation.login_screen.restore_password_screen.enter_login

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun RestorePasswordEnterLogin(
    viewModel: RestorePasswordEnterLoginViewModel = hiltViewModel(),
    navController: NavController
) {

    val state = viewModel.state.value
    val loginText = remember { mutableStateOf(TextFieldValue()) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val cnt = LocalContext.current

    LaunchedEffect(state) {
        if (state.information != null) {
            val jsonData = Json.encodeToString(state.information)
            //Log.e("~~~", jsonData.toString())
           navController.navigate("restorePasswordSelect/${jsonData}/${loginText.value.text}")
        }
        if(state.error.isNotBlank()) {
            Toast.makeText(cnt, "Не найдено", Toast.LENGTH_LONG).show()
        }
    }


    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier
                .fillMaxWidth(1.0f)
                .wrapContentHeight()
        ) {

            Text(
                text = "Восстановление пароля",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 30.sp
            )
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = "Введите логин от аккаунта",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 18.sp
            )

            OutlinedTextField(
                value = loginText.value,
                label = { Text(text = "Логин") },
                onValueChange = { loginText.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(60.dp, 5.dp, 60.dp, 5.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                        viewModel.restorePasswordEnterLogin(loginText.value.text)
                    }
                )
            )
            Button(
                onClick = {
                    keyboardController?.hide()
                    viewModel.restorePasswordEnterLogin(loginText.value.text)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(60.dp, 5.dp, 60.dp, 5.dp)
            ) {
                if (state.isLoading)
                    Text(text = "Загрузка...")
                else
                    Text(text = "Продолжить")
            }
        }
    }
}