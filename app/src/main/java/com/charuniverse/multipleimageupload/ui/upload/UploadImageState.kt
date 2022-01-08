package com.charuniverse.multipleimageupload.ui.upload

import android.net.Uri

data class UploadImageState(
    val path: Uri = Uri.EMPTY,
    val name: String = "",
    val remoteUrl: String = "",
)
