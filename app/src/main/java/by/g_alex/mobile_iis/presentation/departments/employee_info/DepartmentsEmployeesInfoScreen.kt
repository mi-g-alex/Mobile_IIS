package by.g_alex.mobile_iis.presentation.departments.employee_info

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import by.g_alex.mobile_iis.R
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.google.accompanist.flowlayout.FlowRow

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DepartmentsEmployeesInfoScreen(
    id: String,
    viewModel: DepartmentsEmployeesInfoViewModel = hiltViewModel()
) {

    val state = viewModel.state.value
    remember { mutableStateOf(TextFieldValue("")) }

    LaunchedEffect(Unit) {
        viewModel.getEmployees(id)
    }

    val ctx = LocalContext.current

    Scaffold {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            if (state.employeesState != null) {
                LazyColumn {
                    item {
                        Column(modifier = Modifier.padding(15.dp)) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                GlideImage(
                                    model = state.employeesState.photoLink,
                                    contentScale = ContentScale.FillBounds,
                                    modifier = Modifier
                                        .size(250.dp, 250.dp)
                                        .clip(CircleShape),
                                    contentDescription = state.employeesState.lastName + " " +
                                            state.employeesState.firstName + " " +
                                            state.employeesState.middleName
                                ) { it1 ->
                                    it1.placeholder(R.drawable.profile_default)
                                        .error(R.drawable.profile_default).circleCrop()
                                }

                                Text(
                                    text = state.employeesState.lastName + " " +
                                            state.employeesState.firstName + " " +
                                            state.employeesState.middleName, fontSize = 25.sp,
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )
                            }

                            if (state.employeesState.jobPositions?.isNotEmpty() == true) {
                                Divider(
                                    modifier = Modifier
                                        .padding(5.dp)
                                        .height(1.dp)
                                        .fillMaxWidth(), color = MaterialTheme.colorScheme.outline
                                )
                                Text(
                                    text = "Должность. Место работы",
                                    fontSize = 25.sp,
                                    modifier = Modifier
                                )
                                for (i in 0 until state.employeesState.jobPositions.size) {
                                    Text(
                                        text = state.employeesState.jobPositions[i]?.jobPosition.toString()
                                                + ", "
                                                + state.employeesState.jobPositions[i]?.department.toString(),
                                        fontSize = 20.sp,
                                    )
                                }
                            }

                            if (state.employeesState.jobPositions?.isNotEmpty() == true) {
                                Divider(
                                    modifier = Modifier
                                        .padding(5.dp)
                                        .height(1.dp)
                                        .fillMaxWidth(), color = MaterialTheme.colorScheme.outline
                                )
                                Text(
                                    text = "Контакты",
                                    fontSize = 25.sp,
                                    modifier = Modifier
                                )
                                AssistChip(
                                    label = { Text(text = state.employeesState.email.toString()) },
                                    onClick = {
                                        try {
                                            val emailIntent =
                                                Intent(
                                                    Intent.ACTION_VIEW,
                                                    Uri.parse("mailto:" + state.employeesState.email.toString())
                                                )
                                            ctx.startActivity(emailIntent)
                                        } catch (_: Exception) {
                                            Toast
                                                .makeText(
                                                    ctx,
                                                    "An error occurred",
                                                    Toast.LENGTH_LONG
                                                )
                                                .show()
                                        }
                                    },
                                    leadingIcon = {
                                        Icon(
                                            painter = painterResource(id = R.drawable.mail_icon),
                                            contentDescription = "Localized description",
                                            Modifier.size(AssistChipDefaults.IconSize)
                                        )
                                    },
                                )
                                for (i in 0 until state.employeesState.jobPositions.size) {
                                    for (j in 0 until (state.employeesState.jobPositions[i]?.contacts?.size
                                        ?: 0)) {

                                        AssistChip(
                                            label = {
                                                Text(
                                                    text = state.employeesState.jobPositions[i]?.contacts?.get(
                                                        j
                                                    )?.phoneNumber.toString()
                                                )
                                            },
                                            onClick = {
                                                val u = Uri.parse(
                                                    "tel:" + state.employeesState.jobPositions[i]?.contacts?.get(
                                                        j
                                                    )?.phoneNumber.toString()
                                                )
                                                val d = Intent(Intent.ACTION_DIAL, u)
                                                try {
                                                    ctx.startActivity(d)
                                                } catch (_: Exception) {
                                                    Toast
                                                        .makeText(
                                                            ctx,
                                                            "An error occurred",
                                                            Toast.LENGTH_LONG
                                                        )
                                                        .show()
                                                }
                                            },
                                            leadingIcon = {
                                                Icon(
                                                    painter = painterResource(id = R.drawable.phone_icon),
                                                    contentDescription = "Localized description",
                                                    Modifier.size(AssistChipDefaults.IconSize)
                                                )
                                            },
                                        )

                                        Text(
                                            text = state.employeesState.jobPositions[i]?.contacts?.get(
                                                j
                                            )?.address.toString(),
                                            fontSize = 20.sp
                                        )
                                    }
                                }
                            }
                            if (state.employeesState.profileLinks.isNotEmpty()) {
                                Divider(
                                    modifier = Modifier
                                        .padding(5.dp)
                                        .height(1.dp)
                                        .fillMaxWidth(), color = MaterialTheme.colorScheme.outline
                                )
                                Text(
                                    text = "Ссылки",
                                    fontSize = 25.sp,
                                    modifier = Modifier
                                )
                                FlowRow(
                                    mainAxisSpacing = 8.dp,
                                    crossAxisSpacing = (-5).dp,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    for (i in 0 until state.employeesState.profileLinks.size) {
                                        AssistChip(
                                            label = {
                                                Text(
                                                    text = state.employeesState.profileLinks[i]?.profileLinkType.toString()
                                                )
                                            },
                                            onClick = {
                                                val u = Uri.parse(
                                                    "url: " + state.employeesState.profileLinks[i]?.link.toString()
                                                )
                                                val d = Intent(Intent.ACTION_DIAL, u)
                                                try {
                                                    ctx.startActivity(d)
                                                } catch (_: Exception) {
                                                    Toast
                                                        .makeText(
                                                            ctx,
                                                            "An error occurred",
                                                            Toast.LENGTH_LONG
                                                        )
                                                        .show()
                                                }
                                            },
                                        )
                                    }
                                }
                            }
                        }
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
                } else {
                    Box(modifier = Modifier.align(Alignment.Center)) {
                        Column(modifier = Modifier) {
                            Text(
                                text = "Ошибка подключения к серверу...",
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
}

