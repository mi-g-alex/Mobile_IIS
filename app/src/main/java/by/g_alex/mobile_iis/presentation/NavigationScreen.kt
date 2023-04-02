package by.g_alex.mobile_iis.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import androidx.navigation.navOptions
import by.g_alex.mobile_iis.R
import by.g_alex.mobile_iis.presentation.login_screen.LoginScreen
import by.g_alex.mobile_iis.presentation.profile_screen.ProfileCVScreen
import by.g_alex.mobile_iis.presentation.schedule.ScheduleStartUp

@Composable
fun NavigationScreen() {
    val tabsItems = listOf("Расписание", "Профиль")
    val selectedItem = remember { mutableStateOf(0) }
    val navController = rememberNavController()
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val parentRouteName = navBackStackEntry.value?.destination?.route

    Scaffold(
        bottomBar = {
            NavigationBar {
                tabsItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = parentRouteName == item,
                        onClick = {
                            selectedItem.value = index
                            navController.navigate( item, navOptions {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            )
                        },
                        icon = {
                            when (item) {
                                "Расписание" -> Icon(
                                    painter = painterResource(id = R.drawable.schedule_icon),
                                    contentDescription = null
                                )

                                "Профиль" -> Icon(
                                    painter = painterResource(id = R.drawable.icon_profile),
                                    contentDescription = null
                                )
                            }
                        },
                        label = { Text(text = item) }
                    )
                }
            }
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            NavHost(navController = navController, startDestination = "Расписание") {
                navigation(startDestination = "scheduleHome", route = "Расписание") {
                    composable(
                        route = "scheduleHome",
                        deepLinks = listOf(NavDeepLink("deeplink://schedule"))
                    ) {
                        ScheduleStartUp()
                    }
                }
                navigation(startDestination = "profileHome", route = "Профиль") {
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
            }
        }
    }
}