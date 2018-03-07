package ryu236.android.com.githubclientapp

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.TextView
import okhttp3.*
import timber.log.Timber
import java.io.IOException

class MainActivity : AppCompatActivity() {

    val mClientId: String = "128f1acba0db81b630d7"
    val mClientSecret: String = "00218364b4dccf7b70acd9b86f38520b6f6e1d41"
    var mCode: String? = null
    var mAccessToken: String? = null
    val client: OkHttpClient = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        mAccessToken = sharedPref.getString("access_token", null)

        if (mAccessToken == null) {
            val intent: Intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Timber.tag("access_token").d("gotcha!")
        }

    }

}