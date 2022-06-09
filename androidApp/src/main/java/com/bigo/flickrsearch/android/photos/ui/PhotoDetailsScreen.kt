package com.bigo.flickrsearch.android.photos.ui

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.bigo.flickrsearch.android.R
import com.bigo.flickrsearch.android.ui.composables.CenterLoadingIndicator
import com.bigo.flickrsearch.android.ui.composables.CommonAppBarTitle
import com.bigo.flickrsearch.android.ui.composables.CommonNavigationIcon
import com.bigo.flickrsearch.android.ui.theme.FlickrSearchTheme
import com.bigo.flickrsearch.photos.domain.entities.Photo
import com.bigo.flickrsearch.photos.presentation.PhotoDetailsViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun PhotoDetailsScreen(
    navController: NavController,
    photoId: String?,
    photoDetailsViewModel: PhotoDetailsViewModel = getViewModel(),
) {
    LaunchedEffect(photoId) {
        photoDetailsViewModel.loadPhoto(photoId)
    }
    FlickrSearchTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { CommonAppBarTitle(R.string.photo_details_title) },
                    navigationIcon = { CommonNavigationIcon(onClick = { navController.popBackStack() }) },
                )
            },
        ) { padding ->
            Surface(
                color = MaterialTheme.colors.background,
                modifier = Modifier.padding(padding),
            ) {
                val photoDetailsState by photoDetailsViewModel.stateFlow.collectAsState()

                val loading = photoDetailsState.loading ?: false
                val error = photoDetailsState.error
                val photo = photoDetailsState.photo

                when {
                    loading -> CenterLoadingIndicator()
                    error != null -> PhotoLoadError(error)
                    photo != null -> PhotoDetails(photo, onDownloadClick = {})
                    else -> PhotoNotFound(
                        onGoBackClick = { navController.popBackStack() },
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
fun PhotoDetails(
    photo: Photo,
    onDownloadClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        SubcomposeAsyncImage(
            model = largePhotoImageRequest(photo, LocalContext.current),
            contentDescription = null,
            loading = { CenterLoadingIndicator() },
            error = { PhotoLoadError() },
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        )

        Text(
            text = photo.title,
            style = MaterialTheme.typography.h6,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onDownloadClick, modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(text = stringResource(id = R.string.download))
        }
    }
}

@Composable
fun PhotoNotFound(
    onGoBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Text(text = stringResource(id = R.string.photo_not_found))
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onGoBackClick) {
            Text(text = stringResource(id = R.string.go_back))
        }
    }
}

@Composable
fun PhotoLoadError(error: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = error,
            style = MaterialTheme.typography.subtitle1.copy(
                color = Color.Red
            ),
            textAlign = TextAlign.Center
        )
    }
}

fun largePhotoImageRequest(photo: Photo, context: Context) =
    ImageRequest.Builder(context)
        .data(photo.medium?.url ?: photo.thumbnail?.url)
        .crossfade(true)
        .build()

@Preview(showBackground = true)
@Composable
fun PhotoNotFoundPreview() {
    FlickrSearchTheme {
        PhotoNotFound(onGoBackClick = {})
    }
}