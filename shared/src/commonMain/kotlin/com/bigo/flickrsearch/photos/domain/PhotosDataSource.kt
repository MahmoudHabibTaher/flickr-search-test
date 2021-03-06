package com.bigo.flickrsearch.photos.domain

import com.bigo.flickrsearch.photos.domain.entities.Photo
import com.bigo.flickrsearch.photos.domain.entities.PhotosPage

interface PhotosDataSource {
    suspend fun findPhotos(query: SearchQuery): Result<PhotosPage>
    suspend fun loadPhoto(photoId: String?): Result<Photo?>
}