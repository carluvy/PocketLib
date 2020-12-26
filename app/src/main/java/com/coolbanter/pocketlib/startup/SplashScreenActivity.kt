package com.coolbanter.pocketlib.startup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.coolbanter.pocketlib.R
import com.coolbanter.pocketlib.setup.SignUpActivity
import kotlinx.coroutines.*


class SplashScreenActivity : AppCompatActivity() {
    private val splashTime = 3000L
//    private lateinit var mUser: FirebaseAuth
//    private val sharedPreferences by lazy { InjectorUtils.provideSharedPreference(this) }
    private val activityScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
//        val contents = Contents(sharedPreferences)

        activityScope.launch {
            delay(splashTime)
            startActivity(Intent(this@SplashScreenActivity, SignUpActivity::class.java))
            finish()
        }




//        Handler(Looper.getMainLooper()).postDelayed({
////            if (contents.isFirstLaunch()) {
//////                contents.setAppsFirstLaunch(false)
//                launchActivity<SignUpActivity>()
//                finish()
//
////                mUser = FirebaseAuth.getInstance()
////                if (mUser.currentUser != null) {
////                    launchActivity<DashboardActivity>()
////                    finish()
////                } else {
////                    launchActivity<LoginActivity>()
////                    finish()
////                }
//
//        }, splashTime)

    }

//    override fun onPause() {
//        activityScope.cancel()
//        super.onPause()
//    }
}