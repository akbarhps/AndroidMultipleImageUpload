package com.charuniverse.multipleimageupload.ui.gallery

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.charuniverse.multipleimageupload.databinding.ViewGalleryDisplayBinding
import com.charuniverse.multipleimageupload.ui.upload.UploadImageState

open class GalleryDisplayAdapter(
    private val images: List<UploadImageState>,
    private val events: Events,
) : PagerAdapter() {

    interface Events {
        fun onDisplayClicked(position: Int)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding = ViewGalleryDisplayBinding.inflate(LayoutInflater.from(container.context))
        val touchImageView = binding.tivImageGalleryDisplay
        val image = images[position]

        touchImageView.setOnClickListener {
            events.onDisplayClicked(position)
        }

        // this could be better right?
        if (image.path != Uri.EMPTY) {
            Glide.with(container.context)
                .load(image.path)
                .into(touchImageView)
        } else {
            Glide.with(container.context)
                .load(image.remoteUrl)
                .into(touchImageView)
        }

        container.addView(binding.root)
        return binding.root
    }

    override fun getCount(): Int = images.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ConstraintLayout)
    }
}
