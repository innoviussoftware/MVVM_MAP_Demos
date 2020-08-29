package com.example.mvvm_map_demos.ui.editprofile

import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.mvvm_map_demos.R
import com.example.mvvm_map_demos.utils.FileUtil
import com.makeramen.roundedimageview.RoundedTransformationBuilder
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.activity_edit_profile.*
import java.io.File

class EditProfileActivity : AppCompatActivity() {

    lateinit var mContext: Context
    var position: Int = 0
    var first_name: String? = ""
    var last_name: String? = ""
    var email: String? = ""
    var avatar: String? = ""
    var id:Int?=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        mContext = EditProfileActivity@ this

        id= intent.getIntExtra("id", 0)
        first_name = intent.getStringExtra("first_name")
        last_name = intent.getStringExtra("last_name")
        email = intent.getStringExtra("email")
        avatar = intent.getStringExtra("avatar")
        position = intent.getIntExtra("position", 0)


        edtTvNameAEP.setText(first_name)
        edtTvSecondNameAEP.setText(last_name)
        edtTvEmailAEP.setText(email)

        val transformation = RoundedTransformationBuilder()
            .cornerRadiusDp(1f)
            .oval(true)
            .build()

        if (avatar != null) {
            Picasso.with(mContext)
                .load(avatar)
                .fit()
                .transform(transformation)
                .into(imgVwProfPicAEP)
        } else
            imgVwProfPicAEP.setImageDrawable(mContext.resources.getDrawable(R.mipmap.ic_launcher))


    }




    fun updateProfileClick(view: View) {
        first_name=edtTvNameAEP.text.toString()
        last_name=edtTvSecondNameAEP.text.toString()
        email=edtTvEmailAEP.text.toString()

        var intent = Intent();
        intent.putExtra("id", id)
        intent.putExtra("first_name", first_name)
        intent.putExtra("last_name", last_name)
        intent.putExtra("email", email)
        intent.putExtra("profilemageUri",profilemageUri)
        intent.putExtra("avatar",avatar)
        intent.putExtra("position", position)
        setResult(RESULT_OK, intent);
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    fun backEditClick(view: View) {
        finish()
    }

    fun profileUpdateClick(view:View){
        try {
            if (checkPermission()) {
                CropImage.startPickImageActivity(this)
            }
        } catch (e: Exception) {
            e.printStackTrace();
        }
    }

    private val MY_PERMISSIONS_REQUEST_CAMERA = 1

    private fun checkPermission(): Boolean {
        try {

            val AccessCamera =
                ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA)
            val ReadPermission =
                ContextCompat.checkSelfPermission(
                    mContext,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            val WritePermission =
                ContextCompat.checkSelfPermission(
                    mContext,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )

            val listPermissionsNeeded = ArrayList<String>()

            if (AccessCamera != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.CAMERA)
            }
            if (ReadPermission != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
            if (WritePermission != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(
                    this@EditProfileActivity,
                    listPermissionsNeeded.toTypedArray(), MY_PERMISSIONS_REQUEST_CAMERA
                )
                return false
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return true
    }

    //Image set start

    private var mCropImageUri: Uri? = null
    var profilemageUri: Uri? = null
    //Profile Pic
    var filePathProfile: String? = ""
    var fileProfile: File? = null

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    var imageUri: Uri? = null
                    if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
                        imageUri = CropImage.getPickImageResultUri(this, data)
                        if (CropImage.isReadExternalStoragePermissionsRequired(this, imageUri)) {
                            mCropImageUri = imageUri
                            requestPermissions(
                                arrayOf(
                                    Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.CAMERA
                                ), CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE
                            )
                        } else {
                            startCropImageActivity(imageUri)
                        }
                    } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

                        try {
                            profilemageUri = CropImage.getActivityResult(data).getUri()

                            //Todo: isSelectImageType=0 means user pic, isSelectImageType=1 means company pic
                            filePathProfile = FileUtil.getPath(mContext!!, profilemageUri!!)
                            Glide.with(mContext)
                                .load(profilemageUri)
                                .apply(RequestOptions.circleCropTransform())
                                .into(imgVwProfPicAEP)
                        } catch (e: Exception) {
                            e.printStackTrace();
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun startCropImageActivity(imageUri: Uri) {
        CropImage.activity(imageUri)
            .start(this)
    }

}