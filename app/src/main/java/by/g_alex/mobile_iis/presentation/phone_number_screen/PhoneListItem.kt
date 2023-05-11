package by.g_alex.mobile_iis.presentation.phone_number_screen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.g_alex.mobile_iis.data.remote.dto.phone_book.AuditoryPhoneNumberDto

@Composable
fun PhoneListItem(
    item: AuditoryPhoneNumberDto
) {
    Log.e("SHIt","SHIIIIIIIIT")
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp)) {
        var deps = ""
        deps = ""
        item.departments.onEach {
            deps += it.abbrev + "/"
        }
        Text(text = deps.removeSuffix("/"), color = MaterialTheme.colorScheme.inverseSurface,
        textAlign = TextAlign.Center, fontSize = 20.sp, modifier = Modifier.align(Alignment.CenterHorizontally))
        Text(text = item.auditory, color = MaterialTheme.colorScheme.inverseSurface)
        var fios = ""
        item.employees.onEach {
            fios += it.fio + ","
        }

        Text(text = fios.removeSuffix(","), color = MaterialTheme.colorScheme.inverseSurface)
        var phones = ""
        item.phones.onEach {
            phones += it + ", "
        }

        Text(text = phones.removeSuffix(", "), color = MaterialTheme.colorScheme.inverseSurface)
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .height(1.dp),
            color = MaterialTheme.colorScheme.inverseSurface
        )
    }
}