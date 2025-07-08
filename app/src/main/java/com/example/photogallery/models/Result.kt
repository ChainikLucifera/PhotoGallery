package com.example.photogallery.models

data class Result(
    val attribution: String,
    val category: String,
    val creator: String,
    val creator_url: String,
    val detail_url: String,
    val fields_matched: List<String>,
    val filesize: Int,
    val filetype: String,
    val foreign_landing_url: String,
    val height: Int,
    val id: String,
    val indexed_on: String,
    val license: String,
    val license_url: String,
    val license_version: String,
    val mature: Boolean,
    val provider: String,
    val related_url: String,
    val source: String,
    val tags: List<Tag>,
    val thumbnail: String,
    val title: String,
    val unstable__sensitivity: List<Any>,
    val url: String,
    val width: Int
)