package by.g_alex.mobile_iis.presentation.settings.advance_screens.change_skills

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import by.g_alex.mobile_iis.R
import by.g_alex.mobile_iis.domain.model.profile.Skill

@Composable
fun ChangeSkillsDialog(
    viewModel: SkillsViewModel = hiltViewModel(),
    navController: NavController
) {
    val inName = remember { mutableStateOf(TextFieldValue("")) }
    val links = remember {
        mutableStateOf((listOf<Skill>()))
    }
    val delInd = remember {
        mutableStateOf(mutableListOf<Int>())
    }
    LaunchedEffect(Unit) {
        links.value = viewModel.getSkillFromDB().toMutableList()
    }
    val showAddDialog = remember{mutableStateOf(false)}
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = "Изменить навыки",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 17.sp
                )
            },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }
                })
        },
        floatingActionButton ={ ExtendedFloatingActionButton(
            icon = { Icon(Icons.Filled.Add, contentDescription = "Добавить") },
            text = { Text("Добавить") },
            onClick = {showAddDialog.value = true},
            shape = RoundedCornerShape(40.dp)
        )}
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {
            LazyColumn(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxSize()
            ) {
                items(links.value) { reference ->
                    val index = links.value.indexOf(reference)
                    Row(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth(),
                        //horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = reference.name, color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.weight(0.5f)
                        )
                        val res = remember {
                            mutableStateOf(R.drawable.delete_o_icon)
                        }
                        Icon(
                            painter = painterResource(id = res.value),
                            contentDescription = "sdc",
                            modifier = Modifier
                                .clickable {
                                    res.value =
                                        if (res.value == R.drawable.delete_o_icon) R.drawable.delete_icon
                                        else R.drawable.delete_o_icon
                                    if (delInd.value.contains(index)) {
                                        delInd.value.remove(delInd.value.indexOf(index))
                                    } else
                                        delInd.value.add(index)
                                    val newLinks = mutableListOf<Skill>()
                                    links.value.onEachIndexed { ind, ref ->
                                        if (!delInd.value.contains(ind)) {
                                            newLinks.add(ref)
                                        }
                                    }
                                    viewModel.putSkills(newLinks)
                                    Log.e("NewLInk", newLinks.toString())
                                    links.value = newLinks
                                    inName.value = TextFieldValue("");
                                }
                                .weight(0.05f),
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Divider(
                        modifier = Modifier
                            .padding(5.dp)
                            .height(1.dp)
                            .fillMaxWidth(), color = MaterialTheme.colorScheme.outline
                    )
                }
            }
        }
    }
    if(showAddDialog.value){
        AlertDialog(
            onDismissRequest = {
               showAddDialog.value = false
            },
            title = { Text(text = "Добавить навык") },
            text = { Column() {
                OutlinedTextField(value = inName.value, onValueChange = {
                    inName.value = it
                }, label = { Text(text = "Навык") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        //textColor = MaterialTheme.colorScheme.onBackground,
                        unfocusedBorderColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
            },
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = MaterialTheme.colorScheme.inverseSurface
                    ),
                    onClick = {
                        val newLinks = mutableListOf<Skill>()
                        newLinks.addAll(links.value)
                        if (inName.value.text.isNotBlank())
                            newLinks.add(
                                Skill(
                                    id = null,
                                    name = inName.value.text,
                                )
                            )
                        links.value = newLinks
                        inName.value = TextFieldValue("")
                        viewModel.putSkills(newLinks)
                        showAddDialog.value = false
                    }) {
                    Text("Oк")
                }
            }
        )
    }
}