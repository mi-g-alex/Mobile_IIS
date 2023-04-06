package by.g_alex.mobile_iis.presentation

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import androidx.navigation.navOptions
import by.g_alex.mobile_iis.R
import by.g_alex.mobile_iis.presentation.login_screen.LoginScreen
import by.g_alex.mobile_iis.presentation.mark_book.MarkBookScreen
import by.g_alex.mobile_iis.presentation.profile_screen.ProfileCVScreen
import by.g_alex.mobile_iis.presentation.schedule.ScheduleStartUp
import by.g_alex.mobile_iis.presentation.schedule.additional_views.BottomSheet
import by.g_alex.mobile_iis.presentation.user_group.UserGroupScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NavigationScreen() {
    val tabsItems = listOf("schedule", "mark_book", "profile", "grade_book", "more")
    val selectedItem = remember { mutableStateOf(0) }
    val navController = rememberNavController()
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val parentRouteName = navBackStackEntry.value?.destination?.route


    val scope = rememberCoroutineScope()
    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContent = {
            BottomMenuMore()
        },
        sheetShape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
        sheetElevation = 12.dp
    ) {
        Scaffold(
            bottomBar = {
                NavigationBar {
                    tabsItems.forEachIndexed { index, item ->
                        NavigationBarItem(
                            selected = parentRouteName == item,
                            onClick = {
                                selectedItem.value = index
                                if (tabsItems[index] != "more") {
                                    navController.navigate(item, navOptions {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
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
                                        painter = painterResource(id = R.drawable.baseline_more_horiz_24),
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
                                    "schedule" -> Text(text = "Расписание")

                                    "mark_book" -> Text(text = "Зачётка")

                                    "profile" -> Text(text = "Профиль")

                                    "grade_book" -> Text(text = "Рейтинг")

                                    "more" -> Text(text = "Ещё")
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
                            UserGroupScreen()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomMenuMore() {
    Box(
        Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth()
            .wrapContentHeight(Alignment.CenterVertically)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(4)
        ) {
            for (i in 1..14)
                item {
                    Column(
                        Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_groups_24),
                            contentDescription = null
                        )
                        Text(text = "group")
                    }
                }
            item {
                Spacer(Modifier.height(50.dp))
            }
        }
    }
}