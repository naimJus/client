package com.example.bankclients.ui.list

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.domain.model.UserItem
import kotlinx.coroutines.flow.StateFlow

@Composable
fun MainScreen(uiStateFlow: StateFlow<UiState>, errorStateFlow: StateFlow<Alert?>) {
    val showDialog: MutableState<Alert?> = remember { mutableStateOf(null) }
    val ui: UiState by uiStateFlow.collectAsState()

    LaunchedEffect(Unit) {
        errorStateFlow.collect {
            showDialog.value = it
        }
    }

    when (val res = ui) {
        is UiState.Items -> Items(itemsState = res)
        UiState.Loading -> LoadingView()
        is UiState.Toast -> TODO()
    }

    val error = showDialog.value
    if (error != null) {
        CustomDialog(error) {
            showDialog.value = null
        }
    }
}

@Composable
fun LoadingView() {
    Box(contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Items(itemsState: UiState.Items) {
    val data = itemsState.users
    LazyColumn(Modifier.padding(vertical = 8.dp)) {
        items(items = data, key = { item -> item.id }) { user ->
            UserItem(Modifier.animateItemPlacement(), user)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserItem(modifier: Modifier, item: UserItem) {
    var expanded by remember { mutableStateOf(false) }

    Surface(
        onClick = { expanded = !expanded },
        modifier = modifier
            .animateContentSize(animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow))
            .padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 24.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Details(expanded = expanded, primary = item.name, item.email, item.userName, item.phone, item.company, item.address)
        }
    }
}

@Composable
fun RowScope.Details(expanded: Boolean, primary: String, vararg details: String) {
    Column(
        Modifier
            .weight(1f)
            .padding(horizontal = 16.dp)
    ) {
        Text(text = primary, maxLines = 1, overflow = TextOverflow.Ellipsis, style = MaterialTheme.typography.bodyLarge)
        if (expanded) {
            for (detail in details) {
                Text(text = detail, style = MaterialTheme.typography.bodyMedium)
            }
        } else {
            Text(text = details.first(), style = MaterialTheme.typography.bodyMedium, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
    }
}

@Composable
fun CustomDialog(alertState: Alert, onclick: () -> Unit) {
    AlertDialog(
        onDismissRequest = { onclick.invoke() },
        title = { Text(text = stringResource(id = alertState.title)) },
        text = { Text(text = stringResource(id = alertState.message)) },
        confirmButton = {
            TextButton(onClick = {
                onclick.invoke()
            }) {
                Text(stringResource(id = alertState.button))
            }
        }
    )
}