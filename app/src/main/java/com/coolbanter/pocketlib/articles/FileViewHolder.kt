package com.coolbanter.pocketlib.articles

import androidx.recyclerview.widget.RecyclerView
import com.coolbanter.pocketlib.databinding.RvItemsBinding

class FileViewHolder(itemView: RvItemsBinding) : RecyclerView.ViewHolder(itemView.root) {

    var imageView = itemView.rvImageView
    var textView = itemView.tvRv
}
