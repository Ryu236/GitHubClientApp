package ryu236.android.com.githubclientapp

import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
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
            Timber.tag(getString(R.string.token)).d("gotcha!")
            response.setText(mAccessToken, TextView.BufferType.NORMAL)
        }

    }

}