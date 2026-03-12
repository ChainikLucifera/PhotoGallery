package com.example.photogallery.utils.extensions

import com.example.photogallery.data.entities.FavouriteImage
import com.example.photogallery.models.Result

fun Result.toFavouriteImage() : FavouriteImage = FavouriteImage(
    id = id,
    title = title,
    url = url,
    thumbnail = thumbnail,
    creator = creator,
    license = license,
    width = width,
    height = height,
    source = source
)

fun FavouriteImage.toResult() : Result = Result(
    id = id,
    title = title,
    url = url,
    thumbnail = thumbnail,
    creator = creator,
    license = license,
    width = width,
    height = height,
    source = source,
    attribution = null,
    category = null,
    creator_url = null,
    detail_url = null,
    fields_matched = null,
    filesize = null,
    filetype = null,
    foreign_landing_url = null,
    indexed_on = null,
    license_url = null,
    license_version = null,
    mature = null,
    provider = null,
    related_url = null,
    tags = null,
    unstable__sensitivity = null
)