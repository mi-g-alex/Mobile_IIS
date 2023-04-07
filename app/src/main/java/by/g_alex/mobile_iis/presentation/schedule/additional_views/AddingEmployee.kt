package by.g_alex.mobile_iis.presentation.schedule.additional_views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import by.g_alex.mobile_iis.presentation.schedule.ScheduleViewModel
import by.g_alex.mobile_iis.presentation.schedule.lists_items.EmployeeItem
import by.g_alex.mobile_iis.domain.model.profile.schedule.EmployeeModel
import kotlinx.coroutines.launch

@Composable
fun AddingEmployee(viewModel: ScheduleViewModel, navController: NavController){
    val searchText = remember {
        mutableStateOf("")
    }
    val prList = remember {
        mutableListOf<EmployeeModel>()
    }
    val coroutineScope = rememberCoroutineScope()
    Box(modifier = Modifier.fillMaxSize()) {
        val state = viewModel.prepState.value
        Column(modifier = Modifier.fillMaxSize()) {
            OutlinedTextField(
                value = searchText.value,
                shape = MaterialTheme.shapes.large,
                onValueChange = { newText ->
                  coroutineScope.launch {
                      searchText.value = newText
                      if (searchText.value.length == 1) {
                          prList.clear()
                          for (n in state.preps ?: emptyList()) {
                              if (n.fio.lowercase()[0] == searchText.value[0]) {
                                  prList.add(n)
                              }
                          }
                      }
                      else if(searchText.value.isEmpty()){
                          prList.clear()
                          prList.addAll(state.preps?: emptyList())
                      }
                  }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 10.dp),
                singleLine = true,
                leadingIcon = { Icon(Icons.Outlined.Search, contentDescription = "Search") }
            )
            if (state.preps?.isNotEmpty() == true) {
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    if(searchText.value.isEmpty() || prList.isEmpty())
                        prList.addAll(state.preps!!)
                    items(prList) { itm ->
                       if(itm.lastName.lowercase().contains(searchText.value.lowercase())) EmployeeItem(employee = itm, viewModel = viewModel, navController = navController)
                    }
                }
            } else
                viewModel.getEmployee()
        }
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
        if (state.error.isNotBlank())
            androidx.compose.material.Text(
                text = "Something went wrong..." + state.error,
                fontSize = 20.sp,
                modifier = Modifier.align(
                    Alignment.Center
                ),
                color = Color.LightGray
            )
    }
}
