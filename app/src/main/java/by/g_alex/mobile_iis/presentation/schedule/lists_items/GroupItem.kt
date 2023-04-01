package by.g_alex.mobile_iis.presentation.schedule.lists_items
import by.g_alex.mobile_iis.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import by.g_alex.mobile_iis.presentation.schedule.ScheduleViewModel
import by.g_alex.mobile_iis.presentation.schedule.navigation.Screen
import by.g_alex.mobile_iis.domain.model.profile.schedule.GroupModel


@Composable
fun GroupItem(group: GroupModel, viewModel: ScheduleViewModel, navController: NavController){
    Column(modifier = Modifier.padding(vertical = 5.dp, horizontal = 20.dp).clickable {
        viewModel.addGroups(group.name)
        viewModel.headerText.value = group.name
        viewModel.getSchedule(group.name)
        //viewModel.getScheadule(group.name)
        //viewModel.getCurrentWeek()
        navController.navigate(Screen.Home.route)
    }) {
        Row(modifier = Modifier.padding(bottom = 10.dp)) {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.baseline_groups_24),
                contentDescription = "sdsdc",
                modifier = Modifier.size(50.dp, 50.dp)
            )
            Column() {
                Text(text = group.name, color = Color.LightGray, fontSize = 20.sp, modifier = Modifier.padding(horizontal = 10.dp))
                Text(text = group.specialityAbbrev, fontSize = 10.sp, color = Color.LightGray, modifier = Modifier.padding(horizontal = 20.dp))
                Text(text = group.facultyAbbrev, fontSize = 10.sp, color = Color.LightGray, modifier = Modifier.padding(horizontal = 20.dp))
            }
        }
        Box(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .fillMaxWidth()
                .background(Color.DarkGray)
                .height(1.dp)
        )

    }

}