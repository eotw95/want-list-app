package com.eotw95.wantnote

import android.content.Context
import android.content.res.Resources
import androidx.compose.material.ScaffoldState
import androidx.navigation.NavHostController
import com.eotw95.wantnote.common.snackbar.SnackBarManager
import com.eotw95.wantnote.common.snackbar.SnackBarMessage.Companion.toMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class WantNoteAppState(
    val navController: NavHostController,
    val context: Context,
    val resources: Resources,
    val scaffoldState: ScaffoldState,
    coroutineScope: CoroutineScope
) {
    init {
        coroutineScope.launch {
            SnackBarManager.messages.collect { message ->
                message?.let {
                    scaffoldState.snackbarHostState.showSnackbar(it.toMessage(resources))
                }
            }
        }
    }
    fun navigate(route: String) = navController.navigate(route) { launchSingleTop = true }
    fun popUp() = navController.popBackStack()
    fun navigateAndPopUp(navRoute: String, popRoute: String) {
        navController.navigate(navRoute) {
            launchSingleTop = true
            popUpTo(popRoute) { inclusive = true }
        }
    }
}