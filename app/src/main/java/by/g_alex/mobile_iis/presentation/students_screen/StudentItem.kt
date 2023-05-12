package by.g_alex.mobile_iis.presentation.students_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.g_alex.mobile_iis.R
import by.g_alex.mobile_iis.data.remote.dto.students.Cv
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun StudentItem(
    item: Cv
) {
    Column(
        modifier = Modifier
            .padding(vertical = 5.dp, horizontal = 20.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 5.dp)
                .fillMaxWidth()
        ) {
            GlideImage(
                model = item.photoUrl,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .size(70.dp)
                    .clip(CircleShape),
                contentDescription = item.lastName
            ) {
                it.placeholder(R.drawable.profile_default)
                    .error(R.drawable.profile_default).circleCrop()
            }
            Column {
                Text(
                    text = item.lastName + " " + item.firstName + " " + item.middleName,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                Text(
                    text = item.faculty + " " + item.speciality,
                    modifier = Modifier.padding(horizontal = 20.dp),
                    fontSize = 10.sp
                )
                Text(
                    text = item.studentGroup,
                    modifier = Modifier.padding(horizontal = 20.dp),
                    fontSize = 10.sp
                )
                Row(modifier = Modifier.padding(horizontal = 20.dp)) {
                    if(item.showRating)
                    for (n in 1..5) {
                        Icon(
                            painter = painterResource(id = R.drawable.star_icon),
                            modifier = Modifier.size(15.dp),
                            contentDescription = "sdcscds",
                            tint = if (item.rating >= n) Color(0xffffbf00)
                            else MaterialTheme.colorScheme.onBackground
                        )

                    }
                }
            }
        }
    }
}