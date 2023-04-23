package by.g_alex.mobile_iis.presentation.login_screen.restore_password_screen.select_how_restore

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import by.g_alex.mobile_iis.data.remote.dto.login.RestorePasswordEnterLoginResponseDto

@Composable
fun RestorePasswordSelect(
    viewModel: RestorePasswordEnterLoginViewModel = hiltViewModel(),
    navController: NavController,
    data: RestorePasswordEnterLoginResponseDto
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        LazyColumn {
            item {
                Text(
                    text = "Выберите метод восстановления",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
                )
            }
            data.contacts?.let {
                items(it.size) { i ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                    ) {
                        Column(Modifier.padding(10.dp)) {
                            var s: String = ""
                            when (it[i]?.contactTypeId) {
                                4 -> s = "Номер телефона"
                                6 -> s = "Почта"
                                else -> "Другое"
                            }
                            Text(
                                text = s,
                                fontSize = 20.sp,
                                modifier = Modifier.fillMaxWidth()
                            )
                            it[i]?.contactValue?.let { it1 ->
                                Text(
                                    text = it1,
                                    fontSize = 20.sp,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}