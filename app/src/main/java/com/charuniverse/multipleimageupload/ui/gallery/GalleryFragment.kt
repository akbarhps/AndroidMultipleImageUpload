package com.charuniverse.multipleimageupload.ui.gallery

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.charuniverse.multipleimageupload.R
import com.charuniverse.multipleimageupload.databinding.FragmentGalleryBinding
import com.charuniverse.multipleimageupload.ui.upload.UploadImageViewModel

class GalleryFragment : Fragment(R.layout.fragment_gallery) {

    private lateinit var binding: FragmentGalleryBinding
    private lateinit var galleryToolbar: ConstraintLayout
    private lateinit var galleryViewPager: GalleryViewPager
    private lateinit var rvGalleryIndicator: RecyclerView

    private val viewModel: UploadImageViewModel by activityViewModels()
    private val args: GalleryFragmentArgs by navArgs()

    private var isIndicatorVisible = true

    private val galleryDisplayEvents = object : GalleryDisplayAdapter.Events {
        override fun onDisplayClicked(position: Int) {
            isIndicatorVisible = !isIndicatorVisible

            val visibility = if (isIndicatorVisible) View.VISIBLE else View.GONE
            rvGalleryIndicator.visibility = visibility
            galleryToolbar.visibility = visibility
        }
    }

    private val galleryIndicatorEvents = object : GalleryIndicatorAdapter.Events {
        override fun onIndicatorClicked(position: Int) {
            galleryViewPager.currentItem = position
            rvGalleryIndicator.scrollToPosition(position)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGalleryBinding.bind(view)

        galleryToolbar = binding.galleryToolbar
        galleryViewPager = binding.galleryViewPager

        rvGalleryIndicator = binding.rvGalleryIndicator
        rvGalleryIndicator.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)

        binding.ivGalleryClose.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.ivGalleryDelete.setOnClickListener {
            viewModel.deletePickedImage(galleryViewPager.currentItem)
        }

        viewModel.pickedImages.observe(viewLifecycleOwner, {
            galleryViewPager.adapter = GalleryDisplayAdapter(
                events = galleryDisplayEvents,
                images = it,
            )
            rvGalleryIndicator.adapter = GalleryIndicatorAdapter(
                events = galleryIndicatorEvents,
                images = it,
            )

            galleryViewPager.currentItem = args.scrollPosition
            rvGalleryIndicator.scrollToPosition(args.scrollPosition)
        })
    }
}