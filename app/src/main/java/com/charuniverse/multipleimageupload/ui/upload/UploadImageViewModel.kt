package com.charuniverse.multipleimageupload.ui.upload

import android.content.ClipData
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UploadImageViewModel : ViewModel() {

    private val _pickedImages = MutableLiveData(mutableListOf<UploadImageState>())
    val pickedImages: LiveData<MutableList<UploadImageState>> = _pickedImages

    fun deletePickedImage(position: Int) {
        val temp = _pickedImages.value!!
        temp.removeAt(position)
        _pickedImages.value = temp
    }

    private fun handleData(uri: Uri): UploadImageState {
        val stringUri = uri.toString()
        val name = stringUri.substring(stringUri.lastIndexOf("/") + 1)
        return UploadImageState(uri, "${System.currentTimeMillis()}_$name")
    }

    private fun handleClipData(clipData: ClipData): List<UploadImageState> {
        val list = mutableListOf<UploadImageState>()
        for (index in 0 until clipData.itemCount) {
            val uri = clipData.getItemAt(index).uri
            list.add(handleData(uri))
        }
        return list
    }

    fun handleImagePicked(result: Intent) {
        val list = _pickedImages.value!!.toMutableList()
        when {
            (result.clipData != null) -> list.addAll(handleClipData(result.clipData!!))
            (result.data != null) -> list.add(handleData(result.data!!))
        }
        _pickedImages.value = list
    }
}