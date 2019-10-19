package com.ovlesser.sampletest.model

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.android.parcel.Parcelize

@Parcelize
class Geo(
    @JsonProperty("lat") val lat: String,
    @JsonProperty("lng") val lng: String
): Parcelable

@Parcelize
class Address(
    @JsonProperty("street") val street: String,
    @JsonProperty("suite") val suite: String,
    @JsonProperty("city") val city: String,
    @JsonProperty("zipcode") val zipcode: String,
    @JsonProperty("geo") val geo: Geo
): Parcelable

@Parcelize
class Company(
    @JsonProperty("name") val name: String,
    @JsonProperty("catchPhrase") val catchPhrase: String,
    @JsonProperty("bs") val bs: String
): Parcelable

@Parcelize
class User(
    @JsonProperty("id") val id: Int,
    @JsonProperty("name") val name: String,
    @JsonProperty("username") val username: String,
    @JsonProperty("email") val email: String,
    @JsonProperty("address") val address: Address,
    @JsonProperty("phone") val phone: String,
    @JsonProperty("website") val website: String,
    @JsonProperty("company") val company: Company
): Parcelable