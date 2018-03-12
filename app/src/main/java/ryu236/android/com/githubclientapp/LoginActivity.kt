package ryu236.android.com.githubclientapp

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.customtabs.CustomTabsIntent
import android.text.TextUtils
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
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

        loginButton.setOnClickListener {
            Customtabs()
        }
    }

    private fun Customtabs() {
        val url: String = getString(R.string.oauth_url) + "authorize?" + "client_id=" + mClientId
        val customtabsIntent: CustomTabsIntent = CustomTabsIntent.Builder().build()
        Timber.tag("customtabs").d("launch")
        customtabsIntent.launchUrl(this, Uri.parse(url))
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Timber.tag("NewIntent").d("onNewIntent")
        setIntent(intent)

        val action = intent.action
        if (Intent.ACTION_VIEW != action) {
            Timber.tag("action_view").d(action)
            return
        }

        val uri: Uri = intent.data
        mCode = uri.getQueryParameter("code")
        if (TextUtils.isEmpty(mCode)) {
            // Error
            val error = uri.getQueryParameter("error")
            Toast.makeText(applicationContext, "Error reason: $error", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(applicationContext, "Success code: $mCode", Toast.LENGTH_SHORT).show()
            getAccessToken()
        }
    }


    private fun getAccessToken() {
        val request: Request = Request.Builder()
                .url(getString(R.string.oauth_url) + "access_token?" + "code=" + mCode + "&client_id=" + mClientId + "&client_secret=" + mClientSecret)
                //.header("Accept", "application/json")
                .build()

        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call?, response: Response?) {
                val res: String? = response?.body()?.string()
                if (res != null) {
                    //TODO: 成功した時のトークンについて処理を書く
                    val access_token = res.split("&")[0].substring(13)
                    val sharedPref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
                    sharedPref.edit().putString(getString(R.string.token), access_token).apply()
                    Timber.tag(getString(R.string.token)).d(access_token)
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                }
            }
            override fun onFailure(call: Call?, e: IOException?) {
                Timber.tag("okhttp").d("error")
            }
        })
    }

}
