package by.g_alex.mobile_iis.presentation.omissions_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import by.g_alex.mobile_iis.data.local.entity.OmissionsByStudentDto
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun OmissionsScreen(
    viewModel: OmissionsViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Пропуски")
                },
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            if (state.omissionsState != null) {
                if (state.omissionsState.isNotEmpty()) {
                    Item(state.omissionsState)
                } else {
                    Text(
                        text = "Тут ничего нет",
                        modifier = Modifier.align(Alignment.Center),
                        textAlign = TextAlign.Center
                    )
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Item(it1: List<OmissionsByStudentDto>) {
    val it = it1.sortedWith(
        compareBy({ it.dateFrom * -1 }, { it.dateTo * -1 })
    )
    LazyColumn {
        for (i in it.indices) {
            if (i == 0) {
                stickyHeader {
                    Text(
                        text = "${it[i].term} семестр",
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.background),
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp
                    )
                }
            } else if (it[i].term != it[i - 1].term) {
                stickyHeader {
                    Text(
                        text = "${it[i].term} семестр",
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.background),
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp
                    )
                }
            }

            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(horizontal = 10.dp, vertical = 5.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.inverseOnSurface
                    )
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text(
                            text = it[i].name,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(vertical = 3.dp)
                                .align(CenterHorizontally)
                        )
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "C " + SimpleDateFormat(
                                    "dd.MM.yyyy",
                                    Locale.getDefault()
                                ).format(Date(it[i].dateFrom)),
                                fontSize = 15.sp,
                                modifier = Modifier.padding(5.dp)
                            )
                            Text(
                                text = "До " + SimpleDateFormat(
                                    "dd.MM.yyyy",
                                    Locale.getDefault()
                                ).format(Date(it[i].dateTo)),
                                fontSize = 15.sp,
                                modifier = Modifier.padding(5.dp)
                            )
                        }
                        it[i].note?.let { it1 ->
                            Text(
                                text = it1,
                                modifier = Modifier.padding(5.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}


