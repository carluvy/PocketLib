package com.coolbanter.pocketlib.articles

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.coolbanter.pocketlib.R
import com.coolbanter.pocketlib.databinding.ActivityAddArticleBinding
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.HashMap


class AddArticleActivity : AppCompatActivity() {

    companion object {
        private const val PICK_FILE_CODE = 102

    }

    private lateinit var mStorageRef: StorageReference
    private lateinit var mDataReference: DatabaseReference

    private lateinit var binding : ActivityAddArticleBinding
    private lateinit var mProgressbar : ProgressBar
    private lateinit var uploadFileBtn : MaterialButton
    private lateinit var fileName : TextInputEditText
    private lateinit var mImage : ImageView
    private lateinit var progressTv : TextView
    private var isImageAdded  = false
    private lateinit var fileUri : Uri


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_article)



        fileName = binding.articleName
        mProgressbar = binding.progressBar
        mImage = binding.article
        uploadFileBtn = binding.uploadButton
        progressTv = binding.viewProgress

        mStorageRef = FirebaseStorage.getInstance().reference.child("File")
        mDataReference = FirebaseDatabase.getInstance().reference.child("File name")



        mImage.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent, PICK_FILE_CODE)
        }

        binding.uploadButton.setOnClickListener {
            val nameOfFile = fileName.text.toString()
            if (isImageAdded)
                uploadFile(nameOfFile)

        }
    }


    private fun uploadFile(nameOfFile: String) {
        progressTv.visibility = View.VISIBLE
        mProgressbar.visibility = View.VISIBLE
        val key = mDataReference.push().key
    mStorageRef.child("$key.jpg").putFile(fileUri).addOnSuccessListener {
        mStorageRef.child("$key.jpg").downloadUrl.addOnSuccessListener { uri ->
            val hashMap: HashMap<String, String> = HashMap()
            hashMap["FileName"] = nameOfFile
            hashMap["FileUrl"] = uri.toString()

            if (key != null) {
                mDataReference.child(key).setValue(hashMap)
                    .addOnSuccessListener {
                        startActivity(Intent(applicationContext, DashboardActivity::class.java))
                        Toast.makeText(this, "Data Successfully Uploaded!", Toast.LENGTH_SHORT)
                            .show();
                    }
            }
        }

    } .addOnProgressListener {
                val progress : Long = it.bytesTransferred * 100 / it.totalByteCount
                mProgressbar.progress = progress.toInt()
                progressTv.text = "$progress %"
            }

    }
//
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_FILE_CODE && data != null) {
                fileUri = data.data!!
                isImageAdded = true
                mImage.setImageURI(fileUri)



//
        }
    }

}