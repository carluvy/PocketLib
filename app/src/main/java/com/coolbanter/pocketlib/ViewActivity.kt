package com.coolbanter.pocketlib

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.coolbanter.pocketlib.articles.DashboardActivity
import com.coolbanter.pocketlib.databinding.ActivityViewBinding
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso

class ViewActivity : AppCompatActivity() {
    private lateinit var binding : ActivityViewBinding
    private lateinit var imageView: ImageView
    lateinit var textView: TextView
    private lateinit var btnDelete: Button

    private lateinit var ref: DatabaseReference
    private lateinit var databaseReference:DatabaseReference
    private lateinit var storageReference: StorageReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view)


        imageView = binding.imageSingleViewActivity
        textView = binding.textViewSingleViewActivity
        btnDelete =binding.btnDelete
        ref = FirebaseDatabase.getInstance().reference.child("File name")
        val fileKey = intent.getStringExtra("FileKey")
        databaseReference = FirebaseDatabase.getInstance().reference.child("FileInfo").child(fileKey!!)
        storageReference = FirebaseStorage.getInstance().reference.child("FileImage").child("$fileKey.jpg")
        ref.child(fileKey).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val fileName = dataSnapshot.child("FileName").value.toString()
                    val fileUrl = dataSnapshot.child("FileUrl").value.toString()
                    Picasso.get().load(fileUrl).into(imageView)
//                    Glide.with(applicationContext)
//                        .load(fileUrl)
////                        .placeholder(R.drawable.ic_baseline_school_24)
//                        .centerCrop()
//                    .timeout(30)
////                    .error(
////                        ResourcesCompat.getDrawable(
////                            resources,
////                            R.drawable.ic_baseline_school_24,
////                            null
////                        )
//
//                        .into(imageView)
                    textView.text = fileName
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
        btnDelete.setOnClickListener {
            databaseReference.removeValue().addOnSuccessListener {
                storageReference.delete().addOnSuccessListener {
                    startActivity(
                            Intent(
                                    applicationContext,
                                    DashboardActivity::class.java
                            )
                    )
                }
            }
        }
    }
}