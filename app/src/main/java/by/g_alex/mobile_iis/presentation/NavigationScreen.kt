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
import by.g_alex.mobile_iis.presentation.bug_report.BugReportScreen
import by.g_alex.mobile_iis.presentation.department_schedule_screen.DepartmentScheduleScreen
import by.g_alex.mobile_iis.presentation.departments.employee_info.DepartmentsEmployeesInfoScreen
import by.g_alex.mobile_iis.presentation.departments.employees_list.DepartmentsEmployeesListScreen
import by.g_alex.mobile_iis.presentation.departments.tree.DepartmentsScreen
import by.g_alex.mobile_iis.presentation.diciplines_screen.DiciplinesScreen
import by.g_alex.mobile_iis.presentation.diploma_screen.DiplomaScreen
import by.g_alex.mobile_iis.presentation.dormitory_screen.DormitoryScreen
import by.g_alex.mobile_iis.presentation.grade_book_screen.RatingScreen
import by.g_alex.mobile_iis.presentation.login_screen.LoginScreen
import by.g_alex.mobile_iis.presentation.login_screen.restore_password_screen.enter_login.RestorePasswordEnterLogin
import by.g_alex.mobile_iis.presentation.login_screen.restore_password_screen.restore_end.RestorePasswordEndScreen
import by.g_alex.mobile_iis.presentation.login_screen.restore_password_screen.select_how_restore.RestorePasswordSelect
import by.g_alex.mobile_iis.presentation.mark_book.MarkBookScreen
import by.g_alex.mobile_iis.presentation.omissions_screen.OmissionsScreen
import by.g_alex.mobile_iis.presentation.penalty_screen.FinesScreen
import by.g_alex.mobile_iis.presentation.phone_number_screen.PhoneNumbersScreen
import by.g_alex.mobile_iis.presentation.profile_screen.ProfileCVScreen
import by.g_alex.mobile_iis.presentation.rating_screen.PersonalRateScreen
import by.g_alex.mobile_iis.presentation.rating_screen.RatingAllScreen
import by.g_alex.mobile_iis.presentation.schedule.ScheduleStartUp
import by.g_alex.mobile_iis.presentation.settings.SettingsScreen
import by.g_alex.mobile_iis.presentation.settings.advance_screens.change_email.ChangeEmailScreen
import by.g_alex.mobile_iis.presentation.settings.advance_screens.change_links.LinksDialog
import by.g_alex.mobile_iis.presentation.settings.advance_screens.change_skills.ChangeSkillsDialog
import by.g_alex.mobile_iis.presentation.students_screen.StudentsScreen
import by.g_alex.mobile_iis.presentation.study_screen.StudyScreen
import by.g_alex.mobile_iis.presentation.study_screen.add_certificates.AddCertificateScreen
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
        BaseNavItem("mark_book", "Зачётка", R.drawable.mark_book_icon),
        BaseNavItem("profile", "Профиль", R.drawable.profile_icon),
        BaseNavItem("grade_book", "Оценки", R.drawable.grade_book_icon),
        BaseNavItem("more", "Ещё", R.drawable.more_icon),
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
                                Text(text = item.title, fontSize = 9.sp)
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
                            selectedItem.value = 0
                        }
                    }
                    navigation(startDestination = "profileHome", route = "profile") {
                        composable(
                            route = "profileHome",
                        ) {
                            ProfileCVScreen(navController)
                            selectedItem.value = 2
                        }
                        composable(
                            route = "login",
                        ) {
                            LoginScreen(navController = navController)
                            selectedItem.value = 2
                        }
                        composable(
                            route = "restorePassword"
                        ) {
                            RestorePasswordEnterLogin(navController = navController)
                            selectedItem.value = 2
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
                            selectedItem.value = 2
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
                            selectedItem.value = 2
                        }
                    }
                    navigation(startDestination = "mark_bookHome", route = "mark_book") {
                        composable(route = "mark_bookHome") {
                            MarkBookScreen()
                            selectedItem.value = 1
                        }
                    }
                    navigation(startDestination = "grade_bookHome", route = "grade_book") {
                        composable(route = "grade_bookHome") {
                            RatingScreen()
                            selectedItem.value = 3
                        }
                    }
                    composable(route = "groupHome") {
                        UserGroupScreen()
                        selectedItem.value = 4
                    }
                    composable(route = "omissionsHome") {
                        OmissionsScreen()
                        selectedItem.value = 4
                    }
                    navigation(route = "studyHome", startDestination = "study") {
                        composable(route = "study") {
                            StudyScreen(navController = navController)
                            selectedItem.value = 4
                        }
                        composable(route = "addCertificate") {
                            AddCertificateScreen(navController)
                            selectedItem.value = 4
                        }
                    }
                    composable(route = "announcements") {
                        AnnouncementScreen()
                        selectedItem.value = 4
                    }
                    composable(route = "dormitory") {
                        DormitoryScreen()
                        selectedItem.value = 4
                    }
                    composable(route = "fines") {
                        FinesScreen()
                        selectedItem.value = 4
                    }
                    composable(route = "diciplines") {
                        DiciplinesScreen()
                        selectedItem.value = 4
                    }
                    composable(route = "diploma") {
                        DiplomaScreen()
                        selectedItem.value = 4
                    }
                    composable(route = "caf-schedule") {
                        DepartmentScheduleScreen()
                        selectedItem.value = 4
                    }
                    composable(route = "phone-numbers"){
                        PhoneNumbersScreen()
                        selectedItem.value = 4
                    }
                    composable(route = "students"){
                        StudentsScreen()
                        selectedItem.value = 4
                    }
                    navigation(startDestination = "departmentsHome", route = "departments") {
                        composable(route = "departmentsHome") {
                            DepartmentsScreen(navController = navController)
                            selectedItem.value = 4
                        }
                        composable(route = "departmentsEmployeesList/{id}/{name}") { it1 ->
                            val number = it1.arguments?.getString("id")?.toInt() ?: 0
                            val name = it1.arguments?.getString("name") ?: ""
                            DepartmentsEmployeesListScreen(navController, number, name)
                            selectedItem.value = 4
                        }
                        composable(route = "departmentsEmployeeInfo/{id}") { it1 ->
                            val id = it1.arguments?.getString("id") ?: ""
                            DepartmentsEmployeesInfoScreen(id)
                            selectedItem.value = 4
                        }
                    }
                    navigation(startDestination = "all-ratingHome", route = "all-rating") {
                        composable(route = "all-ratingHome") {
                            RatingAllScreen(navController = navController)
                            selectedItem.value = 4
                        }
                        composable(route = "personalRating/{number}") { it1 ->
                            val number = it1.arguments?.getString("number") ?: ""
                            PersonalRateScreen(navController = navController, number = number)
                            selectedItem.value = 4
                        }
                    }
                    navigation(startDestination = "settingsHome", route = "settings") {
                        composable(route = "settingsHome") {
                            SettingsScreen(navController = navController)
                            selectedItem.value = 4
                        }
                        composable(route = "changeEmail") {
                            ChangeEmailScreen(navController)
                            selectedItem.value = 4
                        }
                        composable(route = "changeSkills") {
                            ChangeSkillsDialog(navController = navController)
                            selectedItem.value = 4
                        }
                        composable(route = "changeLinks") {
                            LinksDialog(navController = navController)
                            selectedItem.value = 4
                        }
                        composable(route = "bug-report"){
                            BugReportScreen()
                            selectedItem.value = 4
                        }
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
            R.drawable.omissions_icon
        ),
        BaseNavItem(
            "studyHome",
            "Учёба",
            R.drawable.study_icon
        ),
        BaseNavItem(
            "announcements",
            "Объявления",
            R.drawable.announcements_icon
        ),
        BaseNavItem(
            "dormitory",
            "Общежитие",
            R.drawable.dormitory_icon
        ),
        BaseNavItem(
            "fines",
            "Взыскания",
            R.drawable.fines_icon
        ),
        BaseNavItem(
            "diploma",
            "Диплом",
            R.drawable.diploma_icon
        ),
        BaseNavItem(
            "settings",
            "Настройки",
            R.drawable.settings_icon
        ),
        BaseNavItem(
            "caf-schedule",
            "Кафедры",
            R.drawable.caf_schedule_icon
        ),
        BaseNavItem(
            "departments",
            "Отделы",
            R.drawable.departments_icon
        ),
        BaseNavItem(
            "students",
            "Студенты",
            R.drawable.all_students_icon
        ),
        BaseNavItem(
            "all-rating",
            "Рейтинг",
            R.drawable.all_rating_icon
        ),
        BaseNavItem(
            "diciplines",
            "Дисциплины",
            R.drawable.disciplines_icon
        ),
        BaseNavItem(
            "phone-numbers",
            "Справочник",
            R.drawable.phone_info_icon
        ),
        BaseNavItem(
            "bug-report",
            "Баг Репорт",
            R.drawable.bug_icon
        )
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
                        Text(text = listOfItems[i].title, fontSize = 10.sp)
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