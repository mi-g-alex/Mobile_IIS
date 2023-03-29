package by.g_alex.mobile_iis.presentation.schedule.lists_items
import by.g_alex.mobile_iis.R
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import by.g_alex.mobile_iis.presentation.schedule.ScheduleViewModel
import by.g_alex.mobile_iis.presentation.schedule.navigation.Screen
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.compose.domain.model.PrepodModel


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PrepItem(prep: PrepodModel, viewModel: ScheduleViewModel, navController: NavController){
    Column(modifier = Modifier.padding(vertical = 5.dp, horizontal = 20.dp).clickable {
        viewModel.addPrepods(prep)
        viewModel.headertext.value = prep.fio
        viewModel.getPrepodScheadule(prep.urlId)
        navController.navigate(Screen.Home.route)
    }) {
        Row(modifier = Modifier.padding(bottom = 10.dp)) {
            GlideImage(
                model = prep.photoLink,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.size(50.dp,50.dp),
                contentDescription = "sdkcmsd"
//                placeHolder = ImageBitmap.imageResource(R.drawable.placeholder),
//                // shows an error ImageBitmap when the request failed.
//                error = ImageBitmap.imageResource(R.drawable.error)
            ){
                it.placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground).circleCrop()
            }
            val dept = remember{mutableStateOf("")}
            dept.value = ""
            for(n in prep.academicDepartment.indices){
                dept.value += prep.academicDepartment[n]
                if(n != prep.academicDepartment.size-1)
                    dept.value+=", "
            }
            Column() {
                Text(text = prep.fio, color = Color.LightGray, fontSize = 20.sp, modifier = Modifier.padding(horizontal = 10.dp))
                Text(text = prep.degree, fontSize = 10.sp, color = Color.LightGray, modifier = Modifier.padding(horizontal = 20.dp))
                Text(text = dept.value, fontSize = 10.sp, color = Color.LightGray, modifier = Modifier.padding(horizontal = 20.dp))
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