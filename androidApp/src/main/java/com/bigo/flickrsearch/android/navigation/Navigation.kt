package com.bigo.flickrsearch.android.navigation

object Screens {
    const val photosSearch = "photosSearch"
    const val photoDetails = "photoDetails/{photoId}"
    fun photoDetails(photoId: String) = photoDetails.replace("{photoId}", photoId)
}