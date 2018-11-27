package com.uryonet.rempen.model.entity

data class PhotoList (
    val errCode: String,
    val errMsg: String,
    val dirs: List<PhotoDir>
)
