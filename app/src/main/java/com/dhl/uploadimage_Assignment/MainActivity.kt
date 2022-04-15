package com.dhl.uploadimage_Assignment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import com.github.dhaval2404.imagepicker.ImagePicker

class MainActivity : AppCompatActivity() {
    private val btnSelectImage: AppCompatButton by lazy {
        findViewById(R.id.btnSelectPhoto)
    }

    private val imgPost: AppCompatImageView by lazy {
        findViewById(R.id.appCompatImageView)
    }

    private val btnUpload: AppCompatButton by lazy {
        findViewById(R.id.btnUpload)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUI()
    }

    private fun initUI() {
        btnSelectImage.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start()
        }

        btnUpload.setOnClickListener{
            val imgURI = btnUpload.tag as Uri?
            if(imgURI == null){
                Toast.makeText(this,"Please select image first",Toast.LENGTH_SHORT).show()
            }else{
                FirebaseStorageManager().uploadImage(this,imgURI)
                Toast.makeText(this@MainActivity,"File Uploaded",Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            val uri: Uri = data?.data!!

            // Use Uri object instead of File to avoid storage permissions
            imgPost.setImageURI(uri)
            btnUpload.setTag(uri)
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }
}