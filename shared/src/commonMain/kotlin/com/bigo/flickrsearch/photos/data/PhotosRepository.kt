package com.bigo.flickrsearch.photos.data

import com.bigo.flickrsearch.core.entities.EntityMapper
import com.bigo.flickrsearch.photos.data.remote.PhotosApi
import com.bigo.flickrsearch.photos.data.remote.entities.PhotoRemote
import com.bigo.flickrsearch.photos.data.remote.entities.PhotosPageRemote
import com.bigo.flickrsearch.photos.domain.PhotosDataSource
import com.bigo.flickrsearch.photos.domain.SearchQuery
import com.bigo.flickrsearch.photos.domain.entities.Photo
import com.bigo.flickrsearch.photos.domain.entities.PhotoInfo
import com.bigo.flickrsearch.photos.domain.entities.PhotosPage

class PhotosRepository(
    private val photosApi: PhotosApi,
) : PhotosDataSource {

    private val photos = mutableSetOf<Photo>()

    override suspend fun findPhotos(query: SearchQuery): Result<PhotosPage> =
        photosApi.searchPhotos(
            tags = query.tags,
            page = 1,
            perPage = 21
        ).map { result -> result.photos }.map(photosPageRemoteMapper).also { result ->
            photos.clear()
            result.map { photos.addAll(it.photos) }
        }

    override suspend fun loadPhoto(photoId: String?): Result<Photo?> =
        runCatching { photos.find { it.id == photoId } }
}

internal val photosPageRemoteMapper: EntityMapper<PhotosPageRemote, PhotosPage> =
    { photosPageRemote ->
        PhotosPage(
            page = photosPageRemote.page,
            perPage = photosPageRemote.perPage,
            totalPhotos = photosPageRemote.totalPhotos,
            totalPages = photosPageRemote.totalPages,
            photos = photosPageRemote.photos.map(photoRemoteMapper)
        )
    }

internal val photoRemoteMapper: EntityMapper<PhotoRemote, Photo> = { photoRemote ->
    Photo(
        id = photoRemote.id,
        title = photoRemote.title,
        owner = photoRemote.owner,
        secret = photoRemote.secret,
        server = photoRemote.server,
        medium = photoRemote.getPhotoMediumInfo(),
        thumbnail = photoRemote.getPhotoThumbnailInfo(),
    )
}

private fun PhotoRemote.getPhotoMediumInfo(): PhotoInfo? =
    url?.let { url -> PhotoInfo(url = url, width = width, height = height) }

private fun PhotoRemote.getPhotoThumbnailInfo(): PhotoInfo? =
    thumbnailUrl?.let { url ->
        PhotoInfo(
            url = url,
            width = thumbnailWidth,
            height = thumbnailHeight
        )
    }