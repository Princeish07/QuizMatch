package com.quizmatch.app.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.quizmatch.app.data.model.firebase.User
import com.quizmatch.app.utils.Constants.JPEG_FILE_PREFIX
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.sql.Timestamp
import java.util.Date


//Method to check validation email
fun String.isValidEmail(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}
//Method to show toast on fragment
fun Fragment.showToast(resId: Int? = null, message: String? = null) {
    activity?.let {
        Toast.makeText(
            it, if (resId != null) {
                it.getString(resId)
            } else {
                message!!
            }, Toast.LENGTH_SHORT
        ).show()
    }
}

//Method to show toast on activity
fun Activity.showToast(resId: Int? = null, message: String? = null) {

    Toast.makeText(
        this, if (resId != null) {
            this.getString(resId)
        } else {
            message!!
        }, Toast.LENGTH_SHORT
    ).show()
}

/**
 * Execute navigation
 *
 * Execute fragment navigation based on [fragmentNavigationBuilder] options.
 * [fragmentNavigationBuilder] contains several options like fragment name,
 * Container name, add or replace fragment.
 *
 * @param fragmentNavigationBuilder
 */
fun FragmentActivity.executeNavigation(fragmentNavigationBuilder: FragmentNavigationBuilder) {
    val currentFragment: Fragment = fragmentNavigationBuilder.fragment
    val fts = supportFragmentManager.beginTransaction()
    currentFragment.arguments = fragmentNavigationBuilder.bundle
    if (fragmentNavigationBuilder.isAddFragment)
        fts.add(
            fragmentNavigationBuilder.container!!,
            currentFragment,
            currentFragment.javaClass.simpleName
        )
    else
        fts.replace(
            fragmentNavigationBuilder.container!!,
            currentFragment,
            currentFragment.javaClass.simpleName
        )

    if (fragmentNavigationBuilder.isBackStack)
        fts.addToBackStack(currentFragment.javaClass.simpleName)
    fts.commit()
}


data class FragmentNavigationBuilder(
    var fragment: Fragment,
    var container: Int? = null,
    var isAddFragment: Boolean = false,
    var isBackStack: Boolean = false,
    var bundle: Bundle? = null
) {
    fun container(container: Int) = apply { this.container = container }
    fun isAddFragment(isAddFragment: Boolean) = apply { this.isAddFragment = isAddFragment }
    fun isBackStack(isBackStack: Boolean) = apply { this.isBackStack = isBackStack }
    fun bundle(bundle: Bundle?) = apply { this.bundle = bundle }
    fun build() = FragmentNavigationBuilder(fragment, container, isAddFragment, isBackStack, bundle)
}
private fun createNoMedia(directory: String) {

    val filepath = directory + "/"

    val file = File("$filepath.nomedia")
    try {
        file.createNewFile()
    } catch (e: IOException) {
        e.printStackTrace()
    }

}

@Throws(IOException::class)
fun setUpImageFile(directory: String): File? {
    var imageFile: File? = null
    if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
        val storageDir = File(directory)
        if (!storageDir.mkdirs()) {
            if (!storageDir.exists()) {
                Log.d("BasePicture", "failed to create directory")
                return null
            }
        }
        createNoMedia(directory)
        imageFile = File.createTempFile(
            JPEG_FILE_PREFIX + System.currentTimeMillis() + "_",
            Constants.JPEG_FILE_SUFFIX,
            storageDir
        )
    }
    return imageFile
}
fun Activity.storeValueInDevice(bitmap: Bitmap,onSuccess:(Boolean)->Unit){
    // Define the file path and name

    val file = setUpImageFile(Environment.getExternalStorageDirectory().absolutePath + "/mydir")

    try {
        // Create an output stream to the file
        val outputStream: OutputStream = FileOutputStream(file)

        // Compress the bitmap and write it to the output stream
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)

        // Close the output stream
        outputStream.close()
        onSuccess(true)

        // Display a success message or perform any other desired actions
    } catch (e: IOException) {
        e.printStackTrace()
        onSuccess(false)

        // Handle the exception
    }
}

fun requestPermission(activity: Activity,requestCode:Int) {
    // requesting permissions if not provided.
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
        val uri: Uri = Uri.fromParts("package", activity.packageName, null)
        intent.data = uri
        ContextCompat.startActivity(activity, intent, null)

    } else {
        //below android 11=======
        ActivityCompat.requestPermissions(
            activity,
            arrayOf<String>(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            requestCode
        )
    }
}
fun checkPermission(context: Context): Boolean {
    // checking of permissions.
    val permission1: Int =
        ContextCompat.checkSelfPermission(context,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

    return permission1 == PackageManager.PERMISSION_GRANTED
}

class CustomException(message: String? = "Something went wrong!!!") : Exception(message)


interface OnItemClickListener {
    fun onItemClick(position: Int,itemDetail: User)
}