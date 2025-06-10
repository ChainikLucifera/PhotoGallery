package com.example.photogallery.models

data class PhotoResult(
    val attribution: String,
    val category: String,
    val creator: String,
    val creatorUrl: String,
    val detailUrl: String,
    val fieldsMatched: List<String>,
    val filesize: Int,
    val filetype: String,
    val foreignLandingUrl: String,
    val height: Int,
    val id: String,
    val indexedOn: String,
    val license: String,
    val licenseUrl: String,
    val licenseVersion: String,
    val mature: Boolean,
    val provider: String,
    val relatedUrl: String,
    val source: String,
    val tags: List<Tag>,
    val thumbnail: String,
    val title: String,
    val unstableSensitivity: List<Any>,
    val url: String,
    val width: Int
)