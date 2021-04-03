package com.jeanporcher.tpjeanporcher.userinfo

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import coil.load
import com.jeanporcher.tpjeanporcher.BuildConfig
import com.jeanporcher.tpjeanporcher.R
import com.jeanporcher.tpjeanporcher.network.Api
import com.jeanporcher.tpjeanporcher.tasklist.TaskListFragment
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File


class UserInfoActivity : AppCompatActivity() {
    private val userInfoViewModel: UserInfoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)
        val avatarImg: ImageView = findViewById(R.id.avatar_img)
        val backBtn: ImageButton = findViewById(R.id.back_to_list_button)
        val takePictureButton: Button = findViewById(R.id.take_picture_button)
        val uploadImageButton: Button = findViewById(R.id.upload_image_button)
        val saveUserButton: Button = findViewById(R.id.save_user_button)
        val firstname = findViewById<EditText>(R.id.firstname)
        val lastname = findViewById<EditText>(R.id.lastname)
        val email = findViewById<EditText>(R.id.email)
        val USER_INFO = "user_info"

        backBtn.setOnClickListener(){
            intent.putExtra(USER_INFO,"")
            setResult(RESULT_OK,intent)
            finish()
        }
        lifecycleScope.launch(){
            userInfoViewModel.getInfos()
            val userInfo = userInfoViewModel.userInfos.value!!
            firstname.setText(userInfo.firstName)
            lastname.setText(userInfo.lastName)
            email.setText(userInfo.email)
            saveUserButton.setOnClickListener(){
                lifecycleScope.launch(){
                    val user = UserInfo(email.text.toString(),firstname.text.toString(),lastname.text.toString(),userInfo.avatar)
                    Log.d("userInfos ==>",user.toString())
                    userInfoViewModel.updateUser(user)
                    intent.putExtra(USER_INFO,"")
                    setResult(RESULT_OK,intent)
                    finish()
                }
            }
        }

        avatarImg.setOnClickListener(){
            openGallery()
        }
        takePictureButton.setOnClickListener(){
            askCameraPermissionAndOpenCamera()
        }
        uploadImageButton.setOnClickListener(){
            openGallery()
        }
    }
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) openCamera()
            else showExplanationDialog()
        }

    private fun requestCameraPermission() =
        requestPermissionLauncher.launch(Manifest.permission.CAMERA)

    private fun askCameraPermissionAndOpenCamera() {
        when {
            ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED -> openCamera()
            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> showExplanationDialog()
            else -> requestCameraPermission()
        }
    }

    private fun showExplanationDialog() {
        AlertDialog.Builder(this).apply {
            setMessage("On a besoin de la caméra sivouplé ! 🥺")
            setPositiveButton("Bon, ok") { _, _ ->
                requestCameraPermission()
            }
            setCancelable(true)
            show()
        }
    }
    // create a temp file and get a uri for it
    private val photoUri by lazy {
        FileProvider.getUriForFile(
            this,
            BuildConfig.APPLICATION_ID +".fileprovider",
            File.createTempFile("avatar", ".jpeg", externalCacheDir)
        )
    }
    // register
    private val takePicture = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) handleImage(photoUri)
        else Toast.makeText(this, "Erreur ! 😢", Toast.LENGTH_LONG).show()
    }
    // use
    private fun openCamera() = takePicture.launch(photoUri)

    // register
    private val pickInGallery =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
           if (uri != null) {
               handleImage(uri)
           }

        }

    // use
    private fun openGallery() = pickInGallery.launch("image/*")

    private fun convert(uri: Uri) =
        MultipartBody.Part.createFormData(
            name = "avatar",
            filename = "temp.jpeg",
            body = contentResolver.openInputStream(uri)!!.readBytes().toRequestBody()
        )
    private fun handleImage(uri: Uri){
        lifecycleScope.launch {
            if (uri.toString() != "") {
                val avatarImg: ImageView = findViewById(R.id.avatar_img)
                userInfoViewModel.updateAvatar(convert(uri))
                avatarImg.load(uri.toString())
            }
        }
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            userInfoViewModel.getInfos()
            val avatarImg: ImageView = findViewById(R.id.avatar_img)
            val userInfo = userInfoViewModel.userInfos.value!!
            if (userInfo.avatar != "") avatarImg.load(userInfo.avatar)
        }
    }

}