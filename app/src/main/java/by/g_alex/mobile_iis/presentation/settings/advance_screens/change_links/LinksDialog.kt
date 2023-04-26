package by.g_alex.mobile_iis.presentation.settings.advance_screens.change_links

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import by.g_alex.mobile_iis.R
import by.g_alex.mobile_iis.domain.model.profile.Reference

@Composable
fun LinksDialog(
    setShowDialog: (Boolean) -> Unit,
    viewModel: LinksViewModel = hiltViewModel(),
    //references: MutableList<Reference>
) {

    val inName = remember { mutableStateOf(TextFieldValue("")) }
    val inRef = remember { mutableStateOf(TextFieldValue("")) }

    val links = remember {
        mutableStateOf((listOf<Reference>()))
    }
    val delInd = remember {
        mutableStateOf(mutableListOf<Int>())
    }
    LaunchedEffect(Unit) {
        links.value = viewModel.getLinkFromDB().toMutableList()
    }
    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.height(350.dp)
        ) {
            LazyColumn(modifier = Modifier.padding(10.dp).fillMaxSize()) {
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
                        Text(
                            text = reference.reference,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.weight(1f)
                        )
                        val res = remember {
                            mutableStateOf(R.drawable.baseline_delete_outline_24)
                        }
                        Icon(
                            painter = painterResource(id = res.value),
                            contentDescription = "sdc",
                            modifier = Modifier
                                .clickable {
                                    res.value =
                                        if (res.value == R.drawable.baseline_delete_outline_24) R.drawable.baseline_delete_sm
                                        else R.drawable.baseline_delete_outline_24
                                    if (delInd.value.contains(index)) {
                                        delInd.value.remove(delInd.value.indexOf(index))
                                    } else
                                        delInd.value.add(index)
                                }
                                .weight(0.2f),
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Divider(modifier = Modifier.padding(5.dp).height(1.dp).fillMaxWidth(), color = MaterialTheme.colorScheme.outline)
                }
                item {
                    OutlinedTextField(value = inName.value, onValueChange = {
                        inName.value = it
                    }, label = { Text(text = "Name") },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = MaterialTheme.colorScheme.onBackground,
                            unfocusedBorderColor = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = inRef.value,
                        onValueChange = { inRef.value = it },
                        label = { Text(text = "Reference") },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = MaterialTheme.colorScheme.onBackground,
                            unfocusedBorderColor = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Button(onClick = {
                        val newLinks = mutableListOf<Reference>()
                        links.value.onEachIndexed { index, reference ->
                            if (!delInd.value.contains(index)) {
                                newLinks.add(reference)
                            }
                        }
                        if (inName.value.text.isNotBlank() && inRef.value.text.isNotBlank())
                            newLinks.add(
                                Reference(
                                    id = null,
                                    name = inName.value.text,
                                    reference = inRef.value.text
                                )
                            )
                        viewModel.putLinks(newLinks)
                        Log.e("NewLInk", newLinks.toString())
                        setShowDialog(false)
                    },
                    modifier = Modifier.fillMaxWidth()) {

                        Text(text = "Cохранить", textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        }
    }
}