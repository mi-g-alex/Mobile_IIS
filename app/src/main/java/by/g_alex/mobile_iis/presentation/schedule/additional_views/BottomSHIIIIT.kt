package by.g_alex.mobile_iis.presentation.schedule.additional_views

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import by.g_alex.mobile_iis.R
import by.g_alex.mobile_iis.common.Constants.ADDED_SCHEDULE
import by.g_alex.mobile_iis.presentation.schedule.ScheduleViewModel
import by.g_alex.mobile_iis.presentation.schedule.navigation.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSHIIT(viewModel: ScheduleViewModel, navController:NavController, coroutineScope:CoroutineScope, sheetState: BottomSheetState){
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(viewModel.getGroups()) { item ->
            Text(
                text = item,
                fontSize = 30.sp,
                color = Color.LightGray,
                modifier = Modifier
                    .padding(start = 10.dp, top = 5.dp, bottom = 5.dp)
                    .fillMaxWidth()
                    .clickable {
                        viewModel.getSchedule(item); viewModel.headerText.value =
                        item
                        coroutineScope.launch { sheetState.collapse() }
                    })

        }
        item {
           if(viewModel.getEmployees().isNotEmpty()) Box(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 5.dp).background(
                    Color.LightGray
                )
                    .height(2.dp).clip(shape = RoundedCornerShape(100.dp))
            )
        }
            items(viewModel.getEmployees()) { iter ->
            val prefs = viewModel.context.getSharedPreferences(ADDED_SCHEDULE, Context.MODE_PRIVATE)
            val urlidd = prefs.getString(iter,"")?:""
            Text(
                text = iter,
                fontSize = 30.sp,
                color = Color.LightGray,
                modifier = Modifier
                    .padding(start = 10.dp, top = 5.dp, bottom = 5.dp)
                    .fillMaxWidth()
                    .clickable {
                        viewModel.getEmployeeSchedule(urlidd); viewModel.headerText.value =
                        iter
                        Log.e("TAGG", urlidd)
                        coroutineScope.launch { sheetState.collapse() }
                    })

        }
        item {
            Row(modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navController.navigate(route = Screen.Search.route)
                }) {
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.plus),
                    contentDescription = "fghjk",
                    modifier = Modifier
                        .height(70.dp)
                        .width(70.dp)
                        .padding(5.dp)
                )
                Text(
                    text = "Add new",
                    fontSize = 30.sp,
                    color = Color.LightGray,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }
    }
}