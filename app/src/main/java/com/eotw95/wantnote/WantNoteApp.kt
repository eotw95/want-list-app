package com.eotw95.wantnote

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarHost
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.eotw95.wantnote.screen.add.AddWantScreen
import com.eotw95.wantnote.screen.add.AddWantViewModel
import com.eotw95.wantnote.screen.edit.EditWant
import com.eotw95.wantnote.screen.preview.PreviewWantScreen
import com.eotw95.wantnote.screen.splash.SplashScreen
import com.eotw95.wantnote.screen.wantList.WantListScreen
import com.eotw95.wantnote.screen.web.WebWant
import kotlinx.coroutines.CoroutineScope

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun WantNoteApp(
    openMedia: () -> Unit,
    viewModel: AddWantViewModel = hiltViewModel()
) {
    val appState = rememberAppState()

    Scaffold(
        scaffoldState = appState.scaffoldState,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onAddImageClick(null, appState.context)
                    appState.navigate(Screens.Add.route)
                }
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null)
            }
        }
    ) {
        Spacer(modifier = Modifier.padding(it))
        AppNavigation(
            appState,
            openImageGallery = openMedia
        )
    }
}

@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController(),
    context: Context = LocalContext.current.applicationContext,
    resources: Resources = LocalContext.current.resources,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    scaffoldState: ScaffoldState = rememberScaffoldState()
) =
    remember(navController, context, resources, coroutineScope, scaffoldState) {
        WantNoteAppState(navController, context, resources, scaffoldState, coroutineScope)
    }

@Composable
fun AppNavigation(
    appState: WantNoteAppState,
    openImageGallery: () -> Unit
) {
    NavHost(
        navController = appState.navController,
        startDestination = Screens.Splash.route,
        builder = {
            composable(Screens.Home.route) {
                WantListScreen(
                    onCellClick = {
                            itemId -> appState.navigate(Screens.Preview.route + "/$itemId")
                    }
                )
            }
            composable(Screens.Add.route) {
                AddWantScreen(
                    openAndPopUp = {
                        appState.navigateAndPopUp(Screens.Splash.route, Screens.Add.route)
                    },
                    onAddImageClick = openImageGallery
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
                WebWant(url = url)
            }
            composable(Screens.Splash.route) {
                SplashScreen { appState.navigate(Screens.Home.route) }
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
}