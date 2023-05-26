package by.g_alex.mobile_iis.presentation.settings.advance_screens.change_password

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import by.g_alex.mobile_iis.R

@Composable
fun ChangePasswordDialog(
    setShowDialog: (Boolean) -> Unit,
    viewModel: ChangePasswordViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val inputOld = remember { mutableStateOf(TextFieldValue("")) }
    val inputNew = remember { mutableStateOf(TextFieldValue("")) }
    val inputNew1 = remember { mutableStateOf(TextFieldValue("")) }

    val reg = ("^[0-9A-Za-z@#\$%^&+=_]{8,}\$")
    val regex = Regex(reg)
    val matches = regex.matches(inputNew.value.text) && inputNew.value.text.isNotBlank()

    val showPasswordState = remember {
        mutableStateOf(false)
    }


    LaunchedEffect(state.information) {
        if (state.information == 200) {
            setShowDialog(false)
        }
    }

    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface(
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalAlignment = CenterHorizontally
            ) {
                OutlinedTextField(
                    value = inputOld.value,
                    label = { Text(text = "Старый пароль") },
                    onValueChange = { inputOld.value = it },
                    modifier = Modifier
                        .fillMaxWidth(),
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
                    value = inputNew.value,
                    label = { Text(text = "Новый пароль") },
                    onValueChange = { inputNew.value = it },
                    modifier = Modifier
                        .fillMaxWidth(),
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
                    value = inputNew1.value,
                    label = { Text(text = "Повторите новый пароль") },
                    onValueChange = { inputNew1.value = it },
                    modifier = Modifier
                        .fillMaxWidth(),
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
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    enabled = inputNew1.value.text == inputNew.value.text && !state.isLoading && inputNew1.value.text.length >= 8 && matches,
                    onClick = {
                        viewModel.changePass(inputOld.value.text, inputNew.value.text)
                    }) {
                    if (state.isLoading) {
                        Text("Сохранение...")
                    } else if (!matches) {
                        Text("Неправильный формат пароля")
                    } else if (inputNew1.value.text != inputNew.value.text) {
                        Text("Пароли не совпадают")
                    } else if (state.error.isNotBlank()) {
                        Text("Ошибка. Повторить попытку")
                    } else if (state.information == 409) {
                        Text("Неправильный пароль")
                    } else {
                        Text("Сохранить")
                    }
                }
            }
        }
    }
}