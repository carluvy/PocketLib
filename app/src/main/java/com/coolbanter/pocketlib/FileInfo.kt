package com.coolbanter.pocketlib

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class FileInfo(

    var fileUrl: String? = null,
    var fileName: String? = null
)

