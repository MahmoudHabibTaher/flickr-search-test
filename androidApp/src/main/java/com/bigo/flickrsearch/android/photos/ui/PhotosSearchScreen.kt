package com.bigo.flickrsearch.android.photos.ui

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.bigo.flickrsearch.android.R
import com.bigo.flickrsearch.android.navigation.Screens
import com.bigo.flickrsearch.android.ui.composables.CenterLoadingIndicator
import com.bigo.flickrsearch.android.ui.composables.CommonAppBarTitle
import com.bigo.flickrsearch.android.ui.composables.CommonNavigationIcon
import com.bigo.flickrsearch.android.ui.theme.FlickrSearchTheme
import com.bigo.flickrsearch.photos.domain.entities.Photo
import com.bigo.flickrsearch.photos.domain.entities.PhotoInfo
import com.bigo.flickrsearch.photos.presentation.PhotosSearchViewModel
import org.koin.androidx.compose.getViewModel

private const val defaultInitialSearchTag = "Electrolux"

@Composable
fun PhotosSearchScreen(
    navController: NavController,
    photosSearchViewModel: PhotosSearchViewModel = getViewModel(),
    initialSearchTag: String = defaultInitialSearchTag,
) {
    LaunchedEffect(initialSearchTag) {
        photosSearchViewModel.search(initialSearchTag)
    }
    FlickrSearchTheme {
        Scaffold(
            topBar = {
                TopAppBar(title = { CommonAppBarTitle(R.string.app_title) })
            }
        ) { padding ->
            Surface(
                color = MaterialTheme.colors.background,
                modifier = Modifier.padding(padding),
            ) {
                PhotosSearchResult(navController, photosSearchViewModel)
            }
        }
    }
}

@Composable
fun PhotosSearchResult(
    navController: NavController,
    photosSearchViewModel: PhotosSearchViewModel
) {
    val photosSearchState by photosSearchViewModel.stateFlow.collectAsState()

    val loading = photosSearchState.loading ?: false
    val error = photosSearchState.error
    val photosPage = photosSearchState.photosPage

    when {
        loading -> CenterLoadingIndicator()
        error != null -> PhotosLoadError(error)
        photosPage != null -> PhotosList(
            photos = photosPage.photos,
            onPhotoClick = { photo ->
                navController.navigate(Screens.photoDetails(photo.id))
            })
    }
}

@Composable
fun PhotosLoadError(error: String) {
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

@Composable
fun PhotosList(
    photos: List<Photo>,
    onPhotoClick: (Photo) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 128.dp), modifier = modifier) {
        items(photos) { photo ->
            PhotoItem(
                photo,
                onPhotoClick = onPhotoClick,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun PhotoItem(
    photo: Photo,
    onPhotoClick: (Photo) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.clickable {
            onPhotoClick(photo)
        },
        elevation = 2.dp
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            SubcomposeAsyncImage(
                model = smallPhotoImageRequest(photo, LocalContext.current),
                contentDescription = null,
                loading = { CenterLoadingIndicator() },
                error = { PhotoLoadError() },
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )
        }
    }
}

fun smallPhotoImageRequest(photo: Photo, context: Context) =
    ImageRequest.Builder(context)
        .data(photo.thumbnail?.url ?: photo.medium?.url)
        .crossfade(true)
        .build()

@Composable
fun PhotoLoadError() {
    Image(
        painter = painterResource(id = R.drawable.ic_baseline_error_outline_24),
        contentDescription = null,
        contentScale = ContentScale.Inside,
        modifier = Modifier.size(56.dp),
        colorFilter = ColorFilter.tint(Color.Red)
    )
}

@Preview(showBackground = true, heightDp = 100)
@Composable
fun PhotosLoadErrorPreview() {
    FlickrSearchTheme {
        PhotosLoadError(error = "Something went wrong!")
    }
}

@Preview
@Composable
fun PhotoItemPreview() {
    FlickrSearchTheme {
        PhotoItem(
            photo = Photo(
                id = "",
                owner = "",
                secret = "",
                title = "Electrolux image",
                server = "",
                medium = PhotoInfo(url = "https://picsum.photos/300/460"),
                thumbnail = PhotoInfo(url = "https://picsum.photos/300/460"),
            ),
            onPhotoClick = {}
        )
    }
}

@Preview(showBackground = true, widthDp = 128, heightDp = 128)
@Composable
fun PhotoLoadPlaceholderPreview() {
    FlickrSearchTheme {
        CenterLoadingIndicator()
    }
}

@Preview(showBackground = true)
@Composable
fun PhotoLoadErrorPreview() {
    FlickrSearchTheme {
        PhotoLoadError()
    }
}