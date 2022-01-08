package com.charuniverse.multipleimageupload.ui.gallery

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.charuniverse.multipleimageupload.R
import com.charuniverse.multipleimageupload.databinding.ViewGalleryIndicatorBinding
import com.charuniverse.multipleimageupload.ui.upload.UploadImageState

class GalleryIndicatorAdapter(
    private val images: List<UploadImageState>,
    private val events: Events,
) : RecyclerView.Adapter<GalleryIndicatorAdapter.ViewHolder>() {

    interface Events {
        fun onIndicatorClicked(position: Int)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_gallery_indicator, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = ViewGalleryIndicatorBinding.bind(holder.itemView)
        val image = images[position]

        // this could be better right?
        if (image.path != Uri.EMPTY) {
            Glide.with(binding.root.context)
                .load(image.path)
                .into(binding.galleryIndicatorImage)
        } else {
            Glide.with(binding.root.context)
                .load(image.remoteUrl)
                .into(binding.galleryIndicatorImage)
        }

        binding.galleryIndicatorItem.setOnClickListener {
            events.onIndicatorClicked(position)
        }
    }

    override fun getItemCount(): Int = images.size
}
