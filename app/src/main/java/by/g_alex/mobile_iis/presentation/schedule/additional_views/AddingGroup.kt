package by.g_alex.mobile_iis.presentation.schedule.additional_views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import by.g_alex.mobile_iis.presentation.schedule.ScheduleViewModel
import by.g_alex.mobile_iis.presentation.schedule.lists_items.GroupItem

@Composable
fun AddingNewGroup(viewModel: ScheduleViewModel, navController:NavController){
    val searchText = remember {
        mutableStateOf("")
    }
    Box() {
        val state = viewModel.groupState.value
        Column(modifier = Modifier.fillMaxSize()) {
            OutlinedTextField(
                value = searchText.value,
                shape = MaterialTheme.shapes.large,
                onValueChange = { newText ->
                    searchText.value = newText
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                leadingIcon = { Icon(Icons.Outlined.Search, contentDescription = "Search") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 10.dp)

            )

            if (state.Groups.isNotEmpty()) {
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(state.Groups) { itm ->
                        if (itm.name.contains(searchText.value)) GroupItem(
                            group = itm,
                            viewModel,
                            navController
                        )
                    }
                }
            } else
                viewModel.getGroup()
        }
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
        if (state.error.isNotBlank())
            Text(
                text = "Something went wrong..." + state.error,
                fontSize = 20.sp,
                modifier = Modifier.align(
                    Alignment.Center
                )
            )
    }
}