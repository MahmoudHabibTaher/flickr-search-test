package com.bigo.flickrsearch.photos.domain.entities

data class Photo(
    val id: String,
    val owner: String,
    val secret: String,
    val server: String,
    val title: String,
    val medium: PhotoInfo? = null,
    val thumbnail: PhotoInfo? = null
)

data class PhotoInfo(
    val url: String,
    val width: Int? = null,
    val height: Int? = null,
)

data class PhotosPage(
    val page: Int,
    val totalPages: Int,
    val perPage: Int,
    val totalPhotos: Int,
    val photos: List<Photo>,
)