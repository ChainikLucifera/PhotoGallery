package com.example.photogallery.models

data class OpenVerseResponse(
    val page: Int,
    val pageCount: Int,
    val pageSize: Int,
    val resultCount: Int,
    val results: List<PhotoResult>?,
    val warnings: List<Warning>
)