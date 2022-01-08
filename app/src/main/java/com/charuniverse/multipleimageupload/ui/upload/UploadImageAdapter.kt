package com.charuniverse.multipleimageupload.ui.upload

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.charuniverse.multipleimageupload.R
import com.charuniverse.multipleimageupload.databinding.ViewUploadImageDisplayBinding

class UploadImageAdapter(
    private val events: Events,
    private val states: List<UploadImageState>
) : RecyclerView.Adapter<UploadImageAdapter.ViewHolder>() {

    interface Events {
        fun onImageClicked(position: Int)
        fun onDeleteImageClicked(position: Int)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun getItemCount(): Int = states.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_upload_image_display, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = ViewUploadImageDisplayBinding.bind(holder.itemView)
        val state = states[position]

        Glide.with(binding.root.context)
            .load(state.path)
            .into(binding.ivUploadImageDisplay)

        binding.uploadImageItem.setOnClickListener {
            events.onImageClicked(position)
        }

        binding.cvUploadImageDisplayRemove.setOnClickListener {
            events.onDeleteImageClicked(position)
        }
    }
}
