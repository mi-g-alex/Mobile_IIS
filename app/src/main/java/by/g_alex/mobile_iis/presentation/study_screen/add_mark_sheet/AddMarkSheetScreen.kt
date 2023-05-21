package by.g_alex.mobile_iis.presentation.study_screen.add_mark_sheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import java.util.Calendar

@Composable
fun AddMarkSheetScreen() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        val mCalendar = Calendar.getInstance()

        // Fetching current year, month and day
        val mYear = mCalendar.get(Calendar.YEAR)
        val mMonth = mCalendar.get(Calendar.MONTH)
        val mDay = mCalendar.get(Calendar.DAY_OF_WEEK)

        Text(mDay.toString())
    }
}

