package com.eotw95.wantnote.common.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.eotw95.wantnote.R
import com.eotw95.wantnote.Screens

@Composable
fun BasicToolBar(
    title: Int = R.string.text_empty,
    navIcon: ImageVector,
    navAction: () -> Unit,
    action: @Composable RowScope.() -> Unit
) {
    TopAppBar(
        title = { Text(text = stringResource(id = title)) },
        navigationIcon = {
            Icon(
                imageVector = navIcon,
                contentDescription = null,
                modifier = Modifier.clickable { navAction() }.size(30.dp)
            )
        },
        actions = action,
        elevation = 0.dp,
        modifier = Modifier.statusBarsPadding().padding(horizontal = 10.dp)
    )
}

@Composable
fun TransparentToolBar(
    title: Int = R.string.text_empty,
    navIcon: ImageVector,
    navAction: () -> Unit,
    action: @Composable RowScope.() -> Unit
) {
    TopAppBar(
        title = { Text(text = stringResource(id = title)) },
        navigationIcon = {
            Surface(
                color = Color.Transparent,
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colors.onBackground.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(50.dp)).padding(10.dp)
            ) {
                Icon(
                    imageVector = navIcon,
                    contentDescription = null,
                    modifier = Modifier.clickable { navAction() }.size(30.dp),
                    tint = MaterialTheme.colors.primary
                )
            }
        },
        actions = action,
        elevation = 0.dp,
        backgroundColor = Color.Transparent,
        modifier = Modifier.statusBarsPadding().padding(horizontal = 10.dp)
    )
}

@Composable
fun TopToolBar(
    title: Int = R.string.text_empty,
    action: @Composable RowScope.() -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = title),
                fontSize = 25.sp,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold
            )
        },
        actions = action,
        elevation = 0.dp,
        modifier = Modifier.statusBarsPadding()
    )
}

@Composable
fun BasicNavigationBar(
    navController: NavHostController,
    openAndPopUp: (String, String) -> Unit
) {
    val navBackStackEntry = navController.currentBackStackEntry
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation {
        Screens.bottomNavigationItems.forEach { item ->
            BottomNavigationItem(
                selected = currentDestination?.route == item.route,
                onClick = {
                    currentDestination?.let {
                        if (it.route != item.route) {
                            openAndPopUp(item.route, it.route!!)
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = when (item.route) {
                            Screens.Home.route -> Icons.Filled.Home
                            Screens.Settings.route -> Icons.Filled.Settings
                            else -> throw IllegalArgumentException("Invalid icon")
                        },
                        contentDescription = null
                    )
                }
            )
        }
    }
}