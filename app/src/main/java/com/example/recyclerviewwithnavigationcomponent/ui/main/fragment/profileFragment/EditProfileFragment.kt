package com.example.recyclerviewwithnavigationcomponent.ui.main.fragment.profileFragment

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.work.WorkInfo
import coil.load
import com.example.recyclerviewwithnavigationcomponent.R
import com.example.recyclerviewwithnavigationcomponent.databinding.FragmentEditProfileBinding
import com.example.recyclerviewwithnavigationcomponent.ui.main.SharedViewModel
import com.example.recyclerviewwithnavigationcomponent.workers.KEY_IMAGE_URI
import com.nareshchocha.filepickerlibrary.models.ImageCaptureConfig
import com.nareshchocha.filepickerlibrary.models.PickMediaConfig
import com.nareshchocha.filepickerlibrary.models.PickMediaType
import com.nareshchocha.filepickerlibrary.ui.FilePicker
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class EditProfileFragment : Fragment() {
    private lateinit var binding: FragmentEditProfileBinding
    private val viewModel: ProfileViewModel by activityViewModel<ProfileViewModel>()
    private val filePickerResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            ::handleFilePickerResult,
        )
    private val imagePickerResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            ::handleFileImageResult,
        )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentEditProfileBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnChangesImageProfile.setOnClickListener {
            chooseImageDialog()
        }

        showDisplayData()

        binding.btnSaveProfile.setOnClickListener {
            editAccountUserProfile()
        }

        viewModel.errorUpdateUserProfile.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT).show()
        }

        binding.goButton.setOnClickListener { viewModel.applyBlur(2) }

        // Setup view output image file button
        binding.seeFileButton.setOnClickListener {
            viewModel.outputUri?.let { currentUri ->
                val actionView = Intent(Intent.ACTION_VIEW, currentUri)
                actionView.resolveActivity(requireActivity().packageManager)?.run {
                    startActivity(actionView)
                }
            }
        }

        // Hookup the Cancel button
        binding.cancelButton.setOnClickListener { viewModel.cancelWork() }
        viewModel.outputWorkInfos.observe(viewLifecycleOwner, workInfosObserver())

    }

    private fun chooseImageDialog() {
        AlertDialog.Builder(requireContext())
            .setMessage("Pilih Gambar")
            .setPositiveButton("Galery") { _, _ -> openFilePicker() }
            .setNegativeButton("Camera") { _, _ -> openCameraPicker() }
            .show()
    }

    private fun showDisplayData() {
        viewModel.dataProfile.observe(viewLifecycleOwner) {
            binding.usernameProfile.text = it.username
        }
    }

    private fun editAccountUserProfile() {

        if (binding.edtUsernameInEditProfile.text.isNullOrEmpty()) binding.itlUsernameInEditProfile.error =
            "username Empty"
        if (binding.edtEmailInEditProfile.text.isNullOrEmpty()) binding.itlEmailInEditProfile.error =
            "Email Empty"
        if (binding.edtPasswordInEditProfile.text.isNullOrEmpty()) binding.itlPasswordInEditProfile.error =
            "Password Empty"
        else {
            binding.itlUsernameInEditProfile.error = null
            binding.itlEmailInEditProfile.error = null
            binding.itlPasswordInEditProfile.error = null
            val username = binding.edtUsernameInEditProfile.text.toString()
            val email = binding.edtEmailInEditProfile.text.toString()
            val password = binding.edtPasswordInEditProfile.text.toString()
            viewModel.updateAccountUserProfile(username, email, password, null)

            findNavController().popBackStack(R.id.profileFragment, false)

        }

    }

    private fun workInfosObserver(): Observer<List<WorkInfo>> {
        return Observer { listOfWorkInfo ->

            // Note that these next few lines grab a single WorkInfo if it exists
            // This code could be in a Transformation in the ViewModel; they are included here
            // so that the entire process of displaying a WorkInfo is in one location.

            // If there are no matching work info, do nothing
            if (listOfWorkInfo.isNullOrEmpty()) {
                return@Observer
            }

            // We only care about the one output status.
            // Every continuation has only one worker tagged TAG_OUTPUT
            val workInfo = listOfWorkInfo[0]

            if (workInfo.state.isFinished) {
                showWorkFinished()

                // Normally this processing, which is not directly related to drawing views on
                // screen would be in the ViewModel. For simplicity we are keeping it here.
                val outputImageUri = workInfo.outputData.getString(KEY_IMAGE_URI)

                // If there is an output file show "See File" button
                if (!outputImageUri.isNullOrEmpty()) {
                    viewModel.setOutputUri(outputImageUri)
                    binding.seeFileButton.visibility = View.VISIBLE
                    binding.profileImage.load(outputImageUri)
                    findNavController().previousBackStackEntry?.savedStateHandle?.set(
                        KEY_REQUEST_IMAGE_PROFILE, outputImageUri
                    )
                }
            } else {
                showWorkInProgress()
            }
        }
    }

    /* *
      * Shows and hides views for when the Activity is processing an image
      */
    private fun showWorkInProgress() {
        with(binding) {
            progressBar.visibility = View.VISIBLE
            cancelButton.visibility = View.VISIBLE
            goButton.visibility = View.GONE
            seeFileButton.visibility = View.GONE
        }
    }

    /* *
      * Shows and hides views for when the Activity is done processing an image*/

    private fun showWorkFinished() {
        with(binding) {
            progressBar.visibility = View.GONE
            cancelButton.visibility = View.GONE
            goButton.visibility = View.VISIBLE
        }
    }

    private fun openCameraPicker() {
        imagePickerResult.launch(
            FilePicker.Builder(requireContext())
                .imageCaptureBuild(
                    ImageCaptureConfig(),
                )
        )
    }

    private fun handleFileImageResult(activityResult: ActivityResult) {
        if (activityResult.resultCode == RESULT_OK) {
            activityResult.data?.data?.let {
                binding.profileImage.load(it)
                viewModel.setImageUri(it)
            }
        }
    }


    private fun openFilePicker() {
        filePickerResult.launch(
            FilePicker.Builder(requireContext())
                .pickMediaBuild(
                    PickMediaConfig(
                        mPickMediaType = PickMediaType.ImageOnly,
                        allowMultiple = false,
                    ),
                ),
        )
    }

    private fun handleFilePickerResult(result: ActivityResult) {
        if (result.resultCode == RESULT_OK) {
            result.data?.data?.let {
                binding.profileImage.load(it)
                viewModel.setImageUri(it)

            }
        }
    }

    companion object {
        const val KEY_REQUEST_IMAGE_PROFILE = "KEY_REQUEST_IMAGE_PROFILE"
    }
}