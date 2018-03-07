package ryu236.android.com.githubclientapp

import android.app.Application
import timber.log.Timber

/**
 * Created by ryu on 3/6/2018 AD.
 */

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        Timber.d("GitHubApp Start!")
    }
}