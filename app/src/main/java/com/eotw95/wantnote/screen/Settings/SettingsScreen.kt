package com.eotw95.wantnote.screen.Settings

import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.eotw95.wantnote.R
import com.eotw95.wantnote.URL_PRIVACY_POLICY
import com.eotw95.wantnote.common.composable.BasicScrollableColumn
import com.eotw95.wantnote.common.composable.BasicToolBar
import com.eotw95.wantnote.common.composable.RowHaveClickAction

@Composable
fun SettingsScreen(
    openWebView: (String) -> Unit,
    popUp: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    SettingsScreenContent(
        onClickPrivacyPolicy = { viewModel.openWebView(URL_PRIVACY_POLICY, openWebView) },
        popUp = popUp
    )
}

@Composable
fun SettingsScreenContent(
    onClickPrivacyPolicy: () -> Unit,
    popUp: () -> Unit
) {
    BasicScrollableColumn {
        BasicToolBar(navIcon = Icons.Filled.KeyboardArrowLeft, navAction = popUp) { /* do nothing */ }
//        ListItem(label = stringResource(id = R.string.theme)) { /*Todo*/ }
//        ListItem(label = stringResource(id = R.string.contact)) { /*Todo*/ }
        ListItem(label = stringResource(id = R.string.link_privacy_policy)) { onClickPrivacyPolicy() }
    }
}

@Composable
fun ListItem(label: String, onClick: () -> Unit) {
    RowHaveClickAction(onClick = onClick) {
        androidx.compose.material.Text(text = label)
        Icon(imageVector = Icons.Filled.KeyboardArrowRight, contentDescription = null)
    }
    Divider(thickness = 0.5.dp)
}