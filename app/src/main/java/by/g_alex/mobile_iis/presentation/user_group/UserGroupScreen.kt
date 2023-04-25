package by.g_alex.mobile_iis.presentation.user_group

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle.Companion.Italic
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import by.g_alex.mobile_iis.R
import by.g_alex.mobile_iis.data.remote.dto.use_group.UserGroupDto
import com.google.accompanist.flowlayout.FlowRow


@Composable
fun UserGroupScreen(
    viewModel: UserGroupViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    state.userGroupState?.numberOfGroup?.let { Text("Группа $it") }
                },
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            if (state.userGroupState != null) {
                LazyColumn {
                    items(state.userGroupState.groupInfoStudentDto.size) {
                        Item(state.userGroupState.groupInfoStudentDto[it])
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
private fun Item(it: UserGroupDto.GroupInfoStudentDto) {
    val ctx = LocalContext.current
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

            if (it.position.isNotBlank())
                Text(
                    text = it.position,
                    fontStyle = Italic,
                    fontSize = 15.sp,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )

            Text(text = it.fio, fontWeight = Bold, fontSize = 18.sp)


            FlowRow(
                mainAxisSpacing = 8.dp,
                crossAxisSpacing = (-5).dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                AssistChip(
                    label = { Text(text = it.phone) },
                    onClick = {
                        val u = Uri.parse("tel:" + it.phone)
                        val i = Intent(Intent.ACTION_DIAL, u)
                        try {
                            ctx.startActivity(i)
                        } catch (_: Exception) {
                            Toast
                                .makeText(ctx, "An error occurred", Toast.LENGTH_LONG)
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
                AssistChip(
                    label = { Text(text = it.email) },
                    onClick = {
                        try {
                            val emailIntent =
                                Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + it.email))
                            ctx.startActivity(emailIntent)
                        } catch (_: Exception) {
                            Toast
                                .makeText(ctx, "An error occurred", Toast.LENGTH_LONG)
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
            }
        }
    }
}


