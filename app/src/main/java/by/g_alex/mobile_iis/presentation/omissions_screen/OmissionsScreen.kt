package by.g_alex.mobile_iis.presentation.omissions_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import by.g_alex.mobile_iis.data.remote.dto.omissions.OmissionsByStudentDto
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


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
                Item(state.omissionsState)
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
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Item(it: List<OmissionsByStudentDto>) {
    LazyColumn() {
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
                        .padding(8.dp)
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text(
                            text = it[i].name,
                            fontSize = 30.sp,
                            modifier = Modifier
                                .padding(vertical = 3.dp)
                                .align(CenterHorizontally)
                        )
                        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = "C " + SimpleDateFormat(
                                    "dd.MM.yyyy",
                                    Locale.getDefault()
                                ).format(Date(it[i].dateFrom)),
                                fontSize = 20.sp,
                                modifier = Modifier.padding(vertical = 3.dp)
                            )
                            Text(
                                text = "До " + SimpleDateFormat(
                                    "dd.MM.yyyy",
                                    Locale.getDefault()
                                ).format(Date(it[i].dateTo)),
                                fontSize = 20.sp,
                                modifier = Modifier.padding(vertical = 3.dp)
                            )
                        }
                        it[i].note?.let { it1 ->
                            Text(
                                text = it1,
                                modifier = Modifier.padding(vertical = 5.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}


