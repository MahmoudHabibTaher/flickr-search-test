package com.bigo.flickrsearch.photos.domain

import com.bigo.flickrsearch.core.domain.UseCaseWithInput
import com.bigo.flickrsearch.photos.domain.entities.PhotosPage

data class SearchQuery(val tags: List<String>)

class SearchPhotos(
    private val photosDataSource: PhotosDataSource,
) : UseCaseWithInput<SearchQuery, PhotosPage> {
    override suspend fun invoke(input: SearchQuery): Result<PhotosPage> =
        photosDataSource.findPhotos(input)
}