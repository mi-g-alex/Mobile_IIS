package by.g_alex.mobile_iis.presentation.study_screen

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import by.g_alex.mobile_iis.data.remote.dto.study.StudyCertificationsDto
import by.g_alex.mobile_iis.data.remote.dto.study.StudyMarkSheetDto


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StudyScreen(
    viewModel: StudyViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = viewModel.state.value

    val showDialog = remember { mutableStateOf(false) }

    LaunchedEffect(viewModel.needUpdate.value) {
        if (viewModel.needUpdate.value) {
            navController.navigate("studyHome")
            navController.backQueue.remove(navController.previousBackStackEntry)
        }
    }

    val cnt = LocalContext.current

    LaunchedEffect(viewModel.needShowError.value) {
        if (viewModel.needShowError.value != null) {
            Toast.makeText(cnt, viewModel.needShowError.value, Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Учёба")
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog.value = true },
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Добавить"
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            LazyColumn {
                if (!(state.studyAll?.mark_sheet.isNullOrEmpty())) {
                    stickyHeader {
                        Text(
                            modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.background),
                            textAlign = TextAlign.Center,
                            text = "Ведомостички",
                            fontSize = 28.sp
                        )
                    }
                    state.studyAll?.mark_sheet?.let {
                        items(it.size) {
                            MarkSheetItem(state.studyAll.mark_sheet[it])
                        }
                    }

                }
                if (!state.studyAll?.certifications.isNullOrEmpty()) {
                    stickyHeader {
                        Text(
                            modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.background),
                            textAlign = TextAlign.Center,
                            text = "Справки",
                            fontSize = 28.sp
                        )
                    }
                    state.studyAll?.certifications?.let {
                        items(it.size) {
                            CertificationItem(state.studyAll.certifications[it], viewModel)
                        }
                    }
                }

                if (!state.studyAll?.applications.isNullOrEmpty()) {
                    stickyHeader {
                        Text(
                            modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.background),
                            textAlign = TextAlign.Center,
                            text = "ДОТ",
                            fontSize = 28.sp
                        )
                    }
                    state.studyAll?.applications?.let {
                        item {
                            Text(text = it.toString())
                        }
                    }
                }

                if (!state.studyAll?.lib_debts.isNullOrEmpty()) {
                    stickyHeader {
                        Text(
                            modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.background),
                            textAlign = TextAlign.Center,
                            text = "Задолженности в библиотеке",
                            fontSize = 28.sp
                        )
                    }
                    state.studyAll?.lib_debts?.let {
                        items(it.size) { it1 ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .padding(horizontal = 10.dp, vertical = 5.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.inverseOnSurface
                                )
                            ) {
                                Column(Modifier.padding(10.dp)) {
                                    Text(
                                        text = "Дата пропуска ${it[it1]}",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold
                                    )
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

    if (showDialog.value)
        SelectCertificateOrMarkSheetDialog(
            setShowDialog = {
                showDialog.value = it
            },
            navController = navController
        )
}

@Composable
private fun CertificationItem(it: StudyCertificationsDto, viewModel: StudyViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 10.dp, vertical = 5.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.inverseOnSurface
        )
    ) {
        Column(Modifier.padding(10.dp)) {
            var t = ""
            when (it.status) {
                1 -> t = "Напечатано"
                2 -> t = "Обработка"
                3 -> t = "Отклонена"
            }
            Text(
                text = it.provisionPlace,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Row(modifier = Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                Text(text = it.dateOrder, fontSize = 15.sp)
                Text(text = t, fontFamily = FontFamily.Monospace, fontSize = 15.sp)
            }
            if (it.rejectionReason != null) {
                Text(text = "Причина отказа: ${it.rejectionReason}", fontSize = 15.sp)
            }
            if(it.status == 2) {
                Divider(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .height(1.dp)
                        .fillMaxWidth(), color = MaterialTheme.colorScheme.outline
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            it.id?.let { it1 -> viewModel.closeCert(it1) }
                        },
                    horizontalArrangement =  Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Отменить", fontSize = 20.sp)
                }
            }
        }
    }
}


@Composable
private fun MarkSheetItem(it: StudyMarkSheetDto) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        Column(Modifier.padding(10.dp)) {
            Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                it.createDate?.let { it1 -> Text(text = it1, fontSize = 15.sp) }
                it.status?.let { it1 -> Text(text = it1, fontSize = 15.sp) }
            }
            it.subject?.abbrev?.let { it1 -> Text(text = it1, fontSize = 15.sp) }
            it.subject?.lessonTypeAbbrev?.let { it1 -> Text(text = it1, fontSize = 15.sp) }
            it.term?.let { it1 -> Text(text = "$it1 семестр", fontSize = 15.sp) }
            it.employee?.fio?.let { it1 -> Text(text = it1, fontSize = 15.sp) }
            it.absentDate?.let { it1 -> Text(text = "Дата пропуска $it1", fontSize = 15.sp) }
            it.price?.let { it1 -> Text(text = "Стоимость $it1", fontSize = 15.sp) }
            if (it.rejectionReason != null) {
                Text(text = "Причина отказа: ${it.rejectionReason}", fontSize = 15.sp)
            }
        }
    }
}


