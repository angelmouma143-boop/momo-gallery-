package com.example.momogallery.data.model

import android.net.Uri

data class Media(
    val id: Long,
    val uri: Uri,
    val name: String,
    val dateAdded: Long
)
