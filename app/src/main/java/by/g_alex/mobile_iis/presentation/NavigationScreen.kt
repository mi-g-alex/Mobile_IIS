package by.g_alex.mobile_iis.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import androidx.navigation.navOptions
import by.g_alex.mobile_iis.R
import by.g_alex.mobile_iis.presentation.grade_book_screen.RatingScreen
import by.g_alex.mobile_iis.presentation.login_screen.LoginScreen
import by.g_alex.mobile_iis.presentation.mark_book.MarkBookScreen
import by.g_alex.mobile_iis.presentation.profile_screen.ProfileCVScreen
import by.g_alex.mobile_iis.presentation.schedule.ScheduleStartUp
import by.g_alex.mobile_iis.presentation.user_group.UserGroupScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NavigationScreen() {
    val tabsItems = listOf("schedule", "mark_book", "profile", "grade_book", "more")
    val selectedItem = remember { mutableStateOf(0) }
    val navController = rememberNavController()
    /*val navBackStackEntry = navController.currentBackStackEntryAsState()
    val parentRouteName = navBackStackEntry.value?.destination?.route*/


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
                                if (tabsItems[index] != "more") {
                                    selectedItem.value = index
                                    navController.navigate(item, navOptions {
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
                                when (item) {
                                    "schedule" -> Icon(
                                        painter = painterResource(id = R.drawable.schedule_icon),
                                        contentDescription = null
                                    )

                                    "mark_book" -> Icon(
                                        painter = painterResource(id = R.drawable.baseline_book_24),
                                        contentDescription = null
                                    )

                                    "profile" -> Icon(
                                        painter = painterResource(id = R.drawable.icon_profile),
                                        contentDescription = null
                                    )


                                    "grade_book" -> Icon(
                                        painter = painterResource(id = R.drawable.rating),
                                        contentDescription = null,
                                    )

                                    "more" -> Icon(
                                        painter = painterResource(id = R.drawable.baseline_more_horiz_24),
                                        contentDescription = null,
                                    )
                                }
                            },
                            label = {
                                when (item) {
                                    "schedule" -> Text(text = "Расписание", fontSize = 11.sp)

                                    "mark_book" -> Text(text = "Зачётка", fontSize = 11.sp)

                                    "profile" -> Text(text = "Профиль", fontSize = 11.sp)

                                    "grade_book" -> Text(text = "Рейтинг", fontSize = 11.sp)

                                    "more" -> Text(text = "Ещё", fontSize = 11.sp)
                                }
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
                            deepLinks = listOf(NavDeepLink("deeplink://schedule"))
                        ) {
                            ScheduleStartUp()
                        }
                    }
                    navigation(startDestination = "profileHome", route = "profile") {
                        composable(
                            route = "profileHome",
                            deepLinks = listOf(NavDeepLink("deeplink://profile"))
                        ) {
                            ProfileCVScreen(navController)
                        }
                        composable(
                            route = "login",
                            deepLinks = listOf(NavDeepLink("deeplink://login"))
                        ) {
                            LoginScreen(navController = navController)
                        }
                    }
                    navigation(startDestination = "mark_bookHome", route = "mark_book") {
                        composable(
                            route = "mark_bookHome",
                            deepLinks = listOf(NavDeepLink("deeplink://mark_book"))
                        ) {
                            MarkBookScreen()
                        }
                    }
                    navigation(startDestination = "grade_bookHome", route = "grade_book") {
                        composable(
                            route = "grade_bookHome",
                            deepLinks = listOf(NavDeepLink("deeplink://grade_book"))
                        ) {
                            RatingScreen()
                        }
                    }
                    composable(
                        route = "groupHome",
                        deepLinks = listOf(NavDeepLink("deeplink://grade_book"))
                    ) {
                        UserGroupScreen()
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
    selectedItem : MutableState<Int>
) {
    Box(
        Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth()
            .wrapContentHeight(Alignment.CenterVertically)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(4)
        ) {
            item {
                Column(
                    Modifier
                        .align(Alignment.Center)
                        .clickable {
                            scope.launch {
                                bottomSheetState.hide()
                            }
                            navController.navigate("groupHome", navOptions {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                            })
                            selectedItem.value = 4
                        },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_groups_24),
                        contentDescription = null
                    )
                    Text(text = "Группа")
                }
            }
            item {
                Spacer(Modifier.height(50.dp))
            }
        }
    }
}