package com.myfreezer.app.ui.main

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.myfreezer.app.R
import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;
import com.myfreezer.app.BuildConfig

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Get Secrets
//        val ai: ApplicationInfo = applicationContext.packageManager
//            .getApplicationInfo(applicationContext.packageName, PackageManager.GET_META_DATA)
//        val value = ai.metaData["keyValue"]
        val appCenterKey = BuildConfig.APP_CENTER_API_KEY.toString()

        AppCenter.start(getApplication(), appCenterKey,Analytics::class.java, Crashes::class.java);




    }


}