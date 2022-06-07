package com.bigo.flickrsearch.photos.domain.entities

data class Photo(
    val id: String,
    val owner: String,
    val secret: String,
    val title: String,
    val url: String,
    val width: Int,
    val height: Int,
)