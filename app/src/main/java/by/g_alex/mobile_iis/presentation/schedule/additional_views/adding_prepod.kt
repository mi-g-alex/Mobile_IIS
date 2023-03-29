package com.example.compose.presentation.list.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import by.g_alex.mobile_iis.presentation.schedule.ScheduleViewModel
import by.g_alex.mobile_iis.presentation.schedule.lists_items.PrepItem
import com.example.compose.domain.model.PrepodModel
import kotlinx.coroutines.launch

@Composable
fun addingPrepod(viewModel: ScheduleViewModel, navController: NavController){
    val searchtext = remember {
        mutableStateOf("")
    }
    val prList = remember {
        mutableListOf<PrepodModel>()
    }
    val coroutineScope = rememberCoroutineScope()
    Box(modifier = Modifier.fillMaxSize()) {
        val state = viewModel.prepState.value
        Column(modifier = Modifier.fillMaxSize()) {
            OutlinedTextField(
                value = searchtext.value,
                shape = MaterialTheme.shapes.large,
                onValueChange = { newText ->
                  coroutineScope.launch {
                      searchtext.value = newText
                      if (searchtext.value.length == 1) {
                          prList.clear()
                          for (n in state.preps ?: emptyList()) {
                              if (n.fio.lowercase()[0] == searchtext.value[0]) {
                                  prList.add(n)
                              }
                          }
                      }
                      else if(searchtext.value.isEmpty()){
                          prList.clear()
                          prList.addAll(state.preps?: emptyList())
                      }
                  }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 10.dp),
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = androidx.compose.material.MaterialTheme.colors.background,
                    unfocusedBorderColor = androidx.compose.material.MaterialTheme.colors.background,
                    focusedLabelColor = androidx.compose.material.MaterialTheme.colors.primary,
                    cursorColor = androidx.compose.material.MaterialTheme.colors.background ,
                    leadingIconColor = Color.LightGray,
                    backgroundColor = Color.Black//Color(0xff212121)
                ),
                leadingIcon = { Icon(Icons.Outlined.Search, contentDescription = "cajns") },
                textStyle = TextStyle(color = Color.LightGray)
            )
            if (state.preps?.isNotEmpty() == true) {
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    if(searchtext.value.isEmpty() || prList.isEmpty())
                        prList.addAll(state.preps!!)
                    items(prList) { itm ->
                       if(itm.lastName.lowercase().contains(searchtext.value.lowercase())) PrepItem(prep = itm, viewModel = viewModel, navController = navController)
                    }
                }
            } else
                viewModel.getPrepod()
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
