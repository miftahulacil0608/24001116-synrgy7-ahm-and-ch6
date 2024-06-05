package com.example.recyclerviewwithnavigationcomponent.ui.camera

import android.Manifest.permission.CAMERA
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil.load
import com.example.recyclerviewwithnavigationcomponent.databinding.ActivityCameraBinding
import java.io.File

class CameraActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityCameraBinding.inflate(layoutInflater)
    }
    private val permission = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions(),
        ::handlePermission
    )
    private val galleryResult = registerForActivityResult(ActivityResultContracts.GetContent()) {
        binding.imgBanner.load(it)
    }
    private lateinit var uri: Uri

    private val cameraResult =
        registerForActivityResult(ActivityResultContracts.TakePicture(), ::handleCamera)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.btnUploadImg.setOnClickListener {
            if (isWriteExternalStoragePermissionGranted() && isReadExternalStoragePermissionGranted() && isCameraPermissionGranted()) {
                Toast.makeText(this, "Permission diterima", Toast.LENGTH_SHORT).show()
                chooseImageDialog()
            } else {
                askPermission()
            }
        }
    }

    private fun handlePermission(result: Map<String, Boolean>) {
        if (result.containsValue(false)) {
            Toast.makeText(this, "Permission ditolak", Toast.LENGTH_SHORT).show()
            onBackPressed()
        } else {
            Toast.makeText(this, "Permission diterima", Toast.LENGTH_SHORT).show()
        }
    }

    private fun askPermission() {
        permission.launch(
            arrayOf(
                READ_EXTERNAL_STORAGE,
                WRITE_EXTERNAL_STORAGE,
                CAMERA
            )
        )
    }

    private fun isWriteExternalStoragePermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun isReadExternalStoragePermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun isCameraPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun chooseImageDialog() {
        AlertDialog.Builder(this)
            .setMessage("Pilih Gambar")
            .setPositiveButton("Galery") { _, _ -> openGallery() }
            .setNegativeButton("Camera") { _, _ -> openCamera() }
            .show()
    }

    private fun openGallery() {
        intent.type = "image/*"
        galleryResult.launch("image/*")
    }


    private fun handleCamera(result: Boolean) {
        if (result) {
            binding.imgBanner.load(uri)
        } else {
            Toast.makeText(this, "Gagal mengambil gambar", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openCamera() {
        val photoFile = File.createTempFile(
            "IMG_",
            ".jpg",
            this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        )
        uri = FileProvider.getUriForFile(
            this,
            "com.example.recyclerviewwithnavigationcomponent.fileprovider",
            photoFile
        )

        cameraResult.launch(uri)

    }


}