package by.g_alex.mobile_iis.presentation.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import by.g_alex.mobile_iis.R
import by.g_alex.mobile_iis.presentation.settings.advance_screens.change_bio.ChangeBioDialog
import by.g_alex.mobile_iis.presentation.settings.advance_screens.change_password.ChangePasswordDialog
import by.g_alex.mobile_iis.presentation.settings.advance_screens.check_email.CheckEmailDialog

@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val showDialogBio = remember { mutableStateOf(false) }
    val showDialogPass = remember { mutableStateOf(false) }
    val showDialogEmail = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Настройки")
                },
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            if (state.allGood != null) LazyColumn(Modifier.padding(horizontal = 15.dp)) {
                item {
                    //Change Email
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate("changeEmail")
                            },
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .weight(1f)
                        ) {
                            Text(
                                modifier = Modifier,
                                text = "Почта",
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                fontSize = 25.sp
                            )
                            Text(
                                modifier = Modifier,
                                text = "Изменить почту, привязанную к аккаунту",
                                fontSize = 15.sp
                            )
                        }
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_navigate_next_24),
                            contentDescription = null
                        )
                    }
                }

                item {
                    Divider(
                        modifier = Modifier
                            .padding(5.dp)
                            .height(1.dp)
                            .fillMaxWidth(), color = MaterialTheme.colorScheme.outline
                    )
                }

                item {
                    //Change Password
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                showDialogPass.value = true
                            },
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .weight(1f)
                        ) {
                            Text(modifier = Modifier, text = "Пароль", fontSize = 25.sp)
                            Text(
                                modifier = Modifier,
                                text = "Поменять пароль от аккаунта",
                                fontSize = 15.sp
                            )
                        }
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_navigate_next_24),
                            contentDescription = null
                        )
                    }
                }

                item {
                    Divider(
                        modifier = Modifier
                            .padding(5.dp)
                            .height(1.dp)
                            .fillMaxWidth(), color = MaterialTheme.colorScheme.outline
                    )
                }

                item {
                    //Change Password
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .weight(1f)
                                .clickable {
                                    showDialogBio.value = true
                                }
                        ) {
                            Text(
                                modifier = Modifier,
                                text = "Основная информация",
                                fontSize = 25.sp
                            )
                            Text(
                                modifier = Modifier,
                                text = "Изменить основную информацию",
                                fontSize = 15.sp
                            )
                        }
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_navigate_next_24),
                            contentDescription = null
                        )
                    }
                }

                item {
                    Divider(
                        modifier = Modifier
                            .padding(5.dp)
                            .height(1.dp)
                            .fillMaxWidth(), color = MaterialTheme.colorScheme.outline
                    )
                }

                item {
                    //Change Password
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(route = "changeSkills")
                            },
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .weight(1f)
                        ) {
                            Text(modifier = Modifier, text = "Навыки", fontSize = 25.sp)
                            Text(
                                modifier = Modifier,
                                text = "Изменить свои \"Навыки\"",
                                fontSize = 15.sp
                            )
                        }
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_navigate_next_24),
                            contentDescription = null
                        )
                    }
                }

                item {
                    Divider(
                        modifier = Modifier
                            .padding(5.dp)
                            .height(1.dp)
                            .fillMaxWidth(), color = MaterialTheme.colorScheme.outline
                    )
                }

                item {
                    //Change Password
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(route = "changeLinks")
                            },
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .weight(1f)
                        ) {
                            Text(modifier = Modifier, text = "Ссылки", fontSize = 25.sp)
                            Text(
                                modifier = Modifier,
                                text = "Изменить ссылки",
                                fontSize = 15.sp
                            )
                        }
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_navigate_next_24),
                            contentDescription = null
                        )
                    }
                }

                item {
                    Divider(
                        modifier = Modifier
                            .padding(5.dp)
                            .height(1.dp)
                            .fillMaxWidth(), color = MaterialTheme.colorScheme.outline
                    )
                }

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .weight(1f)
                        ) {
                            Text(modifier = Modifier, text = "Рейтинг", fontSize = 25.sp)
                            Text(
                                modifier = Modifier,
                                text = "Кол-во звёзд в \"Студенты\"",
                                fontSize = 15.sp
                            )
                        }
                        Switch(checked = viewModel.rateCheck.value, onCheckedChange = {
                            viewModel.putRating()
                        })
                    }
                }

                item {
                    Divider(
                        modifier = Modifier
                            .padding(5.dp)
                            .height(1.dp)
                            .fillMaxWidth(), color = MaterialTheme.colorScheme.outline
                    )
                }

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .weight(1f)
                        ) {
                            Text(modifier = Modifier, text = "Профиль", fontSize = 25.sp)
                            Text(
                                modifier = Modifier,
                                text = "Отображение профиля в \"Студенты\"",
                                fontSize = 15.sp
                            )
                        }
                        Switch(checked = viewModel.pubCheck.value, onCheckedChange = {
                            viewModel.putPublished()
                        })
                    }
                }

                item {
                    Divider(
                        modifier = Modifier
                            .padding(5.dp)
                            .height(1.dp)
                            .fillMaxWidth(), color = MaterialTheme.colorScheme.outline
                    )
                }

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .weight(1f)
                        ) {
                            Text(modifier = Modifier, text = "Поиск работы", fontSize = 25.sp)
                            Text(
                                modifier = Modifier,
                                text = "Для фильтра \"В поисках работы\"",
                                fontSize = 15.sp
                            )
                        }
                        Switch(checked = viewModel.jobCheck.value, onCheckedChange = {
                            viewModel.putSearchJob()
                        })
                    }
                }

                item {
                    Divider(
                        modifier = Modifier
                            .padding(5.dp)
                            .height(1.dp)
                            .fillMaxWidth(), color = MaterialTheme.colorScheme.outline
                    )
                }

                item {
                    //Change Password
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                showDialogEmail.value = true
                            },
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .weight(1f)
                        ) {
                            Text(modifier = Modifier, text = "Почта Outlook", fontSize = 25.sp)
                            Text(
                                modifier = Modifier,
                                text = "Посмотреть логин и пароль",
                                fontSize = 15.sp
                            )
                        }
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_navigate_next_24),
                            contentDescription = null
                        )
                    }
                }

                item {
                    Spacer(Modifier.height(10.dp))
                    OutlinedButton(onClick = {
                        viewModel.logOut()
                        navController.navigate("login")
                    }, modifier = Modifier.fillMaxWidth()) {
                        Text(text = "Выйти", textAlign = TextAlign.Center, fontSize = 18.sp)
                    }
                }
            }
            if (state.error.isNotBlank()) {
                if (state.error == "LessCookie") {
                    Box(modifier = Modifier.align(Alignment.Center)) {
                        Column(modifier = Modifier) {
                            Text(
                                text = "Сначала войдите в аккаунт...",
                                fontSize = 25.sp
                            )
                        }
                    }
                }
            }
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }

    }
    if (showDialogBio.value)
        ChangeBioDialog(
            setShowDialog = {
                showDialogBio.value = it
            }
        )

    if (showDialogPass.value)
        ChangePasswordDialog(
            setShowDialog = {
                showDialogPass.value = it
            }
        )

    if (showDialogEmail.value)
        CheckEmailDialog(
            setShowDialog = {
                showDialogEmail.value = it
            }
        )


}