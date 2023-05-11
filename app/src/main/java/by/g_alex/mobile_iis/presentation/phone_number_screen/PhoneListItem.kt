package by.g_alex.mobile_iis.presentation.phone_number_screen

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.g_alex.mobile_iis.R
import by.g_alex.mobile_iis.data.remote.dto.phone_book.AuditoryPhoneNumberDto

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PhoneListItem(
    item: AuditoryPhoneNumberDto
) {
    val ctx = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
    ) {
        var deps = ""
        item.departments.onEach {
            deps += it.abbrev + "/"
        }
        Text(
            text = deps.removeSuffix("/"),
            color = MaterialTheme.colorScheme.inverseSurface,
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(text = item.auditory, color = MaterialTheme.colorScheme.inverseSurface)
        var fios = ""
        item.employees.onEach {
            fios += it.fio + ","
        }

        Text(text = fios.removeSuffix(","), color = MaterialTheme.colorScheme.inverseSurface)

        FlowRow() {
            item.phones.onEach {
                AssistChip(
                    label = { androidx.compose.material3.Text(text = it) },
                    onClick = {
                        val u = Uri.parse("tel:" + it)
                        val i = Intent(Intent.ACTION_DIAL, u)
                        try {
                            ctx.startActivity(i)
                        } catch (_: Exception) {
                            Toast
                                .makeText(ctx, "An error occurred", Toast.LENGTH_LONG)
                                .show()
                        }
                    },
                    modifier = Modifier.padding(horizontal = 5.dp),
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.phone_icon),
                            contentDescription = "Localized description",
                            Modifier.size(AssistChipDefaults.IconSize)
                        )
                    },
                )
            }
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .height(1.dp),
            color = MaterialTheme.colorScheme.inverseSurface,
        )
    }
}