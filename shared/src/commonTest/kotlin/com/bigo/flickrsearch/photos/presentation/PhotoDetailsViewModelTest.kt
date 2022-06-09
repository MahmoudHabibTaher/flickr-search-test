package com.bigo.flickrsearch.photos.presentation

import app.cash.turbine.test
import com.bigo.flickrsearch.core.coroutines.TestCoroutineContextProvider
import com.bigo.flickrsearch.photos.data.PhotosRepository
import com.bigo.flickrsearch.photos.data.remote.MockPhotosApi
import com.bigo.flickrsearch.photos.data.remote.entities.PhotoRemote
import com.bigo.flickrsearch.photos.data.remote.entities.PhotosPageRemote
import com.bigo.flickrsearch.photos.data.remote.entities.SearchResultRemote
import com.bigo.flickrsearch.photos.domain.FindPhoto
import com.bigo.flickrsearch.photos.domain.SearchPhotos
import com.bigo.flickrsearch.photos.domain.SearchQuery
import com.bigo.flickrsearch.photos.domain.entities.Photo
import com.bigo.flickrsearch.photos.domain.entities.PhotoInfo
import com.bigo.flickrsearch.photos.domain.entities.PhotosPage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class PhotoDetailsViewModelTest {

    private val mainThreadSurrogate = newSingleThreadContext("UI Thread")
    private val coroutineContextProvider = TestCoroutineContextProvider(mainThreadSurrogate)
    private lateinit var mockApi: MockPhotosApi
    private lateinit var photosRepository: PhotosRepository


    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        mockApi = MockPhotosApi()
        photosRepository = PhotosRepository(mockApi)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `View model should start with empty initial state`() = runTest {
        mockApi.result = Result.failure(Exception())
        val viewModel = PhotoDetailsViewModel(
            coroutineContextProvider,
            findPhoto = FindPhoto(photosRepository)
        )
        assertEquals(viewModel.state, PhotoDetailsState())
    }

    @Test
    fun `When load photo called first emit loading`() = runTest {
        mockApi.result =
            Result.success(SearchResultRemote(PhotosPageRemote(0, 0, 0, 0, emptyList())))
        val viewModel = PhotoDetailsViewModel(
            coroutineContextProvider,
            findPhoto = FindPhoto(photosRepository)
        )
        viewModel.stateFlow.test {
            awaitItem()
            viewModel.loadPhoto("id")
            assertEquals(PhotoDetailsState(loading = true), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `When load photo success emit photo`() = runTest {
        val expectedPhoto = Photo(
            id = "1",
            owner = "2",
            secret = "3",
            server = "3",
            title = "photo",
            medium = PhotoInfo(url = "MediumUrl", width = 100, height = 100),
            thumbnail = PhotoInfo(url = "ThumbnailUrl", width = 50, height = 50),
        )

        mockApi.result = Result.success(
            SearchResultRemote(
                PhotosPageRemote(
                    page = 1,
                    perPage = 21,
                    totalPages = 1,
                    totalPhotos = 21,
                    photos = listOf(
                        PhotoRemote(
                            id = "1",
                            owner = "2",
                            secret = "3",
                            server = "3",
                            title = "photo",
                            url = "MediumUrl",
                            width = 100,
                            height = 100,
                            thumbnailUrl = "ThumbnailUrl",
                            thumbnailHeight = 50,
                            thumbnailWidth = 50,
                        )
                    )
                )
            )
        )
        photosRepository.findPhotos(SearchQuery(listOf("tag")))
        val viewModel = PhotoDetailsViewModel(
            coroutineContextProvider,
            findPhoto = FindPhoto(photosRepository)
        )
        viewModel.stateFlow.test {
            awaitItem()
            viewModel.loadPhoto("1")
            awaitItem()
            assertEquals(
                PhotoDetailsState(
                    photo = expectedPhoto,
                    loading = false,
                    error = null
                ), awaitItem()
            )
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `When load photo called emit photo as null not found`() = runTest {
        val expectedPhoto = Photo(
            id = "1",
            owner = "2",
            secret = "3",
            server = "3",
            title = "photo",
            medium = PhotoInfo(url = "MediumUrl", width = 100, height = 100),
            thumbnail = PhotoInfo(url = "ThumbnailUrl", width = 50, height = 50),
        )

        mockApi.result = Result.success(
            SearchResultRemote(
                PhotosPageRemote(
                    page = 1,
                    perPage = 21,
                    totalPages = 1,
                    totalPhotos = 21,
                    photos = listOf(
                        PhotoRemote(
                            id = "1",
                            owner = "2",
                            secret = "3",
                            server = "3",
                            title = "photo",
                            url = "MediumUrl",
                            width = 100,
                            height = 100,
                            thumbnailUrl = "ThumbnailUrl",
                            thumbnailHeight = 50,
                            thumbnailWidth = 50,
                        )
                    )
                )
            )
        )
        photosRepository.findPhotos(SearchQuery(listOf("tag")))
        val viewModel = PhotoDetailsViewModel(
            coroutineContextProvider,
            findPhoto = FindPhoto(photosRepository)
        )
        viewModel.stateFlow.test {
            awaitItem()
            viewModel.loadPhoto("2")
            awaitItem()
            assertEquals(
                PhotoDetailsState(
                    photo = null,
                    loading = false,
                    error = null
                ), awaitItem()
            )
            cancelAndIgnoreRemainingEvents()
        }
    }
}