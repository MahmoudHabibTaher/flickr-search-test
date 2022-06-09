package com.bigo.flickrsearch.photos.domain

import com.bigo.flickrsearch.core.domain.UseCaseWithInput
import com.bigo.flickrsearch.photos.domain.entities.Photo

class FindPhoto(
    private val photosDataSource: PhotosDataSource,
) : UseCaseWithInput<String?, Photo?> {
    override suspend fun invoke(input: String?): Result<Photo?> =
        photosDataSource.loadPhoto(input)
}