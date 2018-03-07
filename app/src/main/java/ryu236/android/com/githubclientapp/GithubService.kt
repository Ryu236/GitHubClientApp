//package ryu236.android.com.githubclientapp
//
//import retrofit2.http.GET
//import retrofit2.http.Path
//
///**
// * Created by ryu on 2/27/2018 AD.
// */
//
//interface GithubService {
//
//    @GET("users/{user}/repos")
//    Call<List<Repo>> listRepos(@Path("user": String) String user)
//
//    @GET("users")
//    Call<List<Repo>> list(@Path("user": String) String user)
//}