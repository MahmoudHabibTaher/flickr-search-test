package com.bigo.flickrsearch.photos.data.remote.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResultRemote(
    val photos: PhotosPageRemote,
)

@Serializable
data class PhotosPageRemote(
    val page: Int,
    @SerialName("pages")
    val totalPages: Int,
    @SerialName("perpage")
    val perPage: Int,
    @SerialName("total")
    val totalPhotos: Int,
    @SerialName("photo")
    val photos: List<PhotoRemote>,
)

@Serializable
data class PhotoRemote(
    val id: String,
    val owner: String,
    val secret: String,
    val title: String,
    val server: String,
    @SerialName("url_m")
    val url: String? = null,
    @SerialName("width_m")
    val width: Int? = null,
    @SerialName("height_m")
    val height: Int? = null,
    @SerialName("url_sq")
    val thumbnailUrl: String? = null,
    @SerialName("widths_q")
    val thumbnailWidth: Int? = null,
    @SerialName("height_sq")
    val thumbnailHeight: Int? = null,
)