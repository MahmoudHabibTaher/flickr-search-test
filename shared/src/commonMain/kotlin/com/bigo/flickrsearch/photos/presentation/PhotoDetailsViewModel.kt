package com.bigo.flickrsearch.photos.presentation

import com.bigo.flickrsearch.core.coroutines.CoroutineContextProvider
import com.bigo.flickrsearch.core.presentation.CommonViewModel
import com.bigo.flickrsearch.photos.domain.FindPhoto
import com.bigo.flickrsearch.photos.domain.entities.Photo

data class PhotoDetailsState(
    val loading: Boolean? = null,
    val photo: Photo? = null,
    val error: String? = null,
)

class PhotoDetailsViewModel(
    coroutineContextProvider: CoroutineContextProvider,
    private val findPhoto: FindPhoto,
) : CommonViewModel<PhotoDetailsState>(PhotoDetailsState(), coroutineContextProvider) {
    fun loadPhoto(photoId: String?) {
        launchBackground {
            emit(state.copy(loading = true, photo = null, error = null))
            val result = findPhoto(photoId)
            val newState = result.fold(onSuccess = { photo ->
                state.copy(loading = false, photo = photo, error = null)
            }, onFailure = { error ->
                state.copy(loading = false, photo = null, error = error.message)
            })
            emit(newState)
        }
    }
}