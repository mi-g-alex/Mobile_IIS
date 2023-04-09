package by.g_alex.mobile_iis.presentation.study_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.ExtraBold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import by.g_alex.mobile_iis.data.remote.dto.study.StudyCertificationsDto


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StudyScreen(
    viewModel: StudyViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Учёба")
                },
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            if (state.studyCertificate != null) {
                LazyColumn {
                    stickyHeader {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            text = "Справки",
                            fontSize = 28.sp
                        )
                    }
                    items(state.studyCertificate.size) {
                        Item(state.studyCertificate[it])
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
}

@Composable
private fun Item(it: StudyCertificationsDto) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        Column(Modifier.padding(10.dp)) {
            Text(text = it.provisionPlace, fontWeight = ExtraBold, fontSize = 20.sp)
            Text(text = it.dateOrder, fontSize = 18.sp)
            var t = ""
            when (it.status) {
                1 -> t = "Напечатана"
                2 -> t = "Обработка"
                3 -> t = "Отклонена"
            }
            if (t.isNotEmpty()) Text("Статус: $t", fontSize = 18.sp)
            if (it.rejectionReason != null) {
                Text(text = "Причина отказа: ${it.rejectionReason}", fontSize = 18.sp)
            }
        }
    }
}


