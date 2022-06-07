package com.bigo.flickrsearch.photos.domain.entities

data class PhotosPage(
    val page: Int,
    val totalPages: Int,
    val perPage: Int,
    val totalPhotos: Int,
    val photos: List<Photo>,
)
