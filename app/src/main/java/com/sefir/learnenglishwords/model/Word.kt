package com.sefir.learnenglishwords.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Word(
    @ColumnInfo(name = "type")
    @SerializedName("type")
    var type: String?,
    @ColumnInfo(name = "wordName")
    @SerializedName("wordName")
    var wordName: String?,
    @ColumnInfo(name = "translated")
    @SerializedName("translated")
    var translated: String?,
    @ColumnInfo(name = "mean")
    @SerializedName("mean")
    var mean: String?,
    @ColumnInfo(name = "example")
    @SerializedName("example")
    var example: String?,
    @ColumnInfo(name = "image")
    @SerializedName("image")
    var image: String?
) {

    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}