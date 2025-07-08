package com.example.photogallery.models

data class OpenVerseResponse(
    val page: Int,
    val page_count: Int,
    val page_size: Int,
    val result_count: Int,
    val results: List<Result>,
    val warnings: List<Warning>
)