package com.bigo.flickrsearch.photos.data.remote

import com.bigo.flickrsearch.core.network.FlickrApiSettings
import com.bigo.flickrsearch.photos.data.remote.entities.SearchResultRemote
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

interface PhotosApi {
    suspend fun searchPhotos(
        tags: List<String>,
        page: Int,
        perPage: Int,
    ): Result<SearchResultRemote>
}

internal class PhotosKtorApi(
    private val apiSettings: FlickrApiSettings,
    private val httpClient: HttpClient,
) : PhotosApi {
    override suspend fun searchPhotos(
        tags: List<String>,
        page: Int,
        perPage: Int,
    ): Result<SearchResultRemote> =
        runCatching {
            httpClient.get(apiSettings.baseUrl) {
                url {
                    with(parameters) {
                        append("api_key", apiSettings.apiKey)
                        append("method", "flickr.photos.search")
                        append("tags", tags.joinToString(","))
                        append("format", "json")
                        append("nojsoncallback", "true")
                        append("media", "1")
                        append("extras", "url_m,media,url_q")
                        append("per_page", perPage.toString())
                        append("page", page.toString())
                    }
                }
            }.body()
        }

}