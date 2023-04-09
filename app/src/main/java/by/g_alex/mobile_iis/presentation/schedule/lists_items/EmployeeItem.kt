package by.g_alex.mobile_iis.presentation.schedule.lists_items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import by.g_alex.mobile_iis.R
import by.g_alex.mobile_iis.domain.model.profile.schedule.EmployeeModel
import by.g_alex.mobile_iis.presentation.schedule.ScheduleViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun EmployeeItem(
    employee: EmployeeModel,
    viewModel: ScheduleViewModel,
    navController: NavController
) {
    Column(modifier = Modifier
        .padding(vertical = 5.dp, horizontal = 20.dp)
        .clickable {
            viewModel.addEmployees(employee)
            viewModel.headerText.value = employee.fio
            viewModel.getEmployeeSchedule(employee.urlId)
            navController.navigateUp()
        }) {
        Row(modifier = Modifier.padding(bottom = 10.dp).fillMaxWidth()) {
            GlideImage(
                model = employee.photoLink,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.size(50.dp,50.dp).clip(CircleShape),
                contentDescription = employee.fio
            ){
                it.placeholder(R.drawable.profile_default)
                    .error(R.drawable.profile_default).circleCrop()
            }
            Column {
                Text(
                    text = employee.fio,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                if(employee.degree.isNotBlank()) Text(
                    text = employee.degree,
                    fontSize = 10.sp,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )

                var departments = ""

                for(i in employee.academicDepartment) {
                    departments += i;
                    if(i != employee.academicDepartment.last()) departments += ", "
                }

                if(employee.academicDepartment.isNotEmpty()) Text(
                    text = departments,
                    fontSize = 10.sp,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            }
        }
    }
}