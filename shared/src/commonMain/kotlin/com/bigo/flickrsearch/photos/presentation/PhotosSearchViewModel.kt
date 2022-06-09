package com.bigo.flickrsearch.photos.presentation

import com.bigo.flickrsearch.core.coroutines.CoroutineContextProvider
import com.bigo.flickrsearch.core.presentation.CommonViewModel
import com.bigo.flickrsearch.photos.domain.SearchPhotos
import com.bigo.flickrsearch.photos.domain.SearchQuery
import com.bigo.flickrsearch.photos.domain.entities.PhotosPage

data class PhotosSearchState(
    val loading: Boolean? = null,
    val photosPage: PhotosPage? = null,
    val error: String? = null,
)

class PhotosSearchViewModel(
    coroutineContextProvider: CoroutineContextProvider,
    private val searchPhotos: SearchPhotos,
) : CommonViewModel<PhotosSearchState>(
    PhotosSearchState(),
    coroutineContextProvider,
) {
    fun search(tag: String) {
        launchBackground {
            emit(state.copy(loading = true))
            val photosSearchResult = searchPhotos(SearchQuery(listOf(tag)))
            val newState =
                photosSearchResult.fold(
                    onSuccess = { value ->
                        state.copy(
                            photosPage = value,
                            loading = false,
                            error = null,
                        )
                    },
                    onFailure = { error ->
                        state.copy(
                            error = error.message ?: "Something went wrong",
                            loading = false,
                        )
                    })
            emit(newState)
        }
    }
}