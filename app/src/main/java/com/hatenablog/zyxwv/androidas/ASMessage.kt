package com.hatenablog.zyxwv.androidas

import android.os.Parcel
import android.os.Parcelable

data class ASMessage(val body: String?, val profile_image_url: String?, val name: String?) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString(), parcel.readString(), parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(body)
        parcel.writeString(profile_image_url)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ASMessage> {
        override fun createFromParcel(parcel: Parcel): ASMessage {
            return ASMessage(parcel)
        }

        override fun newArray(size: Int): Array<ASMessage?> {
            return arrayOfNulls(size)
        }
    }
}