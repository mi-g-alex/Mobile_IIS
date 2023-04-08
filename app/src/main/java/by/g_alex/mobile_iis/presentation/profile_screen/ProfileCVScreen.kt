package by.g_alex.mobile_iis.presentation.profile_screen

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import by.g_alex.mobile_iis.R
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.flowlayout.FlowRow
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun ProfileCVScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    val state = viewModel.state.value
    val uriHandler = LocalUriHandler.current
    val context = LocalContext.current

    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    LaunchedEffect(selectedImageUri) {
        if(selectedImageUri != null){
            val uri: Uri? = selectedImageUri
            val source: ImageDecoder.Source =
                uri?.let { ImageDecoder.createSource(context.contentResolver, it) }!!
            val bitmap: Bitmap = ImageDecoder.decodeBitmap(source)
            val outputStream = ByteArrayOutputStream()
            Log.e("~~~", selectedImageUri!!.encodedUserInfo.toString())
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            val imageBytes = outputStream.toByteArray()
            val base64String = Base64.encodeToString(imageBytes, Base64.DEFAULT)
            viewModel.updatePhoto(base64String)
        }
    }

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> selectedImageUri = uri }
    )

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
        state.profileInfo?.let { profileInfo ->
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
            ) {
                Card(
                    modifier = Modifier
                        .wrapContentSize(Alignment.TopCenter)
                        .fillMaxWidth(1f)
                        .padding(end = 10.dp, top = 0.dp, start = 10.dp, bottom = 10.dp),
                    shape = RoundedCornerShape(bottomEnd = 35.dp, bottomStart = 35.dp)
                ) {
                    Column(
                        modifier = Modifier,
                        horizontalAlignment = CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .wrapContentWidth()
                                .padding(top = 50.dp),
                            contentAlignment = Alignment.TopCenter
                        ) {
                            if (profileInfo.photoUrl == null)
                                Image(
                                    painter = painterResource(id = R.drawable.profile_default),
                                    contentDescription = profileInfo.lastName + " " + profileInfo.firstName + " " + profileInfo.middleName,
                                    modifier = Modifier
                                        .size(200.dp)
                                        .clip(CircleShape)
                                        .background(Color.Gray, shape = CircleShape)
                                        .border(1.dp, Color.Green)
                                        .clickable {
                                            singlePhotoPickerLauncher.launch(
                                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                            )
                                        },
                                    contentScale = ContentScale.Crop,
                                )
                            else Image(
                                painter = rememberAsyncImagePainter(profileInfo.photoUrl),
                                contentDescription = profileInfo.lastName + " " + profileInfo.firstName + " " + profileInfo.middleName,
                                modifier = Modifier
                                    .size(200.dp)
                                    .clip(CircleShape)
                                    .background(Color.White)
                                    .border(3.dp, Color.Black, CircleShape)
                                    .clickable {
                                        singlePhotoPickerLauncher.launch(
                                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                        )
                                    },
                                contentScale = ContentScale.Crop,
                            )
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            text = profileInfo.lastName + " " + profileInfo.firstName + " " + profileInfo.middleName,
                            fontSize = 25.sp
                        )
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 20.dp),
                            textAlign = TextAlign.Center,
                            text = "Курс " + profileInfo.course + ", " + profileInfo.faculty + ", " + profileInfo.speciality + ", " + profileInfo.studentGroup,
                            fontSize = 20.sp
                        )
                    }
                }

                Card(
                    modifier = Modifier
                        .wrapContentSize(Alignment.TopCenter)
                        .fillMaxWidth(1f)
                        .padding(10.dp),
                    shape = RoundedCornerShape(35.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                    ) {
                        Text(
                            text = "Основная информация",
                            fontSize = 23.sp
                        )
                        profileInfo.summary?.let {
                            Text(
                                text = it,
                                fontSize = 20.sp
                            )
                        }

                        Divider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 15.dp)
                        )

                        Text(
                            text = "Навыки",
                            fontSize = 23.sp
                        )
                        FlowRow(
                            mainAxisSpacing = 8.dp,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            profileInfo.skills?.forEach { skill ->
                                ElevatedSuggestionChip(
                                    onClick = {},
                                    label = {
                                        Text(
                                            text = skill.name
                                        )
                                    }
                                )
                            }
                        }

                        Divider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 15.dp)
                        )

                        Text(
                            text = "Ссылки",
                            fontSize = 23.sp
                        )

                        FlowRow(
                            mainAxisSpacing = 8.dp,
                            crossAxisSpacing = 10.dp,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            profileInfo.references?.forEach { references ->
                                ElevatedSuggestionChip(
                                    icon = {
                                        Icon(
                                            painter = painterResource(id = R.drawable.tg_outline),
                                            contentDescription = "Localized description",
                                            Modifier.size(AssistChipDefaults.IconSize)
                                        )
                                    },
                                    onClick = {
                                        try {
                                            uriHandler.openUri(references.reference)
                                        } catch (e: Exception) {
                                            try {
                                                uriHandler.openUri("https://" + references.reference)
                                            } catch (e: Exception) {
                                                Toast.makeText(
                                                    context,
                                                    e.localizedMessage?.toString() ?: "Error",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }
                                    },
                                    label = {
                                        Text(
                                            text = references.name
                                        )
                                    },
                                )
                            }
                        }

                    }
                }

                Button(
                    onClick = {
                        viewModel.logOut()
                        navController.navigate("login")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 10.dp, top = 0.dp, start = 10.dp, bottom = 10.dp)
                ) {
                    Text(
                        text = "LogOut"
                    )
                }
            }
        }

        LaunchedEffect(state.error) {
            if (state.error == "LessCookie") {
                navController.navigate("login")
            }
        }

        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}

