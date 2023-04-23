package by.g_alex.mobile_iis.presentation.login_screen.restore_password_screen.select_how_restore

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import by.g_alex.mobile_iis.data.remote.dto.login.RestorePasswordEnterLoginResponseDto
import by.g_alex.mobile_iis.presentation.login_screen.restore_password_screen.select_how_restore.components.RestorePasswordSelectDialog

@Composable
fun RestorePasswordSelect(
    navController: NavController,
    data: RestorePasswordEnterLoginResponseDto,
    login: String
) {
    val showDialog = remember { mutableStateOf(false) }
    var whatNeedToInputId = remember {
        mutableStateOf(0)
    }
    var value = remember {
        mutableStateOf("")
    }
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
                            .clickable {
                                whatNeedToInputId.value = it[i]?.contactTypeId ?: 0
                                value.value = it[i]?.contactValue ?: ""
                                showDialog.value = true
                            }
                    ) {
                        Column(Modifier.padding(10.dp)) {
                            var s = ""
                            when (it[i]?.contactTypeId) {
                                4 -> s = "Номер телефона"
                                6 -> s = "Почта"
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
    if (showDialog.value)
        RestorePasswordSelectDialog(
            setShowDialog = {
                showDialog.value = it
            },
            whatNeedToInputId.value,
            value.value,
            login,
            navController
        )
}