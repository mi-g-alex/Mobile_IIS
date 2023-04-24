package by.g_alex.mobile_iis.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import androidx.navigation.navOptions
import by.g_alex.mobile_iis.R
import by.g_alex.mobile_iis.data.remote.dto.login.RestorePasswordEnterLoginResponseDto
import by.g_alex.mobile_iis.presentation.announcement_screen.AnnouncementScreen
import by.g_alex.mobile_iis.presentation.dormitory_screen.DormitoryScreen
import by.g_alex.mobile_iis.presentation.penalty_screen.FinesScreen
import by.g_alex.mobile_iis.presentation.grade_book_screen.RatingScreen
import by.g_alex.mobile_iis.presentation.login_screen.LoginScreen
import by.g_alex.mobile_iis.presentation.login_screen.restore_password_screen.enter_login.RestorePasswordEnterLogin
import by.g_alex.mobile_iis.presentation.login_screen.restore_password_screen.restore_end.RestorePasswordEndScreen
import by.g_alex.mobile_iis.presentation.login_screen.restore_password_screen.select_how_restore.RestorePasswordSelect
import by.g_alex.mobile_iis.presentation.mark_book.MarkBookScreen
import by.g_alex.mobile_iis.presentation.omissions_screen.OmissionsScreen
import by.g_alex.mobile_iis.presentation.profile_screen.ProfileCVScreen
import by.g_alex.mobile_iis.presentation.schedule.ScheduleStartUp
import by.g_alex.mobile_iis.presentation.study_screen.StudyScreen
import by.g_alex.mobile_iis.presentation.user_group.UserGroupScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NavigationScreen() {
    val tabsItems = listOf<BaseNavItem>(
        BaseNavItem("schedule", "Расписание", R.drawable.schedule_icon),
        BaseNavItem("mark_book", "Зачётка", R.drawable.baseline_book_24),
        BaseNavItem("profile", "Профиль", R.drawable.icon_profile),
        BaseNavItem("grade_book", "Рейтинг", R.drawable.rating),
        BaseNavItem("more", "Ещё", R.drawable.baseline_more_horiz_24),
    )
    val selectedItem = remember { mutableStateOf(0) }
    val navController = rememberNavController()

    val scope = rememberCoroutineScope()
    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContent = {
            BottomMenuMore(navController, scope, bottomSheetState, selectedItem)
        },
        sheetShape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
        sheetElevation = 12.dp
    ) {
        Scaffold(
            bottomBar = {
                NavigationBar {
                    tabsItems.forEachIndexed { index, item ->
                        NavigationBarItem(
                            selected = selectedItem.value == index,
                            onClick = {
                                if (item.route != "more") {
                                    selectedItem.value = index
                                    navController.navigate(item.route, navOptions {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                    })
                                } else {
                                    scope.launch {
                                        bottomSheetState.show()
                                    }
                                }
                            },
                            icon = {
                                Icon(
                                    painter = painterResource(id = item.icon),
                                    contentDescription = item.title
                                )
                            },
                            label = {
                                Text(text = item.title, fontSize = 11.sp)
                            }
                        )
                    }
                }
            }
        ) {
            Box(modifier = Modifier.padding(it)) {
                NavHost(navController = navController, startDestination = "schedule") {
                    navigation(startDestination = "scheduleHome", route = "schedule") {
                        composable(
                            route = "scheduleHome",
                        ) {
                            ScheduleStartUp()
                        }
                    }
                    navigation(startDestination = "profileHome", route = "profile") {
                        composable(
                            route = "profileHome",
                        ) {
                            ProfileCVScreen(navController)
                        }
                        composable(
                            route = "login",
                        ) {
                            LoginScreen(navController = navController)
                        }
                        composable(
                            route = "restorePassword"
                        ) {
                            RestorePasswordEnterLogin(navController = navController)
                        }
                        composable(
                            route = "restorePasswordSelect/{data}/{login}"
                        ) { backStackEntry ->
                            val dataRow = backStackEntry.arguments?.getString("data") ?: ""
                            val data =
                                Json.decodeFromString<RestorePasswordEnterLoginResponseDto>(dataRow)
                            val loginRow = backStackEntry.arguments?.getString("login") ?: ""
                            RestorePasswordSelect(
                                navController = navController,
                                data = data,
                                login = loginRow
                            )
                        }
                        composable(
                            route = "restorePasswordEnd/{data}/{login}"
                        ) { backStackEntry ->
                            val dataRow = backStackEntry.arguments?.getString("data") ?: ""
                            val data =
                                Json.decodeFromString<RestorePasswordEnterLoginResponseDto>(dataRow)
                            val loginRow = backStackEntry.arguments?.getString("login") ?: ""
                            RestorePasswordEndScreen(
                                navController = navController,
                                data = data,
                                login = loginRow
                            )
                        }
                    }
                    navigation(startDestination = "mark_bookHome", route = "mark_book") {
                        composable(route = "mark_bookHome") {
                            MarkBookScreen()
                        }
                    }
                    navigation(startDestination = "grade_bookHome", route = "grade_book") {
                        composable(route = "grade_bookHome") {
                            RatingScreen()
                        }
                    }
                    composable(route = "groupHome") {
                        UserGroupScreen()
                    }
                    composable(route = "omissionsHome") {
                        OmissionsScreen()
                    }
                    composable(route = "studyHome") {
                        StudyScreen()
                    }
                    composable(route = "announcements") {
                        AnnouncementScreen()
                    }
                    composable(route = "dormitory") {
                        DormitoryScreen()
                    }
                    composable(route = "fines") {
                        FinesScreen()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomMenuMore(
    navController: NavHostController,
    scope: CoroutineScope,
    bottomSheetState: ModalBottomSheetState,
    selectedItem: MutableState<Int>
) {
    val listOfItems = listOf<BaseNavItem>(
        BaseNavItem(
            "groupHome",
            "Группа",
            R.drawable.group_icon
        ),
        BaseNavItem(
            "omissionsHome",
            "Пропуски",
            R.drawable.baseline_do_not_disturb_24
        ),
        BaseNavItem(
            "studyHome",
            "Учёба",
            R.drawable.baseline_school_24
        ),
        BaseNavItem(
            "announcements",
            "Объявления",
            R.drawable.baseline_watch_later_24
        ),
        BaseNavItem(
            "dormitory",
            "Общежитие",
            R.drawable.baseline_home_work_24
        ),
        BaseNavItem(
            "fines",
            "Взыскания",
            R.drawable.baseline_report_gmailerrorred_24
        ),

        )
    Box(
        Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth()
            .wrapContentHeight(Alignment.CenterVertically)
    ) {
        Column {
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                items(listOfItems.size) { i ->
                    Column(
                        Modifier
                            .align(Alignment.CenterHorizontally)
                            .clickable {
                                scope.launch {
                                    bottomSheetState.hide()
                                }
                                navController.navigate(listOfItems[i].route, navOptions {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                })
                                selectedItem.value = 4
                            }
                            .padding(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painter = painterResource(id = listOfItems[i].icon),
                            contentDescription = listOfItems[i].title,

                            )
                        Text(text = listOfItems[i].title, fontSize = 12.sp)
                    }
                }
            }
            Spacer(Modifier.height(50.dp))
        }
    }
}

data class BaseNavItem(
    val route: String,
    val title: String,
    val icon: Int,
)