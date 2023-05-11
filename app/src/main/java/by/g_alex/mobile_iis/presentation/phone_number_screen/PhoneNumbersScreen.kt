package by.g_alex.mobile_iis.presentation.phone_number_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import by.g_alex.mobile_iis.data.remote.dto.phone_book.RequestDto
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

@Composable
fun PhoneNumbersScreen(
    viewModel: PhoneBookViewModel = hiltViewModel()
) {
    val searchText = remember {
        mutableStateOf("")
    }

    val res = remember { mutableStateOf(viewModel.pageList) }
    val currentPage = remember{ mutableStateOf(0) }
    val currentInd = remember{ mutableStateOf(0) }
    val coroutine = rememberCoroutineScope()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { androidx.compose.material3.Text(text = "Справочник", fontSize = 20.sp) }
            )
        }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Column(modifier = Modifier.fillMaxSize()) {

                OutlinedTextField(
                    value = searchText.value,
                    shape = MaterialTheme.shapes.large,
                    onValueChange = { newText ->
                        searchText.value = newText
                        viewModel.pageList.clear()
                        currentInd.value = 0
                        currentPage.value = 0
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 10.dp),
                    singleLine = true,
                    leadingIcon = {
                        Icon(Icons.Outlined.Search, contentDescription = "Search")
                    },
                    keyboardActions = KeyboardActions(onDone = {viewModel.getPhoneBook(RequestDto(searchValue = searchText.value, pageSize = 20, currentPage = 1))})
                )
                val lazyListState = rememberLazyListState()
                LaunchedEffect(lazyListState){
                    snapshotFlow {
                        lazyListState.firstVisibleItemIndex
                    }
                        .debounce(100L)
                        .collectLatest {
                            currentInd.value = it
                        }
                }
                if(currentInd.value>currentPage.value*20-10 && currentInd.value>0){
                    viewModel.getPhoneBook(RequestDto(searchValue = searchText.value, currentPage = ++currentPage.value, pageSize = 20))
                }
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    state = lazyListState
                ){
                    if(res.value.isNotEmpty()) {
                        viewModel.pageList.onEach {
                            items(it.auditoryPhoneNumberDtoList) {item->
                                PhoneListItem(item = item)
                           }
                        }
                    }
                    if(viewModel.state.value.isLoading){

                        item {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                        }

                        coroutine.launch { lazyListState.scrollToItem(currentInd.value+1)}
                    }
                }

            }
        }

    }
}
