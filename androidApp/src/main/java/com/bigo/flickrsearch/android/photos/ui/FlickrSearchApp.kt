package com.bigo.flickrsearch.android.photos.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bigo.flickrsearch.android.navigation.Screens

@Composable
fun FlickrSearchApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.photosSearch) {
        composable(Screens.photosSearch) {
            PhotosSearchScreen(
                navController = navController,
            )
        }

        composable(Screens.photoDetails) { backStackEntry ->
            val photoId = backStackEntry.arguments?.getString("photoId")
            PhotoDetailsScreen(
                navController = navController,
                photoId = photoId,
            )
        }
    }
}