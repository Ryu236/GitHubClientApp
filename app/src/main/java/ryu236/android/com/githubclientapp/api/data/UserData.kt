package ryu236.android.com.githubclientapp.api.data

import se.ansman.kotshi.JsonSerializable

/**
 * Created by ryu on 3/18/2018 AD.
 */

@JsonSerializable
data class UserData(
        val login: String,
        val id: Int,
        val avatar_url: String,
        val followers: Int,
        val following: Int
)