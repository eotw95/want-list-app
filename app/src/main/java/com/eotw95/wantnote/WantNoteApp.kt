package com.eotw95.wantnote

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.eotw95.wantnote.screen.Settings.SettingsScreen
import com.eotw95.wantnote.screen.add.AddWantScreen
import com.eotw95.wantnote.screen.add.AddWantViewModel
import com.eotw95.wantnote.screen.edit.EditWant
import com.eotw95.wantnote.screen.preview.PreviewWantScreen
import com.eotw95.wantnote.screen.splash.SplashScreen
import com.eotw95.wantnote.screen.tabInfo.TabInfoScreen
import com.eotw95.wantnote.screen.tabInfo.TabInfoViewModel
import com.eotw95.wantnote.screen.wantList.WantListScreen
import com.eotw95.wantnote.screen.web.WebWant
import kotlinx.coroutines.CoroutineScope

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun WantNoteApp(
    openMedia: () -> Unit,
    isFirst: Boolean
) {
    val appState = rememberAppState()

    Scaffold(
        scaffoldState = appState.scaffoldState,
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier.statusBarsPadding()
    ) {
        Surface(modifier = Modifier.padding(it)) {
            AppNavigation(
                appState,
                isFirst = isFirst,
                openImageGallery = openMedia
            )
        }
    }
}

@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController(),
    context: Context = LocalContext.current.applicationContext,
    resources: Resources = LocalContext.current.resources,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
) =
    remember(navController, context, resources, coroutineScope, scaffoldState) {
        WantNoteAppState(navController, context, resources, scaffoldState, coroutineScope)
    }

@Composable
fun AppNavigation(
    appState: WantNoteAppState,
    isFirst: Boolean,
    openImageGallery: () -> Unit
) {
    val tabInfoViewModel: TabInfoViewModel = hiltViewModel()
    if (isFirst) tabInfoViewModel.setupDefaultTab()

    NavHost(
        navController = appState.navController,
        startDestination = Screens.Splash.route,
        builder = {
            composable(Screens.Home.route) {
                WantListScreen(
                    onCellClick = {
                            itemId -> appState.navigate(Screens.Preview.route + "/$itemId")
                    },
                    onTabClick = { appState.navigate(Screens.TabInfo.route) },
                    onSettingClick = { appState.navigate(Screens.Settings.route) },
                    onActionButtonClick = { appState.navigate(Screens.Add.route) },
                    tabInfoViewModel = tabInfoViewModel
                )
            }
            composable(Screens.Add.route) {
                AddWantScreen(
                    openAndPopUp = {
                        appState.navigateAndPopUp(Screens.Splash.route, Screens.Add.route)
                    },
                    popUp = { appState.popUp() },
                    onAddImageClick = openImageGallery,
                    tabInfoViewModel = tabInfoViewModel
                )
            }
            composable(Screens.Preview.route + "/{itemId}") {
                it.arguments?.getString("itemId")?.let { itemId ->
                    PreviewWantScreen(
                        id = itemId.toInt(),
                        openAndPopUp = {
                            appState.navigateAndPopUp(Screens.Splash.route, Screens.Preview.route)
                        },
                        popUp = { appState.popUp() },
                        onUrlClick = { url -> appState.navigate(
                            Screens.Web.route + "/$url"
                        ) }
                    )
                }
            }
            composable(Screens.Edit.route) {
                EditWant()
            }
            composable(Screens.Web.route + "/{url}") {
                val url = it.arguments?.getString("url").toString()
                WebWant(url = url, popUp = { appState.popUp() })
            }
            composable(Screens.Splash.route) {
                println("SplashScreen currentDest=${appState.navController.backQueue[appState.navController.backQueue.size - 2].destination}")
                val backQueue = appState.navController.backQueue
                SplashScreen {
                    when (backQueue[backQueue.size - 2].destination.route) {
                        Screens.Home.route -> { appState.navigate(Screens.Home.route) }
                        Screens.TabInfo.route -> { appState.navigate(Screens.TabInfo.route) }
                        else -> { appState.navigate(Screens.Home.route) }
                    }
                }
            }
            composable(Screens.Settings.route) {
                SettingsScreen(
                    openWebView = { url -> appState.navigate(Screens.Web.route + "/$url") },
                    popUp = { appState.popUp() }
                )
            }
            composable(Screens.TabInfo.route) {
                TabInfoScreen(
                    popUp = { appState.popUp() },
                    open = {
                        appState.navigate(Screens.TabInfo.route)
                    },
                    viewModel = tabInfoViewModel
                )
            }
        }
    )
}

sealed class Screens(
    val route: String
) {
    object Home: Screens("home")
    object Add: Screens("add")
    object Preview: Screens("preview")
    object Edit: Screens("edit")
    object Web: Screens("web")
    object Splash: Screens("splash")
    object Settings: Screens("settings")
    object TabInfo: Screens("tabInfo")

    companion object {
        val bottomNavigationItems = listOf(
            Home,
            Settings
        )
    }
}