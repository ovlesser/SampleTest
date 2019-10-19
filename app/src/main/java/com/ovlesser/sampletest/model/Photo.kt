package com.ovlesser.sampletest.model

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.android.parcel.Parcelize

@Parcelize
class Photo(
    @JsonProperty("albumId") val albumId: Int,
    @JsonProperty("id") val id: Int,
    @JsonProperty("title") val title: String,
    @JsonProperty("url") val url: String,
@JsonProperty("thumbnailUrl") val thumbnailUrl: String
) : Parcelable {
}