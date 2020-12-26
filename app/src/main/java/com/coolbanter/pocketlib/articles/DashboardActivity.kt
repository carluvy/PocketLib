package com.coolbanter.pocketlib.articles

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.coolbanter.pocketlib.FileInfo
import com.coolbanter.pocketlib.R
import com.coolbanter.pocketlib.ViewActivity
import com.coolbanter.pocketlib.databinding.ActivityDashboardBinding
import com.coolbanter.pocketlib.databinding.RvItemsBinding
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso




class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var searchBar : TextInputEditText
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var fab: FloatingActionButton
    private lateinit var options : FirebaseRecyclerOptions<FileInfo>
    private lateinit var mAdapter : FirebaseRecyclerAdapter<FileInfo, FileViewHolder>
    private lateinit var mDatabaseReference: DatabaseReference
    private lateinit var mStorageReference: StorageReference




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)

        mDatabaseReference = FirebaseDatabase.getInstance().reference.child("File name")
        mStorageReference = FirebaseStorage.getInstance().reference.child("File")

        searchBar = binding.searchBar
        fab = binding.floatingActionButton
        mRecyclerView = binding.recyclerview
        mRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@DashboardActivity)
            hasFixedSize()
        }
        fab.setOnClickListener {
            startActivity(Intent(applicationContext, AddArticleActivity::class.java))
        }

        loadFiles("")
    }

    private fun loadFiles(files : String) {
//            downloadFiles("")
            val query =
                mDatabaseReference.orderByChild("FileName").startAt(files).endAt(files + "\uf8ff")
            options =
                FirebaseRecyclerOptions.Builder<FileInfo>().setQuery(query, FileInfo::class.java)
                    .build()
            mAdapter = object : FirebaseRecyclerAdapter<FileInfo, FileViewHolder>(options) {
                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
                    val itemBinding = RvItemsBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                    return FileViewHolder(itemBinding)
                }

                override fun onBindViewHolder(
                    holder: FileViewHolder,
                    position: Int,
                    model: FileInfo
                ) {
                    holder.textView.text = model.fileName
                    Picasso.get().load(model.fileUrl).into(holder.imageView)

//                    Glide.with(applicationContext)
//                        .load(model.fileName)
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
//                        .into(holder.imageView)

                    holder.itemView.setOnClickListener {
                        val intent = Intent(this@DashboardActivity, ViewActivity::class.java)
                        intent.putExtra("FileKey", getRef(position).key)
                        startActivity(intent)
                    }


                }
            }

            mAdapter.startListening()
            mRecyclerView.adapter = mAdapter



    }

//    private fun downloadFiles(fileName: String) {
//            val key = mDatabaseReference.push().key
//            mStorageReference.child("$key.jpg").downloadUrl.addOnSuccessListener {
//                val hashMap: HashMap<String, String?> = HashMap()
//                hashMap["FileName"] = fileName
//                hashMap["FileUrl"] = it.toString()
//
//                if (key != null) {
//                    mDatabaseReference.child(key).setValue(hashMap).addOnSuccessListener {
//                        startActivity(Intent(applicationContext, DashboardActivity::class.java))
//                        finish()
//                    }
//                }
//
//
//            }
//
//    }


}