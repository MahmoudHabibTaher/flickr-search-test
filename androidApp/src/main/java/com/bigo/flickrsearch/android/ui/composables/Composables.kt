package com.bigo.flickrsearch.android.ui.composables

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@Composable
fun CommonAppBarTitle(@StringRes title: Int) {
    Text(text = stringResource(id = title))
}

@Composable
fun CommonNavigationIcon(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(Icons.Filled.ArrowBack, contentDescription = null)
    }
}

@Composable
fun CenterLoadingIndicator() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}