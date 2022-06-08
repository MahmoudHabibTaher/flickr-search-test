package com.bigo.flickrsearch.photos.data

import com.bigo.flickrsearch.photos.domain.PhotosDataSource
import com.bigo.flickrsearch.photos.domain.SearchQuery
import com.bigo.flickrsearch.photos.domain.entities.Photo
import com.bigo.flickrsearch.photos.domain.entities.PhotosPage
import kotlinx.coroutines.delay

class PhotosRepository : PhotosDataSource {
    override suspend fun findPhotos(query: SearchQuery): Result<PhotosPage> {
        delay(1000)
        return Result.success(
            PhotosPage(
                page = 1,
                totalPages = 3,
                perPage = 21,
                totalPhotos = 21 * 3,
                photos = listOf(
                    Photo(
                        id = "PhotoId",
                        owner = "PhotoOwner",
                        secret = "PhotoSecret",
                        title = "Electrolux image",
                        url = "https://picsum.photos/300/460",
                        width = 300,
                        height = 460,
                    )
                ),
            )
        )
    }
}