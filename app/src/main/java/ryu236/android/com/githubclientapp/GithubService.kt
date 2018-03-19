package ryu236.android.com.githubclientapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ryu236.android.com.githubclientapp.api.data.UserData

/**
 * Created by ryu on 2/27/2018 AD.
 */

interface GithubService {

//    @GET("users/{user}/repos")
//    Call<List<Repo>> listRepos(@Path("user": String) String user)

    @GET("user")
    fun userData(@Query("access_token") access_token: String): Call<UserData>
}