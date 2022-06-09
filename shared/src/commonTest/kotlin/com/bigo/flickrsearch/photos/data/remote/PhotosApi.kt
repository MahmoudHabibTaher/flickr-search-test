package com.bigo.flickrsearch.photos.data.remote

import com.bigo.flickrsearch.photos.data.remote.entities.SearchResultRemote

class MockPhotosApi(
    var result: Result<SearchResultRemote>? = null
) : PhotosApi {

    override suspend fun searchPhotos(
        tags: List<String>,
        page: Int,
        perPage: Int
    ): Result<SearchResultRemote> = result!!
}