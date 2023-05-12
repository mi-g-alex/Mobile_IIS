package by.g_alex.mobile_iis.presentation.login_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import by.g_alex.mobile_iis.R

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navController: NavController
) {

    val state = viewModel.state.value
    val loginText = remember { mutableStateOf(TextFieldValue()) }
    val passText = remember { mutableStateOf(TextFieldValue()) }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(state.cookie) {
        if (state.cookie != "" && state.cookie != null) {
            navController.navigate("profileHome")
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn(modifier = Modifier
            .fillMaxWidth()
            .align(Center)) {
            item {
                Column(horizontalAlignment = CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.logo_icon),
                        contentDescription = "",
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(200.dp),
                        contentScale = ContentScale.Crop,
                    )
                    OutlinedTextField(
                        value = loginText.value,
                        label = { Text(text = "Логин") },
                        onValueChange = { loginText.value = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(60.dp, 35.dp, 60.dp, 5.dp),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                    )

                    OutlinedTextField(
                        value = passText.value,
                        label = { Text(text = "Пароль") },
                        onValueChange = { passText.value = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(60.dp, 5.dp, 60.dp, 5.dp),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                keyboardController?.hide()
                                viewModel.loginToAccount(loginText.value.text, passText.value.text)
                            }
                        ),
                        visualTransformation = PasswordVisualTransformation()
                    )

                    Button(
                        onClick = {
                            keyboardController?.hide()
                            viewModel.loginToAccount(loginText.value.text, passText.value.text)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(60.dp, 5.dp, 60.dp, 5.dp)
                    ) {
                        if (state.isLoading)
                            Text(text = "Загрузка...")
                        else
                            Text(text = "Войти")
                    }
                    if (state.error.isNotBlank()) {
                        Text(
                            text = state.error
                        )
                    } else {
                        Text(
                            text = ""
                        )
                    }
                }
            }
        }
        Text(
            modifier = Modifier
                .align(BottomCenter)
                .padding(bottom = 15.dp)
                .clickable {
                    navController.navigate("restorePassword")
                },
            text = "Забыли пароль?"
        )
    }
}