package com.charuniverse.multipleimageupload.ui.upload

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.charuniverse.multipleimageupload.R
import com.charuniverse.multipleimageupload.databinding.FragmentUploadBinding

class UploadFragment : Fragment(R.layout.fragment_upload) {

    private lateinit var binding: FragmentUploadBinding
    private val viewModel: UploadImageViewModel by activityViewModels()

    private val uploadImageEvents = object : UploadImageAdapter.Events {
        override fun onImageClicked(position: Int) {
            val directions = UploadFragmentDirections
                .actionUploadFragmentToGalleryFragment(position)
            findNavController().navigate(directions)
        }

        override fun onDeleteImageClicked(position: Int) {
            viewModel.deletePickedImage(position)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUploadBinding.bind(view)

        binding.rvUploadImage.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)

        viewModel.pickedImages.observe(viewLifecycleOwner, {
            binding.rvUploadImage.adapter = UploadImageAdapter(
                events = uploadImageEvents,
                states = it,
            )
        })

        binding.btnUploadImageAdd.setOnClickListener {
            launchImagePickIntent()
        }

        binding.btnUploadImagePost.setOnClickListener {
            // TODO("upload image to server")
        }
    }

    private fun launchImagePickIntent() {
        Intent(Intent.ACTION_GET_CONTENT).let {
            it.type = "image/*"
            it.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            launcher.launch(it)
        }
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK || it.data != null) {
                viewModel.handleImagePicked(it.data!!)
            }
        }
}