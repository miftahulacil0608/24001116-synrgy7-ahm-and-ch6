package com.example.recyclerviewwithnavigationcomponent.ui.main.fragment.profileFragment

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.recyclerviewwithnavigationcomponent.R
import com.example.recyclerviewwithnavigationcomponent.domain.model.dataclass.UserProfileData
import com.example.recyclerviewwithnavigationcomponent.domain.usecase.UseCase
import com.example.recyclerviewwithnavigationcomponent.workers.BlurWorker
import com.example.recyclerviewwithnavigationcomponent.workers.CleanupWorker
import com.example.recyclerviewwithnavigationcomponent.workers.KEY_IMAGE_URI
import com.example.recyclerviewwithnavigationcomponent.workers.SaveImageToFileWorker
import kotlinx.coroutines.launch

class ProfileViewModel(private val useCase: UseCase, application: Application) : ViewModel() {
    private val _dataProfile = MutableLiveData<UserProfileData>()
    val dataProfile: LiveData<UserProfileData> = _dataProfile

    fun refreshData() {
        try {
            viewModelScope.launch {
                _dataProfile.value = useCase.getAllDataUserProfile()
            }
        } catch (throwable: Throwable) {
            throw IllegalAccessException(throwable.message)
        }
    }

    private var _successLogout = MutableLiveData<Boolean>()
    val successLogout: LiveData<Boolean> = _successLogout
    fun logout() {
        viewModelScope.launch {
            _successLogout.value = false
            useCase.clearDataAuth()
            _successLogout.value = true
        }
    }

    //isUpdateUserProfile
    private val _errorUpdateUserProfile = MutableLiveData<Throwable>()
    val errorUpdateUserProfile: LiveData<Throwable> = _errorUpdateUserProfile

    fun updateAccountUserProfile(username: String, email: String, password: String, image: String?) {
        try {
            viewModelScope.launch {
                useCase.updateDataUserProfile(username, email, password)
                refreshData()
            }
        } catch (throwable: Throwable) {
            _errorUpdateUserProfile.value = throwable
            throw IllegalAccessException(throwable.message)
        }

    }


    private var imageUri: Uri? = null
    internal var outputUri: Uri? = null
    private val workManager = WorkManager.getInstance(application)
    internal val outputWorkInfos: LiveData<List<WorkInfo>> =
        workManager.getWorkInfosByTagLiveData(TAG_OUTPUT)

    companion object {
        const val TAG_OUTPUT = "OUTPUT"
        const val IMAGE_MANIPULATION_WORK_NAME = "image_manipulation_work"
    }

    init {
        // This transformation makes sure that whenever the current work Id changes the WorkInfo
        // the UI is listening to changes
        imageUri = getImageUri(application.applicationContext)
    }

    internal fun cancelWork() {
        workManager.cancelUniqueWork(IMAGE_MANIPULATION_WORK_NAME)
    }

    /**
     * Creates the input data bundle which includes the Uri to operate on
     * @return Data which contains the Image Uri as a String
     */
    private fun createInputDataForUri(): Data {
        val builder = Data.Builder()
        imageUri?.let {
            builder.putString(KEY_IMAGE_URI, imageUri.toString())
        }
        return builder.build()
    }

    /**
     * Create the WorkRequest to apply the blur and save the resulting image
     * @param blurLevel The amount to blur the image
     */
    internal fun applyBlur(blurLevel: Int) {
        // Add WorkRequest to Cleanup temporary images
        var continuation = workManager
            .beginUniqueWork(
                IMAGE_MANIPULATION_WORK_NAME,
                ExistingWorkPolicy.REPLACE,
                OneTimeWorkRequest.from(CleanupWorker::class.java)
            )

        // Add WorkRequests to blur the image the number of times requested
        for (i in 0 until blurLevel) {
            val blurBuilder = OneTimeWorkRequestBuilder<BlurWorker>()

            // Input the Uri if this is the first blur operation
            // After the first blur operation the input will be the output of previous
            // blur operations.
            if (i == 0) {
                blurBuilder.setInputData(createInputDataForUri())
            }

            continuation = continuation.then(blurBuilder.build())
        }

        // Create charging constraint
        val constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .build()

        // Add WorkRequest to save the image to the filesystem
        val save = OneTimeWorkRequestBuilder<SaveImageToFileWorker>()
            .setConstraints(constraints)
            .addTag(TAG_OUTPUT)
            .build()
        continuation = continuation.then(save)

        // Actually start the work
        continuation.enqueue()
    }

    private fun uriOrNull(uriString: String?): Uri? {
        return if (!uriString.isNullOrEmpty()) {
            Uri.parse(uriString)
        } else {
            null
        }
    }

    private fun getImageUri(context: Context): Uri {
        val resources = context.resources

        return Uri.Builder()
            .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
            .authority(resources.getResourcePackageName(R.drawable.ligue_1_logo))
            .appendPath(resources.getResourceTypeName(R.drawable.ligue_1_logo))
            .appendPath(resources.getResourceEntryName(R.drawable.ligue_1_logo))
            .build()
    }

    internal fun setOutputUri(outputImageUri: String?) {
        outputUri = uriOrNull(outputImageUri)
    }

    fun setImageUri(imageUri: Uri) {
        this.imageUri = imageUri
    }
}