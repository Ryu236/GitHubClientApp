package ryu236.android.com.githubclientapp

import android.content.Intent
import android.content.SharedPreferences
import android.icu.text.UnicodeSet
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.TextView
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ryu236.android.com.githubclientapp.api.data.ApplicationJsonAdapterFactory
import ryu236.android.com.githubclientapp.api.data.UserData
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    var mAccessToken: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        mAccessToken = sharedPref.getString(getString(R.string.token), "")

        if (mAccessToken.isEmpty()) {
            Timber.tag(getString(R.string.token)).d("nothing!")
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Timber.tag(getString(R.string.token)).d("gotcha!_$mAccessToken")
            getUserData(mAccessToken)
        }
    }

    fun getUserData(token: String) {

        val moshi: Moshi = Moshi.Builder()
                .add(ApplicationJsonAdapterFactory.INSTANCE)
                .build()
        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()

        val service: GithubService = retrofit.create(GithubService::class.java)
        val userData: Call<UserData> = service.userData(token)

        userData.enqueue(object: retrofit2.Callback<UserData> {
            override fun onResponse(call: retrofit2.Call<UserData>, response: retrofit2.Response<UserData>) {
                text.setText(response.body().toString(), TextView.BufferType.NORMAL)
            }
            override fun onFailure(call: retrofit2.Call<UserData>, e: Throwable) {
                print(e)
            }
        })

    }
}