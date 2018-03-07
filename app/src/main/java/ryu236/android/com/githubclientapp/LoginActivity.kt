package ryu236.android.com.githubclientapp

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.customtabs.CustomTabsIntent
import android.util.Log
import android.widget.TextView
import okhttp3.*
import timber.log.Timber
import java.io.IOException

class LoginActivity : AppCompatActivity() {

    val mClientId: String = "128f1acba0db81b630d7"
    val mClientSecret: String = "00218364b4dccf7b70acd9b86f38520b6f6e1d41"
    var mCode: String? = null
    val client: OkHttpClient = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        Customtabs()
    }

    fun Customtabs() {
        val url: String = getString(R.string.oauth_url) + "authorize?" + "client_id=" + mClientId
        val customtabsIntent: CustomTabsIntent = CustomTabsIntent.Builder().build()
        customtabsIntent.launchUrl(this, Uri.parse(url))
    }


    override fun onResume() {
        super.onResume()
    }


    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)

        val intent = Intent.parseUri(Intent.ACTION_VIEW, 0)
        val action: String? = intent.action

        if (Intent.ACTION_VIEW.equals(action)) {
            val uri: Uri? = intent?.data
            if (uri != null) {
                mCode = uri.getQueryParameter("code")
                getAccessToken()
            }
        }
    }


    fun getAccessToken() {
        val request: Request = Request.Builder()
                .url(getString(R.string.oauth_url) + "access_token?" + "code=" + mCode + "&client_id=" + mClientId + "&client_secret=" + mClientSecret)
                .build()

        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call?, response: Response?) {
                val uri: Uri? = Uri.parse(response?.body()?.string())
                if (uri != null) {
                    //TODO: 成功した時のトークンについて処理を書く
                    val sharedPref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
                    sharedPref.edit().putString("access_token", uri.getQueryParameter("access_token")).apply()
                    Timber.tag("AccessToken").d(uri.toString())
                }
            }
            override fun onFailure(call: Call?, e: IOException?) {
                Timber.tag("okhttp").d("error")
            }
        })
    }

}
